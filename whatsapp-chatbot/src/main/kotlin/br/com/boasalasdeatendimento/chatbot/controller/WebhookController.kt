package br.com.boasalasdeatendimento.chatbot.controller

import br.com.boasalasdeatendimento.chatbot.config.WhatsAppConfig
import br.com.boasalasdeatendimento.chatbot.service.WebhookProcessorService
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
    private val webhookProcessorService: WebhookProcessorService,
    private val whatsAppConfig: WhatsAppConfig
) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Verificação do webhook pela Meta.
     * Retorna hub.challenge se o verify_token estiver correto.
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
     * Recebe eventos de mensagem do Meta e retorna 200 imediatamente.
     *
     * O Meta exige resposta em menos de 5 segundos ou reentrega o evento.
     * Todo o processamento de negócio é delegado ao WebhookProcessorService
     * que roda em pool assíncrono dedicado (webhookExecutor).
     * Isso evita timeouts mesmo quando DynamoDB ou a API do WhatsApp estiverem lentos.
     */
    @PostMapping
    fun receberMensagem(@RequestBody payload: WhatsAppWebhookPayload): ResponseEntity<String> {
        webhookProcessorService.processarAsync(payload)
        return ResponseEntity.ok("EVENT_RECEIVED")
    }
}
