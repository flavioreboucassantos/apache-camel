package com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWhatsAppMessage(
		@JsonProperty(value = "messaging_product") String messagingProduct,
		@JsonProperty(value = "recipient_type") String recipientType,
		@JsonProperty(value = "to") String to,
		@JsonProperty(value = "type") String type,
		@JsonProperty(value = "context") JSONWhatsAppContext context,
		@JsonProperty(value = "text") JSONWhatsAppMessageText messageText) {

	public JSONWhatsAppMessage(String to, String textOfMessage) {
		this("whatsapp", "individual", to, "text", null, new JSONWhatsAppMessageText(textOfMessage, true));
	}
}
