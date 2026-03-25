package com.br.flavioreboucassantos.camel_whatsapp.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWhatsAppContext(@JsonProperty(value = "message_id") String messageId) {
}
