package br.com.boasalasdeatendimento.chatbot.webhook

import br.com.boasalasdeatendimento.chatbot.config.WhatsAppConfig
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class WhatsAppApiClient(
    private val config: WhatsAppConfig
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private val restClient: RestClient by lazy {
        RestClient.builder()
            .baseUrl("${config.apiUrl}/${config.phoneNumberId}")
            .defaultHeader("Authorization", "Bearer ${config.accessToken}")
            .defaultHeader("Content-Type", "application/json")
            .build()
    }

    fun enviarMensagem(destinatario: String, texto: String) {
        val payload = SendMessageRequest(
            to = destinatario,
            text = TextBody(body = texto)
        )
        try {
            restClient.post()
                .uri("/messages")
                .body(payload)
                .retrieve()
                .toBodilessEntity()
            log.info("Mensagem enviada para $destinatario")
        } catch (ex: Exception) {
            log.error("Erro ao enviar mensagem para $destinatario: ${ex.message}", ex)
        }
    }
}
