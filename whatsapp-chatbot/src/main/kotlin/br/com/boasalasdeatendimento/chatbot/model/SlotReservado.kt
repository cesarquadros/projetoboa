package br.com.boasalasdeatendimento.chatbot.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

/**
 * Representa um slot de horário que está reservado (ocupado) por um agendamento.
 * Tabela separada usada como "trava" para garantir reservas atômicas via transactWriteItems.
 * Isso previne double-booking quando dois clientes tentam reservar o mesmo horário simultaneamente.
 *
 * PK: profissionalId
 * SK: dataHora (ISO: "yyyy-MM-ddTHH:mm")
 */
@DynamoDbBean
data class SlotReservado(
    @get:DynamoDbPartitionKey
    var profissionalId: String = "",

    @get:DynamoDbSortKey
    var dataHora: String = "",

    @get:DynamoDbAttribute("agendamentoId")
    var agendamentoId: String = "",

    @get:DynamoDbAttribute("clienteTelefone")
    var clienteTelefone: String = "",

    @get:DynamoDbAttribute("reservadoEm")
    var reservadoEm: String = ""
)
