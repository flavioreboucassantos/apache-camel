package com.br.flavioreboucassantos.camel_whatsapp.route_builder;

import org.apache.camel.Exchange;
import org.apache.camel.builder.ValueBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class RouteBuilderSendWhatsApp extends BaseRouteBuilderSendWhatsApp {

	final String phoneNumberId;
	final String whatsAppToken;

	public RouteBuilderSendWhatsApp(final String phoneNumberId, final String whatsAppToken) {
		this.phoneNumberId = phoneNumberId;
		this.whatsAppToken = whatsAppToken;
	}

	@Override
	public final String getRouteUri() {
		return "direct:sendWhatsApp";
	}

	@Override
	public void configure() throws Exception {

		final ObjectMapper mapper = new ObjectMapper();

		final String baseUrl = "https://graph.facebook.com";
		final String version = "/v23.0/";

		final ValueBuilder headerAuthorizationValue = constant("Bearer " + whatsAppToken);
		final ValueBuilder contentType = constant("application/json");

		final String uriToHttpPost = baseUrl + version + phoneNumberId + "/messages";

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
					log.error("HTTP operation failed with status code: {} and body:\n{}", statusCode, prettyJson);
				})
				.handled(true) // Mark the exception as handled so it doesn't propagate further
				.end();
//				.to("direct:handleErrorResponse"); // Redirect to another route for specific error processing

		// Rota: Receber da Fila -> Enviar para WhatsApp
		from(getRouteUri())
				.log("Enviando mensagem para: ${body.to}")
				.log("Conteúdo da mensagem: ${body.messageText.body}")
				.log("uriToHttpPost: " + uriToHttpPost)

				// Converte o POJO para JSON para a API Meta
				.marshal().json(JsonLibrary.Jackson)
				.log("Corpo da mensagem: ${body}")

				// Configura os cabeçalhos necessários
				.setHeader("Authorization", headerAuthorizationValue)
				.setHeader("Content-Type", contentType)

				// Envia via HTTP POST
				.to(uriToHttpPost)

				.log("Resposta da API: ${body}");

	}
}
