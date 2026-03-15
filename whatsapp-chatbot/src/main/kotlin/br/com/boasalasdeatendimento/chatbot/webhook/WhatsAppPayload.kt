package br.com.boasalasdeatendimento.chatbot.webhook

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WhatsAppWebhookPayload(
    val `object`: String = "",
    val entry: List<Entry> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Entry(
    val id: String = "",
    val changes: List<Change> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Change(
    val value: Value = Value(),
    val field: String = ""
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Value(
    val messages: List<WhatsAppMessage> = emptyList(),
    val contacts: List<Contact> = emptyList(),
    val metadata: Metadata = Metadata()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class WhatsAppMessage(
    val id: String = "",
    val from: String = "",
    val timestamp: String = "",
    val type: String = "",
    val text: TextContent? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TextContent(
    val body: String = ""
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Contact(
    val profile: Profile = Profile(),
    @JsonProperty("wa_id") val waId: String = ""
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Profile(
    val name: String = ""
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Metadata(
    @JsonProperty("display_phone_number") val displayPhoneNumber: String = "",
    @JsonProperty("phone_number_id") val phoneNumberId: String = ""
)

// Payload para enviar mensagem
data class SendMessageRequest(
    @JsonProperty("messaging_product") val messagingProduct: String = "whatsapp",
    @JsonProperty("recipient_type") val recipientType: String = "individual",
    val to: String,
    val type: String = "text",
    val text: TextBody
)

data class TextBody(
    @JsonProperty("preview_url") val previewUrl: Boolean = false,
    val body: String
)
