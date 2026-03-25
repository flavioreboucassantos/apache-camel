package com.br.flavioreboucassantos.webhook_whatsapp.jsonclass;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessage(
		@JsonProperty(value = "object") String object,
		@JsonProperty(value = "entry") List<JSONWebHookMessageEntry> entry) {

}
