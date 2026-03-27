package com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookCallbackEntryChangesValueMetadata(
		@JsonProperty(value = "display_phone_number") String displayPhoneNumber,
		@JsonProperty(value = "phone_number_id") String phoneNumberId) {

}
