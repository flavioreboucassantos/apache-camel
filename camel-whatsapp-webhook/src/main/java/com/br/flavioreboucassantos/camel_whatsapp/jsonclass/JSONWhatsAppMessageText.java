package com.br.flavioreboucassantos.camel_whatsapp.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWhatsAppMessageText(@JsonProperty(value = "body") String body, @JsonProperty(value = "preview_url") boolean previewUrl) {
}
