package br.com.boasalasdeatendimento.chatbot.repository

import br.com.boasalasdeatendimento.chatbot.model.SessaoChatbot
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Repository
class SessaoRepository(enhancedClient: DynamoDbEnhancedClient) {

    private val table: DynamoDbTable<SessaoChatbot> =
        enhancedClient.table("chatbot-sessoes", TableSchema.fromBean(SessaoChatbot::class.java))

    fun buscar(telefone: String): SessaoChatbot? =
        table.getItem(Key.builder().partitionValue(telefone).build())

    fun salvar(sessao: SessaoChatbot) {
        table.putItem(sessao)
    }

    fun deletar(telefone: String) {
        table.deleteItem(Key.builder().partitionValue(telefone).build())
    }
}
