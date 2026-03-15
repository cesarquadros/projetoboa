package br.com.boasalasdeatendimento.chatbot.repository

import br.com.boasalasdeatendimento.chatbot.model.MensagemProcessada
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import java.time.Instant

@Repository
class MensagemProcessadaRepository(enhancedClient: DynamoDbEnhancedClient) {

    private val table: DynamoDbTable<MensagemProcessada> =
        enhancedClient.table("chatbot-mensagens-processadas", TableSchema.fromBean(MensagemProcessada::class.java))

    /**
     * Verifica se um messageId já foi processado.
     */
    fun jaProcessada(messageId: String): Boolean =
        table.getItem(Key.builder().partitionValue(messageId).build()) != null

    /**
     * Registra um messageId como processado. TTL de 1 hora.
     */
    fun registrar(messageId: String, telefone: String) {
        val item = MensagemProcessada(
            messageId = messageId,
            telefone = telefone,
            processadoEm = Instant.now().toString(),
            ttl = Instant.now().plusSeconds(3600).epochSecond
        )
        table.putItem(item)
    }
}
