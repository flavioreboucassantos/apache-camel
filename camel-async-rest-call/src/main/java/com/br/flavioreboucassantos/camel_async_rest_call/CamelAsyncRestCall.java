package com.br.flavioreboucassantos.camel_async_rest_call;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelAsyncRestCall {

	static final private String uriHttps = "economia.awesomeapi.com.br";
	static final private String param1 = "last/USD-BRL";

	static final private void printWipe(final Object o) {
		System.out.println("");
		System.out.println(o);
	}

	static final private void printWipe2(final Object o) {
		System.out.println("");
		System.out.println(o);
		System.out.println("");
	}

	static final private void produceSynchronously(final ProducerTemplate producerTemplate) {
		/*
		 * makes a call synchronously
		 */
		Map<String, Object> headers = new HashMap<>();
		headers.put("uriHttps", uriHttps);
		headers.put("param1", param1);
		producerTemplate.sendBodyAndHeaders("direct:agentOfGET", "ENTRADA DO CORPO PELO sendBodyAndHeaders", headers);
		printWipe("MAIN THREAD PAUSED - MENSAGEM DEPOIS DO PROCESSAMENTO DA ROTA");
	}

	static final private void produceAsynchronous(final ProducerTemplate producerTemplate)
			throws InterruptedException, ExecutionException {
		/*
		 * makes an asynchronous call
		 */
		CompletableFuture<Exchange> completableFuture = producerTemplate.asyncSend("direct:agentOfGET", new AsyncProcessor(uriHttps, param1));

		completableFuture.thenAccept(resultExchange -> {
			if (resultExchange.isFailed()) {
				Exception e = resultExchange.getException();
				e.printStackTrace();
			} else {
				String responseBody = resultExchange.getIn().getBody(String.class);
				printWipe("Exibir BODY retornado no resultExchange do CompletableFuture do producerTemplate.asyncSend");
				printWipe2(responseBody);
			}
		});

		printWipe("MAIN THREAD CONTINUA - MENSAGEM ANTES DO PROCESSAMENTO DA ROTA E REQUISIÇÃO GET");
	}

	public static void main(String[] args) throws Exception {

		/*
		 * STRUCTURING ROUTE
		 */
		printWipe2("STRUCTURING ROUTE");

		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				/*
				 * Exemplo 1
				 */
				from("direct:agentOfGET")
						.log("${body}") // EXPÕE O CORPO DA ENTRADA
						.setHeader(Exchange.HTTP_METHOD, constant("GET"))
						.toD("https://${header.uriHttps}/${header.param1}") // ENVIA REQUISIÇÃO ASSÍNCRONA GET - RECEBE NOVO HEADER & NOVO BODY
						.setBody(simple("CORPO RETORNADO -> ${body}")) // PRECISA CHAMAR ANTES DE .to("seda:agentOfGET") PARA PROCESSAR EM SEQUÊNCIA
						.to("seda:agentOfGET") // ENVIA HEADER & BODY PARA seda:agentOfGET
						.process(p -> printWipe2("Debug BreakPoint Here with message.body=" + p.getIn().getBody(String.class)))
						.log("seda:agentOfGET CONSUMIDA - PARAMETROS RECEBIDOS :: ((uriHttps=${header.uriHttps})) :: ((param1=${header.param1}))");
			}
		};

		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		defaultCamelContext.setMessageHistory(true);
		defaultCamelContext.setTracing(true); // Ativar o rastreamento no contexto detalha o fluxo de mensagens
		defaultCamelContext.start();
		defaultCamelContext.addRoutes(routeBuilder);

		final ProducerTemplate producerTemplate = defaultCamelContext.createProducerTemplate();

		final ConsumerTemplate consumerTemplate = defaultCamelContext.createConsumerTemplate();

		/*
		 * USING PRODUCER
		 */
		printWipe2("USING PRODUCER");

//		produceSynchronously(producerTemplate);
		produceAsynchronous(producerTemplate);

		/*
		 * USING CONSUMER Synchronously
		 */
		printWipe2("USING CONSUMER Synchronously");

		// Receives from the endpoint, waiting until there is a response
		final String receiveBody = consumerTemplate.receiveBody("seda:agentOfGET", String.class);

		printWipe("Exibir BODY consumido no ENDPOINT seda:agentOfGET");
		printWipe2(receiveBody);

		/*
		 * CLOSE CAMEL CONTEXT
		 */
		defaultCamelContext.close();
	}
}
