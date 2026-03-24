package com.br.flavioreboucassantos.camel_whatsapp;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWhatsAppMessage(
		@JsonProperty(value = "messaging_product") String messagingProduct,
		@JsonProperty(value = "to") String to,
		@JsonProperty(value = "type") String type,
		@JsonProperty(value = "text") JSONWhatsAppMessageText messageText) {

	public JSONWhatsAppMessage(String to, String textOfMessage) {
		this("whatsapp", to, "text", new JSONWhatsAppMessageText(textOfMessage));
	}
}
