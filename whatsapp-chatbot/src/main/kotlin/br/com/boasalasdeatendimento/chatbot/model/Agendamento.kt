package br.com.boasalasdeatendimento.chatbot.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

enum class StatusAgendamento { AGENDADO, CANCELADO, CONCLUIDO }

@DynamoDbBean
data class Agendamento(
    @get:DynamoDbPartitionKey
    var clienteTelefone: String = "",

    @get:DynamoDbSortKey
    var agendamentoId: String = "",

    @get:DynamoDbSecondaryPartitionKey(indexNames = ["profissionalId-dataHora-index"])
    var profissionalId: String = "",

    @get:DynamoDbAttribute("servicoId")
    var servicoId: String = "",

    @get:DynamoDbAttribute("servicoNome")
    var servicoNome: String = "",

    @get:DynamoDbAttribute("profissionalNome")
    var profissionalNome: String = "",

    @get:DynamoDbSecondarySortKey(indexNames = ["profissionalId-dataHora-index"])
    var dataHora: String = "",

    @get:DynamoDbAttribute("status")
    var status: String = StatusAgendamento.AGENDADO.name
)
