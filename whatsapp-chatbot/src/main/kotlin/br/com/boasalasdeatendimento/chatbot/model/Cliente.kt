package br.com.boasalasdeatendimento.chatbot.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class Cliente(
    @get:DynamoDbPartitionKey
    var telefone: String = "",

    @get:DynamoDbAttribute("nome")
    var nome: String = "",

    @get:DynamoDbAttribute("createdAt")
    var createdAt: String = ""
)
