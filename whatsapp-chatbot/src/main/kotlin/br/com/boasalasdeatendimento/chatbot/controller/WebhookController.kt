package br.com.boasalasdeatendimento.chatbot.controller

import br.com.boasalasdeatendimento.chatbot.config.WhatsAppConfig
import br.com.boasalasdeatendimento.chatbot.service.ChatbotService
import br.com.boasalasdeatendimento.chatbot.webhook.WhatsAppWebhookPayload
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/webhook")
class WebhookController(
    private val chatbotService: ChatbotService,
    private val whatsAppConfig: WhatsAppConfig
) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Verificação do webhook pela Meta. Retorna hub.challenge se o verify_token estiver correto.
     */
    @GetMapping
    fun verificar(
        @RequestParam("hub.mode") mode: String?,
        @RequestParam("hub.verify_token") verifyToken: String?,
        @RequestParam("hub.challenge") challenge: String?
    ): ResponseEntity<String> {
        return if (mode == "subscribe" && verifyToken == whatsAppConfig.verifyToken) {
            log.info("Webhook verificado com sucesso")
            ResponseEntity.ok(challenge)
        } else {
            log.warn("Falha na verificação do webhook: mode=$mode, token=$verifyToken")
            ResponseEntity.status(403).body("Forbidden")
        }
    }

    /**
     * Recebe mensagens do WhatsApp e processa via ChatbotService.
     */
    @PostMapping
    fun receberMensagem(@RequestBody payload: WhatsAppWebhookPayload): ResponseEntity<String> {
        payload.entry.forEach { entry ->
            entry.changes.forEach { change ->
                change.value.messages.forEach { mensagem ->
                    if (mensagem.type == "text") {
                        val telefone = mensagem.from
                        val texto = mensagem.text?.body ?: ""
                        val nomeContato = change.value.contacts
                            .firstOrNull { it.waId == telefone }?.profile?.name ?: ""
                        log.info("Mensagem recebida de $telefone: $texto")
                        chatbotService.processar(telefone, texto, nomeContato)
                    }
                }
            }
        }
        return ResponseEntity.ok("EVENT_RECEIVED")
    }
}
