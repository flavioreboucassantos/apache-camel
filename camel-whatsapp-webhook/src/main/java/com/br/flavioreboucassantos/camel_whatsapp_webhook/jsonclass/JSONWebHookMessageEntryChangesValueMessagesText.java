package com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessageEntryChangesValueMessagesText(@JsonProperty(value = "body") String body) {
}
