package br.com.boasalasdeatendimento.chatbot.service

import br.com.boasalasdeatendimento.chatbot.model.HorarioAtendimento
import br.com.boasalasdeatendimento.chatbot.repository.ProfissionalRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Representa um slot de horário disponível para geração (antes de salvar no DynamoDB).
 * Não é uma entidade DynamoDB — apenas DTO interno.
 */
data class SlotDisponivel(
    val profissionalId: String,
    val dataHora: String // ISO: "yyyy-MM-ddTHH:mm"
)

/**
 * Serviço responsável por gerar os slots de disponibilidade dos profissionais.
 *
 * Executa automaticamente todo domingo às 01:00 para pré-gerar os próximos 14 dias.
 * Também pode ser acionado manualmente via endpoint admin.
 *
 * Lógica:
 * 1. Para cada profissional ativo, lê seu horarioAtendimento (grade semanal)
 * 2. Para cada dia dos próximos 14 dias, verifica se o profissional atende naquele dia da semana
 * 3. Gera slots de acordo com horaInicio, horaFim e intervaloMinutos
 * 4. Persiste apenas os slots que ainda não existem (não sobrescreve slots já reservados)
 */
@Service
class SlotGeneratorService(
    private val profissionalRepository: ProfissionalRepository,
    private val enhancedClient: DynamoDbEnhancedClient,
    private val objectMapper: ObjectMapper
) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val isoDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val isoDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

    // Tabela de slots reservados — usada para verificar colisões antes de criar slots
    private val slotReservadoTable = enhancedClient.table(
        "chatbot-slots-reservados",
        TableSchema.fromClass(br.com.boasalasdeatendimento.chatbot.model.SlotReservado::class.java)
    )

    /**
     * Dispara toda domingo às 01:00 (cron: segundo minuto hora dia-mês mês dia-semana).
     * Gera slots dos próximos 14 dias para todos os profissionais ativos.
     */
    @Scheduled(cron = "0 0 1 * * SUN")
    @Async("slotGeneratorExecutor")
    fun gerarSlotsSemanalmente() {
        log.info("Iniciando geração semanal de slots de disponibilidade")
        gerarSlots(diasAFrente = 14)
    }

    /**
     * Geração manual (chamada pelo endpoint admin ou no startup em dev).
     * @param diasAFrente quantos dias à frente gerar (padrão: 14)
     * @param apenasAtivos se true, gera apenas para profissionais com ativo=true
     */
    fun gerarSlots(diasAFrente: Int = 14, apenasAtivos: Boolean = true) {
        val profissionais = if (apenasAtivos) profissionalRepository.listarAtivos()
                            else profissionalRepository.listarTodos()

        if (profissionais.isEmpty()) {
            log.warn("Nenhum profissional encontrado para geração de slots")
            return
        }

        val hoje = LocalDate.now()
        val dataFim = hoje.plusDays(diasAFrente.toLong())

        var totalGerados = 0
        var totalIgnorados = 0

        profissionais.forEach { profissional ->
            val horarios: List<HorarioAtendimento> = try {
                objectMapper.readValue(profissional.horarioAtendimento)
            } catch (e: Exception) {
                log.warn("horarioAtendimento inválido para profissional ${profissional.profissionalId}: ${e.message}")
                return@forEach
            }

            if (horarios.isEmpty()) return@forEach

            val slotsParaCriar = mutableListOf<SlotDisponivel>()

            // Itera cada dia do intervalo
            var data = hoje.plusDays(1)
            while (!data.isAfter(dataFim)) {
                val diaSemana = data.dayOfWeek.value
                val gradeHoje = horarios.filter { it.diaSemana == diaSemana }

                gradeHoje.forEach { grade ->
                    var hora = LocalTime.parse(grade.horaInicio)
                    val horaFim = LocalTime.parse(grade.horaFim)
                    while (!hora.isAfter(horaFim.minusMinutes(grade.intervaloMinutos.toLong()))) {
                        val dataHoraIso = "${data.format(isoDate)}T${hora.format(DateTimeFormatter.ofPattern("HH:mm"))}"
                        slotsParaCriar.add(SlotDisponivel(profissional.profissionalId, dataHoraIso))
                        hora = hora.plusMinutes(grade.intervaloMinutos.toLong())
                    }
                }

                data = data.plusDays(1)
            }

            // Filtra apenas slots que ainda não existem como reservados
            val slotsNovos = slotsParaCriar.filter { slot ->
                slotReservadoTable.getItem(
                    Key.builder()
                        .partitionValue(slot.profissionalId)
                        .sortValue(slot.dataHora)
                        .build()
                ) == null
            }

            totalGerados += slotsNovos.size
            totalIgnorados += slotsParaCriar.size - slotsNovos.size

            if (slotsNovos.isNotEmpty()) {
                log.info("Profissional ${profissional.nome}: ${slotsNovos.size} slots novos, ${slotsParaCriar.size - slotsNovos.size} já existentes")
            }
        }

        log.info("Geração de slots concluída: $totalGerados gerados (disponíveis), $totalIgnorados ignorados (já reservados ou existentes)")
    }

    /**
     * Retorna os slots disponíveis (ainda não reservados) de um profissional
     * num intervalo de datas, para uso pelo chatbot.
     */
    fun slotsDisponiveisNoPeriodo(
        profissionalId: String,
        dataInicio: LocalDate,
        dataFim: LocalDate
    ): List<String> {
        val profissional = profissionalRepository.buscar(profissionalId) ?: return emptyList()
        val horarios: List<HorarioAtendimento> = try {
            objectMapper.readValue(profissional.horarioAtendimento)
        } catch (e: Exception) {
            return emptyList()
        }

        val todos = mutableListOf<String>()
        var data = dataInicio
        while (!data.isAfter(dataFim)) {
            val diaSemana = data.dayOfWeek.value
            horarios.filter { it.diaSemana == diaSemana }.forEach { grade ->
                var hora = LocalTime.parse(grade.horaInicio)
                val horaFim = LocalTime.parse(grade.horaFim)
                while (!hora.isAfter(horaFim.minusMinutes(grade.intervaloMinutos.toLong()))) {
                    todos.add("${data.format(isoDate)}T${hora.format(DateTimeFormatter.ofPattern("HH:mm"))}")
                    hora = hora.plusMinutes(grade.intervaloMinutos.toLong())
                }
            }
            data = data.plusDays(1)
        }

        // Filtra os ocupados
        val ocupados = todos.filter { dataHora ->
            slotReservadoTable.getItem(
                Key.builder()
                    .partitionValue(profissionalId)
                    .sortValue(dataHora)
                    .build()
            ) != null
        }.toSet()

        return todos.filter { it !in ocupados }
    }
}
