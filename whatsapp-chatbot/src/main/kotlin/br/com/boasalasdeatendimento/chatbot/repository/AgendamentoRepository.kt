package br.com.boasalasdeatendimento.chatbot.repository

import br.com.boasalasdeatendimento.chatbot.model.Agendamento
import br.com.boasalasdeatendimento.chatbot.model.StatusAgendamento
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest

@Repository
class AgendamentoRepository(enhancedClient: DynamoDbEnhancedClient) {

    private val table: DynamoDbTable<Agendamento> =
        enhancedClient.table("chatbot-agendamentos", TableSchema.fromBean(Agendamento::class.java))

    private val gsiProfissional: DynamoDbIndex<Agendamento> =
        table.index("profissionalId-dataHora-index")

    fun salvar(agendamento: Agendamento) {
        table.putItem(agendamento)
    }

    fun buscar(clienteTelefone: String, agendamentoId: String): Agendamento? =
        table.getItem(
            Key.builder()
                .partitionValue(clienteTelefone)
                .sortValue(agendamentoId)
                .build()
        )

    fun listarPorCliente(clienteTelefone: String): List<Agendamento> =
        table.query(
            QueryConditional.keyEqualTo(Key.builder().partitionValue(clienteTelefone).build())
        ).items().toList()

    fun listarAgendadosPorCliente(clienteTelefone: String): List<Agendamento> =
        listarPorCliente(clienteTelefone)
            .filter { it.status == StatusAgendamento.AGENDADO.name }
            .sortedBy { it.dataHora }

    /**
     * Busca agendamentos de um profissional a partir de uma data/hora (formato ISO: "2024-01-15T00:00")
     * Usado para verificar horários já ocupados.
     */
    fun listarPorProfissionalAPartirDe(profissionalId: String, dataHoraInicio: String): List<Agendamento> =
        gsiProfissional.query(
            QueryEnhancedRequest.builder()
                .queryConditional(
                    QueryConditional.sortGreaterThanOrEqualTo(
                        Key.builder()
                            .partitionValue(profissionalId)
                            .sortValue(dataHoraInicio)
                            .build()
                    )
                )
                .build()
        ).flatMap { it.items() }.toList()

    fun listarTodos(): List<Agendamento> =
        table.scan().items().toList()
}
