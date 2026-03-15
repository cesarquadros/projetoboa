package br.com.boasalasdeatendimento.chatbot.repository

import br.com.boasalasdeatendimento.chatbot.model.Cliente
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Repository
class ClienteRepository(enhancedClient: DynamoDbEnhancedClient) {

    private val table: DynamoDbTable<Cliente> =
        enhancedClient.table("chatbot-clientes", TableSchema.fromBean(Cliente::class.java))

    fun buscar(telefone: String): Cliente? =
        table.getItem(Key.builder().partitionValue(telefone).build())

    fun salvar(cliente: Cliente) {
        table.putItem(cliente)
    }
}
