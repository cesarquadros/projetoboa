package br.com.boasalasdeatendimento.chatbot.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class Profissional(
    @get:DynamoDbPartitionKey
    var profissionalId: String = "",

    @get:DynamoDbAttribute("nome")
    var nome: String = "",

    @get:DynamoDbAttribute("especialidades")
    var especialidades: MutableSet<String> = mutableSetOf(),

    @get:DynamoDbAttribute("ativo")
    var ativo: Boolean = true,

    /**
     * JSON serializado de List<HorarioAtendimento>
     * Exemplo: [{"diaSemana":1,"horaInicio":"09:00","horaFim":"18:00","intervaloMinutos":60}]
     */
    @get:DynamoDbAttribute("horarioAtendimento")
    var horarioAtendimento: String = "[]"
)
