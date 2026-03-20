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
				configBatchSize3.setBatchTimeout(String.valueOf(1000 * 60 * 10)); // Define o tempo em milissegundos que o resequencer espera para receber mensagens. Se o tempo esgotar, ele envia o que foi recebido até então, mesmo que o size não tenha sido atingido.
				from("direct:batchSize3")
						.resequence(header("index"))
						.batch(configBatchSize3)
						.log("B A T C H ::: ${body}")
						.to("mock:result");

				// Stream (Fluxo): Processa eventos ou dados individualmente e em tempo real (ou próximo disso) assim que chegam.
				// Stream (Resequence EIP): Ordena mensagens continuamente, útil para fluxo de mensagens em tempo real onde a latência de agrupamento não é desejada.

				StreamResequencerConfig configStreamCapacity2 = new StreamResequencerConfig();
				configStreamCapacity2.setCapacity("2"); // Define o número máximo de mensagens que o resequenciador pode manter na memória enquanto espera mensagens faltantes.
				configStreamCapacity2.setTimeout("1000"); // O tempo em milissegundos que o resequenciador aguarda uma mensagem faltante antes de enviar as que já possui.
				from("direct:streamCapacity2")
						.resequence(header("index"))
						.stream(configStreamCapacity2)
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

		/*
		 * PREPARE DATA
		 */
		final int limit = 6;
		final String messages[] = new String[limit];
		final Map<String, Object> headers = new HashMap<>();

		for (int i = 1; i <= limit; i++) // 1, 2, 3, 4, 5, 6
			messages[i - 1] = "message " + i + " of " + limit;

		/*
		 * Send Batch
		 */
		printWipe2("SEND BATCH");
		Thread.sleep(1500);
		for (int i = 6; i > 0; i--) { // 6, 5, 4, 3, 2, 1
			headers.put("index", i);
			producerTemplate.sendBodyAndHeaders("direct:batchSize3", messages[i - 1], headers);
			Thread.sleep(250);
		}		
		
		printWipe("aguarda 10 segundos... (1)");
		Thread.sleep(10000);

		/*
		 * Send Stream
		 */
		printWipe2("SEND STREAM");
		Thread.sleep(1500);
		for (int i = 6; i > 0; i--) { // 6, 5, 4, 3, 2, 1
			headers.put("index", i);
			producerTemplate.sendBodyAndHeaders("direct:streamCapacity2", messages[i - 1], headers);
			Thread.sleep(250);
		}
		Thread.sleep(3000); // Aguarda o stream concluir o consumo

		/*
		 * Closes All
		 */
		printWipe("aguarda 10 segundos... (2)");
		Thread.sleep(10000);
		
		printWipe2("CLOSES ALL");
		producerTemplate.close();
		consumerTemplate.close();
		defaultCamelContext.close();
	}
}
