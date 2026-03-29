package com.br.flavioreboucassantos.camel_whatsapp_webhook.route_builder;

import org.apache.camel.Exchange;
import org.apache.camel.builder.ValueBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public final class RouteBuilderSendWhatsApp extends BaseRouteBuilderSendWhatsApp {

	private static final Logger LOG = LoggerFactory.getLogger(RouteBuilderSendWhatsApp.class);

	final String baseUrl = "https://graph.facebook.com";
	final String version = "/v23.0/";

	final ValueBuilder headerAuthorizationValue;
	final ValueBuilder contentType = constant("application/json");
	final String uriToHttpPost;

	public RouteBuilderSendWhatsApp(
			@ConfigProperty(name = "whatsapp_send.phone_number_id") final String phoneNumberId,
			@ConfigProperty(name = "whatsapp_send.whatsapp_token") final String whatsAppToken) {

		headerAuthorizationValue = constant("Bearer " + whatsAppToken);
		uriToHttpPost = baseUrl + version + phoneNumberId + "/messages";
	}

	@Override
	public String getRouteId() {
		return "SendWhatsApp";
	}

	@Override
	public String getUriFrom() {
		return "direct:sendWhatsApp";
	}

	@Override
	public String getUriTo() {
		return uriToHttpPost;
	}

	@Override
	public void configure() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();

		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		onException(HttpOperationFailedException.class)
				.process(exchange -> {
					HttpOperationFailedException exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, HttpOperationFailedException.class);
					final int statusCode = exception.getStatusCode();
					final String responseBody = exception.getResponseBody();

					// Read the JSON string into a Java object model and write it back as a pretty-printed string
					final Object jsonObject = mapper.readValue(responseBody, Object.class);
					final String prettyJson = mapper.writeValueAsString(jsonObject);

					// Log the error details or perform custom logic
					log.error("\nHTTP operation failed with status code: {} and body:\n{}", statusCode, prettyJson);
				})
				.handled(true) // Mark the exception as handled so it doesn't propagate further
				.end();
//				.to("direct:handleErrorResponse"); // Redirect to another route for specific error processing

		// Rota: Receber da Fila -> Enviar para WhatsApp
		from(getUriFrom())
				.marshal().json(JsonLibrary.Jackson, true)
				.log("\n> Camel (From " + getUriFrom() + " To " + getUriTo() + ") With Body:\n${body}\n")

				.setHeader("Authorization", headerAuthorizationValue)
				.setHeader("Content-Type", contentType)

				.to(getUriTo())

				.log("\n> Resposta da API: ${body}\n");
	}
}
