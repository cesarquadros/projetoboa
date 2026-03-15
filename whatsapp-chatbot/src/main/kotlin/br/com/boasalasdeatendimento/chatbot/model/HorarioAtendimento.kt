package br.com.boasalasdeatendimento.chatbot.model

/**
 * Representa a grade semanal de atendimento de um profissional.
 * diaSemana: 1=Segunda, 2=Terça, ... 7=Domingo
 */
data class HorarioAtendimento(
    val diaSemana: Int,
    val horaInicio: String,
    val horaFim: String,
    val intervaloMinutos: Int = 60
)
