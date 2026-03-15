package br.com.boasalasdeatendimento.chatbot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "whatsapp")
class WhatsAppConfig {
    lateinit var apiUrl: String
    lateinit var phoneNumberId: String
    lateinit var accessToken: String
    lateinit var verifyToken: String
}
