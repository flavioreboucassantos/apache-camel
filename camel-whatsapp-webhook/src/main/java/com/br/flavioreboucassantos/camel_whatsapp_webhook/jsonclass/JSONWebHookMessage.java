package com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessage(
		@JsonProperty(value = "object") String object,
		@JsonProperty(value = "entry") List<JSONWebHookMessageEntry> entry) {

}
