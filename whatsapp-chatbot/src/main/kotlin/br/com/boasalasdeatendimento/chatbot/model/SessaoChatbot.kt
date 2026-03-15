package br.com.boasalasdeatendimento.chatbot.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class SessaoChatbot(
    @get:DynamoDbPartitionKey
    var telefone: String = "",

    @get:DynamoDbAttribute("estado")
    var estado: String = EstadoChatbot.INICIO.name,

    @get:DynamoDbAttribute("contexto")
    var contexto: String = "{}",

    @get:DynamoDbAttribute("ttl")
    var ttl: Long = 0L
)
