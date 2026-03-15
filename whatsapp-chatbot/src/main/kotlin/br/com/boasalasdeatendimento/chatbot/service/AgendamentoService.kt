package br.com.boasalasdeatendimento.chatbot.service

import br.com.boasalasdeatendimento.chatbot.model.Agendamento
import br.com.boasalasdeatendimento.chatbot.model.SlotReservado
import br.com.boasalasdeatendimento.chatbot.model.StatusAgendamento
import br.com.boasalasdeatendimento.chatbot.repository.AgendamentoRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Expression
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.ConditionCheck
import software.amazon.awssdk.enhanced.dynamodb.model.Put
import software.amazon.awssdk.enhanced.dynamodb.model.TransactPutItemEnhancedRequest
import software.amazon.awssdk.enhanced.dynamodb.model.TransactWriteItemsEnhancedRequest
import software.amazon.awssdk.services.dynamodb.model.TransactionCanceledException
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class SlotOcupadoException(profissionalId: String, dataHora: String) :
    RuntimeException("Horário $dataHora do profissional $profissionalId já está reservado.")

@Service
class AgendamentoService(
    private val agendamentoRepository: AgendamentoRepository,
    private val enhancedClient: DynamoDbEnhancedClient
) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    private val agendamentoTable: DynamoDbTable<Agendamento> =
        enhancedClient.table("chatbot-agendamentos", TableSchema.fromBean(Agendamento::class.java))

    private val slotReservadoTable: DynamoDbTable<SlotReservado> =
        enhancedClient.table("chatbot-slots-reservados", TableSchema.fromBean(SlotReservado::class.java))

    /**
     * Cria um agendamento usando DynamoDB TransactWriteItems para garantir atomicidade.
     *
     * A transação executa duas operações simultaneamente:
     * 1. Put em chatbot-slots-reservados com condição attribute_not_exists — garante que o slot
     *    não esteja ocupado. Se já existir, a transação inteira é cancelada.
     * 2. Put em chatbot-agendamentos para registrar o agendamento.
     *
     * Isso previne double-booking mesmo sob alta concorrência.
     */
    fun agendar(
        clienteTelefone: String,
        profissionalId: String,
        profissionalNome: String,
        servicoId: String,
        servicoNome: String,
        data: String,
        horario: String
    ): Agendamento {
        val dt = LocalDateTime.parse("$data $horario", dtFormatter)
        val isoDataHora = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
        val agendamentoId = UUID.randomUUID().toString()

        val agendamento = Agendamento(
            clienteTelefone = clienteTelefone,
            agendamentoId = agendamentoId,
            profissionalId = profissionalId,
            profissionalNome = profissionalNome,
            servicoId = servicoId,
            servicoNome = servicoNome,
            dataHora = isoDataHora,
            status = StatusAgendamento.AGENDADO.name
        )

        val slotReservado = SlotReservado(
            profissionalId = profissionalId,
            dataHora = isoDataHora,
            agendamentoId = agendamentoId,
            clienteTelefone = clienteTelefone,
            reservadoEm = Instant.now().toString()
        )

        // Condição: o slot NÃO pode existir ainda (attribute_not_exists)
        val slotNaoExisteCondicao = Expression.builder()
            .expression("attribute_not_exists(profissionalId)")
            .build()

        try {
            enhancedClient.transactWriteItems(
                TransactWriteItemsEnhancedRequest.builder()
                    .addPutItem(
                        slotReservadoTable,
                        TransactPutItemEnhancedRequest.builder(SlotReservado::class.java)
                            .item(slotReservado)
                            .conditionExpression(slotNaoExisteCondicao)
                            .build()
                    )
                    .addPutItem(
                        agendamentoTable,
                        TransactPutItemEnhancedRequest.builder(Agendamento::class.java)
                            .item(agendamento)
                            .build()
                    )
                    .build()
            )
        } catch (ex: TransactionCanceledException) {
            log.warn("Tentativa de double-booking bloqueada: profissional=$profissionalId dataHora=$isoDataHora cliente=$clienteTelefone")
            throw SlotOcupadoException(profissionalId, isoDataHora)
        }

        log.info("Agendamento criado: id=$agendamentoId profissional=$profissionalNome dataHora=$isoDataHora")
        return agendamento
    }

    /**
     * Cancela um agendamento e libera o slot atomicamente via transação.
     */
    fun cancelar(clienteTelefone: String, agendamentoId: String): Boolean {
        val agendamento = agendamentoRepository.buscar(clienteTelefone, agendamentoId) ?: return false
        if (agendamento.status != StatusAgendamento.AGENDADO.name) return false

        val agendamentoCancelado = agendamento.copy(status = StatusAgendamento.CANCELADO.name)

        try {
            enhancedClient.transactWriteItems(
                TransactWriteItemsEnhancedRequest.builder()
                    // Atualiza status do agendamento para CANCELADO
                    .addPutItem(
                        agendamentoTable,
                        TransactPutItemEnhancedRequest.builder(Agendamento::class.java)
                            .item(agendamentoCancelado)
                            .build()
                    )
                    // Remove o slot reservado para liberar o horário
                    .addDeleteItem(slotReservadoTable, SlotReservado(
                        profissionalId = agendamento.profissionalId,
                        dataHora = agendamento.dataHora
                    ))
                    .build()
            )
        } catch (ex: TransactionCanceledException) {
            log.error("Falha ao cancelar agendamento $agendamentoId: ${ex.message}")
            return false
        }

        log.info("Agendamento cancelado: id=$agendamentoId slot liberado: ${agendamento.dataHora}")
        return true
    }

    fun listarHistorico(clienteTelefone: String): List<Agendamento> =
        agendamentoRepository.listarPorCliente(clienteTelefone)
            .sortedByDescending { it.dataHora }

    fun listarAgendadosFuturos(clienteTelefone: String): List<Agendamento> {
        val agora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"))
        return agendamentoRepository.listarAgendadosPorCliente(clienteTelefone)
            .filter { it.dataHora >= agora }
    }

    fun listarTodos(): List<Agendamento> = agendamentoRepository.listarTodos()
}
