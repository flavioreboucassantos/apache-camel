package com.br.flavioreboucassantos.webhook_whatsapp.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessageEntryChangesValueMessagesText(@JsonProperty(value = "body") String body) {
}
