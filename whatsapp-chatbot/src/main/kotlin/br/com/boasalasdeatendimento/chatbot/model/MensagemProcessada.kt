package br.com.boasalasdeatendimento.chatbot.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

/**
 * Rastreia IDs de mensagens WhatsApp já processadas para evitar processamento duplicado.
 * O Meta pode entregar o mesmo evento webhook mais de uma vez.
 * TTL de 1 hora garante limpeza automática pelo DynamoDB.
 */
@DynamoDbBean
data class MensagemProcessada(
    @get:DynamoDbPartitionKey
    var messageId: String = "",

    @get:DynamoDbAttribute("telefone")
    var telefone: String = "",

    @get:DynamoDbAttribute("processadoEm")
    var processadoEm: String = "",

    @get:DynamoDbAttribute("ttl")
    var ttl: Long = 0L
)
