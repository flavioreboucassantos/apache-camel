package com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookCallbackEntryChangesValueStatusesPricing(
		@JsonProperty(value = "billable") boolean billable,
		@JsonProperty(value = "pricing_model") String pricingModel,
		@JsonProperty(value = "category") String category,
		@JsonProperty(value = "type") String type) {
}
