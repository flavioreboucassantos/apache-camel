package com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWhatsAppContext(@JsonProperty(value = "message_id") String messageId) {
}
