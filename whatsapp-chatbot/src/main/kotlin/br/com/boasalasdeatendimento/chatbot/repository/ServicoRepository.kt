package br.com.boasalasdeatendimento.chatbot.repository

import br.com.boasalasdeatendimento.chatbot.model.Servico
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Repository
class ServicoRepository(enhancedClient: DynamoDbEnhancedClient) {

    private val table: DynamoDbTable<Servico> =
        enhancedClient.table("chatbot-servicos", TableSchema.fromBean(Servico::class.java))

    fun buscar(servicoId: String): Servico? =
        table.getItem(Key.builder().partitionValue(servicoId).build())

    fun listarTodos(): List<Servico> =
        table.scan().items().toList()

    fun salvar(servico: Servico) {
        table.putItem(servico)
    }

    fun deletar(servicoId: String) {
        table.deleteItem(Key.builder().partitionValue(servicoId).build())
    }
}
