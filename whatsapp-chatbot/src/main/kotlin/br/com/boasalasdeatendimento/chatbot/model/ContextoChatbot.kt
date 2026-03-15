package br.com.boasalasdeatendimento.chatbot.model

data class ContextoChatbot(
    val profissionalId: String? = null,
    val profissionalNome: String? = null,
    val servicoId: String? = null,
    val servicoNome: String? = null,
    val data: String? = null,
    val horario: String? = null,
    val agendamentoId: String? = null
)
