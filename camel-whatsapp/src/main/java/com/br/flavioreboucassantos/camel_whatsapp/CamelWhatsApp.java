package com.br.flavioreboucassantos.camel_whatsapp;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ValueBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;

import com.br.flavioreboucassantos.camel_whatsapp.jsonclass.JSONWhatsAppMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CamelWhatsApp {
	static final private void print(final Object o) {
		System.out.println(o);
	}

	static final private void printWipe(final Object o) {
		System.out.println("");
		System.out.println(o);
	}

	static final private void printWipe2(final Object o) {
		System.out.println("");
		System.out.println(o);
		System.out.println("");
	}

	static final private void printWipe3(final Object o) {
		System.out.println(o);
		System.out.println("");
	}

	static public void main(String[] args) throws Exception {
		/*
		 * STRUCTURING CAMEL ROUTE & CONTEXT
		 */

		final RouteBuilder routeBuilder = new RouteBuilder() {

			final ConfigLoader loader = new ConfigLoader();
			final ObjectMapper mapper = new ObjectMapper();

			final String baseUrl = "https://graph.facebook.com";
			final String version = "/v23.0/";

			final String phoneNumberId = loader.getProperty("phoneNumberId");
			final String whatsAppToken = loader.getProperty("whatsAppToken");

			final ValueBuilder headerAuthorizationValue = constant("Bearer " + whatsAppToken);
			final ValueBuilder contentType = constant("application/json");

			final String uriToHttpPost = baseUrl + version + phoneNumberId + "/messages";

			@Override
			public void configure() throws Exception {

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
//						.to("direct:handleErrorResponse"); // Redirect to another route for specific error processing

				// Rota: Receber da Fila -> Enviar para WhatsApp
				from("direct:sendWhatsApp")
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
		};
		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		defaultCamelContext.start();
		defaultCamelContext.addRoutes(routeBuilder);

		final ProducerTemplate producerTemplate = defaultCamelContext.createProducerTemplate();
		final ConsumerTemplate consumerTemplate = defaultCamelContext.createConsumerTemplate();

		/*
		 * USING PRODUCER
		 */

		final String to = "553185868479";
		try {
			producerTemplate.sendBody("direct:sendWhatsApp", new JSONWhatsAppMessage(to, "(2) Teste de envio de mensagem para WhatsApp"));
		} catch (Exception e) {
			printWipe2("sendBody para direct:sendWhatsApp Falhou: " + e.getMessage());
		} finally {
		}

		/*
		 * USING CONSUMER
		 */
//		final String receiveBody = consumerTemplate.receiveBody("seda:end", String.class);

		printWipe("aguarda 2 segundos... (1)");
		Thread.sleep(2000);

		printWipe2("CLOSES ALL");
		producerTemplate.close();
		consumerTemplate.close();
		defaultCamelContext.close();
	}
}
