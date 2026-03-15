package br.com.boasalasdeatendimento.chatbot.service

import br.com.boasalasdeatendimento.chatbot.repository.MensagemProcessadaRepository
import br.com.boasalasdeatendimento.chatbot.webhook.WhatsAppWebhookPayload
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class WebhookProcessorService(
    private val chatbotService: ChatbotService,
    private val mensagemProcessadaRepository: MensagemProcessadaRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Processa o payload do webhook de forma assíncrona no pool "webhookExecutor".
     * O WebhookController retorna 200 imediatamente para o Meta, satisfazendo o limite de 5 segundos.
     * Este método é executado em background.
     *
     * Inclui deduplicação por messageId: se o Meta entregar o mesmo evento mais de uma vez,
     * a segunda entrega é descartada silenciosamente.
     */
    @Async("webhookExecutor")
    fun processarAsync(payload: WhatsAppWebhookPayload) {
        payload.entry.forEach { entry ->
            entry.changes.forEach { change ->
                change.value.messages.forEach { mensagem ->
                    if (mensagem.type != "text") return@forEach

                    val messageId = mensagem.id
                    val telefone = mensagem.from
                    val texto = mensagem.text?.body ?: return@forEach

                    // Deduplicação: descarta mensagens já processadas
                    if (mensagemProcessadaRepository.jaProcessada(messageId)) {
                        log.info("Mensagem $messageId já processada anteriormente — descartando duplicata")
                        return@forEach
                    }

                    // Registra antes de processar para evitar race condition em caso de reentrada
                    mensagemProcessadaRepository.registrar(messageId, telefone)

                    val nomeContato = change.value.contacts
                        .firstOrNull { it.waId == telefone }?.profile?.name ?: ""

                    try {
                        log.info("Processando mensagem $messageId de $telefone")
                        chatbotService.processar(telefone, texto, nomeContato)
                    } catch (ex: Exception) {
                        log.error("Erro ao processar mensagem $messageId de $telefone: ${ex.message}", ex)
                        // Não re-lança: o webhook já retornou 200 para o Meta
                    }
                }
            }
        }
    }
}
