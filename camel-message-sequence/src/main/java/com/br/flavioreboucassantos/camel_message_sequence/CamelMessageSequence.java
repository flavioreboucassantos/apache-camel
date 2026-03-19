package com.br.flavioreboucassantos.camel_message_sequence;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.config.BatchResequencerConfig;
import org.apache.camel.model.config.StreamResequencerConfig;

public class CamelMessageSequence {

	static final private void printWipe(final Object o) {
		System.out.println("");
		System.out.println(o);
	}

	static final private void printWipe2(final Object o) {
		System.out.println("");
		System.out.println(o);
		System.out.println("");
	}

	public static void main(String[] args) throws Exception {
		/*
		 * STRUCTURING CAMEL ROUTE & CONTEXT
		 */
		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {

				// Batch (Lote): Processa grandes volumes de dados de uma só vez, em intervalos programados.
				// Batch: Agrupa mensagens, ordena e envia o lote. Requer um ponto final para saber quando o lote "fecha" (via tempo ou tamanho).

				BatchResequencerConfig configBatchSize3 = new BatchResequencerConfig();
				configBatchSize3.setBatchSize("3"); // Define o tamanho máximo do lote. O resequencer aguardará chegar esta quantidade de mensagens antes de ordená-las e enviá-las.
				configBatchSize3.setBatchTimeout("4000"); // Define o tempo em milissegundos que o resequencer espera para receber mensagens. Se o tempo esgotar, ele envia o que foi recebido até então, mesmo que o size não tenha sido atingido
				from("direct:batchSize3")
						.resequence(header("index"))
						.batch(configBatchSize3)
						.log("B A T C H ::: ${body}")
						.to("mock:result");

				// Stream (Fluxo): Processa eventos ou dados individualmente e em tempo real (ou próximo disso) assim que chegam.
				// Stream (Resequence EIP): Ordena mensagens continuamente, útil para fluxo de mensagens em tempo real onde a latência de agrupamento não é desejada.

				StreamResequencerConfig configStreamCapacity3 = new StreamResequencerConfig();
				configStreamCapacity3.setCapacity("3"); // Define o número máximo de mensagens que o resequenciador pode manter na memória enquanto espera mensagens faltantes.
				configStreamCapacity3.setTimeout("5000"); // O tempo em milissegundos que o resequenciador aguarda uma mensagem faltante antes de enviar as que já possui.
				from("direct:streamCapacity3")
						.resequence(header("index"))
						.stream(configStreamCapacity3)
						.log("S T R E A M ::: ${body}")
						.to("mock:result");

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
		printWipe2("USING PRODUCER");

		final int limit = 3;
		final String messages[] = new String[limit];
		final Map<String, Object> headers = new HashMap<>();

		/*
		 * Prepare Data
		 */
		for (int i = 0; i < limit; i++) // 0, 1, 2
			messages[i] = "message " + i + " of " + limit;

		headers.put("limit", limit);

		/*
		 * Send Batch
		 */
		printWipe2("SEND BATCH");
		Thread.sleep(1500);
		for (int i = limit - 1; i >= 0; i--) { // 2, 1, 0
			headers.put("index", i);
			producerTemplate.sendBodyAndHeaders("direct:batchSize3", messages[i], headers);
		}

		Thread.sleep(10000);

		/*
		 * Send Stream
		 */
		printWipe2("SEND STREAM");
		Thread.sleep(1500);
		for (int i = limit - 1; i >= 0; i--) { // 2, 1, 0
			headers.put("index", i);
			producerTemplate.sendBodyAndHeaders("direct:streamCapacity3", messages[i], headers);
			Thread.sleep(1250); // Tente ser mais rapido que a configuração de timeout para incluir na ordenação
		}
		printWipe2("O tempo em milissegundos que o resequenciador aguarda uma mensagem faltante antes de enviar as que já possui.");

		/*
		 * Closes All
		 */		
		Thread.sleep(7000); // Espera um pouco mais que o timeout do stream
		printWipe2("CLOSES ALL");
		producerTemplate.close();
		consumerTemplate.close();
		defaultCamelContext.close();
	}
}
