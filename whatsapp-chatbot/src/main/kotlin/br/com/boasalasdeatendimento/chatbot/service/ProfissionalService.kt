package br.com.boasalasdeatendimento.chatbot.service

import br.com.boasalasdeatendimento.chatbot.model.HorarioAtendimento
import br.com.boasalasdeatendimento.chatbot.model.Profissional
import br.com.boasalasdeatendimento.chatbot.model.SlotReservado
import br.com.boasalasdeatendimento.chatbot.repository.ProfissionalRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class ProfissionalService(
    private val profissionalRepository: ProfissionalRepository,
    private val enhancedClient: DynamoDbEnhancedClient,
    private val objectMapper: ObjectMapper
) {
    private val dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val isoDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val isoDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

    private val slotReservadoTable: DynamoDbTable<SlotReservado> =
        enhancedClient.table("chatbot-slots-reservados", TableSchema.fromBean(SlotReservado::class.java))

    fun listarAtivos(): List<Profissional> = profissionalRepository.listarAtivos()

    fun buscar(id: String): Profissional? = profissionalRepository.buscar(id)

    /**
     * Retorna lista de horários disponíveis para um profissional numa data específica.
     * 1. Gera todos os slots possíveis com base em horarioAtendimento
     * 2. Consulta chatbot-slots-reservados para obter os slots ocupados
     * 3. Retorna somente os slots não ocupados
     *
     * dataTexto: formato dd/MM/yyyy
     */
    fun horariosDisponiveis(profissionalId: String, dataTexto: String): List<String> {
        val profissional = profissionalRepository.buscar(profissionalId) ?: return emptyList()
        val data = LocalDate.parse(dataTexto, dtFormatter)
        val diaSemana = data.dayOfWeek.value

        val horarios: List<HorarioAtendimento> = objectMapper.readValue(profissional.horarioAtendimento)
        val gradeHoje = horarios.filter { it.diaSemana == diaSemana }
        if (gradeHoje.isEmpty()) return emptyList()

        val slotsGerados = mutableListOf<LocalTime>()
        for (grade in gradeHoje) {
            var hora = LocalTime.parse(grade.horaInicio)
            val horaFim = LocalTime.parse(grade.horaFim)
            while (!hora.isAfter(horaFim.minusMinutes(grade.intervaloMinutos.toLong()))) {
                slotsGerados.add(hora)
                hora = hora.plusMinutes(grade.intervaloMinutos.toLong())
            }
        }

        // Consulta os slots ocupados diretamente na tabela chatbot-slots-reservados
        val dataIso = data.format(isoDate)
        val ocupados = slotsOcupadosNaData(profissionalId, dataIso)

        return slotsGerados
            .map { it.format(DateTimeFormatter.ofPattern("HH:mm")) }
            .filter { horarioStr -> "${dataIso}T${horarioStr}" !in ocupados }
    }

    /**
     * Retorna os próximos N dias em que o profissional atende.
     */
    fun proximosDiasDisponiveis(profissionalId: String, quantidade: Int = 7): List<String> {
        val profissional = profissionalRepository.buscar(profissionalId) ?: return emptyList()
        val horarios: List<HorarioAtendimento> = objectMapper.readValue(profissional.horarioAtendimento)
        val diasComAtendimento = horarios.map { it.diaSemana }.toSet()

        val dias = mutableListOf<String>()
        var data = LocalDate.now().plusDays(1)
        while (dias.size < quantidade) {
            if (data.dayOfWeek.value in diasComAtendimento) {
                dias.add(data.format(dtFormatter))
            }
            data = data.plusDays(1)
        }
        return dias
    }

    /**
     * Consulta os slots já reservados de um profissional numa data via tabela chatbot-slots-reservados.
     * Retorna um Set de dataHora ISO ("yyyy-MM-ddTHH:mm") ocupados.
     */
    private fun slotsOcupadosNaData(profissionalId: String, dataIso: String): Set<String> {
        val inicioData = "${dataIso}T00:00"
        val fimData = "${dataIso}T23:59"

        return slotReservadoTable.query(
            QueryEnhancedRequest.builder()
                .queryConditional(
                    QueryConditional.sortBetween(
                        Key.builder().partitionValue(profissionalId).sortValue(inicioData).build(),
                        Key.builder().partitionValue(profissionalId).sortValue(fimData).build()
                    )
                )
                .build()
        ).items().map { it.dataHora }.toSet()
    }
}
