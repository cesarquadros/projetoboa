package br.com.boasalasdeatendimento.chatbot.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class Servico(
    @get:DynamoDbPartitionKey
    var servicoId: String = "",

    @get:DynamoDbAttribute("nome")
    var nome: String = "",

    @get:DynamoDbAttribute("duracao")
    var duracao: Int = 60,

    @get:DynamoDbAttribute("preco")
    var preco: Double = 0.0
)
