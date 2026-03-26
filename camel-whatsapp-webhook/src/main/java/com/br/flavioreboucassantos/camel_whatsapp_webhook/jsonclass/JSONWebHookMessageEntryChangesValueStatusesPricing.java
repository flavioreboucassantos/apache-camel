package com.br.flavioreboucassantos.camel_whatsapp_webhook.jsonclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JSONWebHookMessageEntryChangesValueStatusesPricing(
		@JsonProperty(value = "billable") boolean billable,
		@JsonProperty(value = "pricing_model") String pricingModel,
		@JsonProperty(value = "category") String category,
		@JsonProperty(value = "type") String type) {
}
