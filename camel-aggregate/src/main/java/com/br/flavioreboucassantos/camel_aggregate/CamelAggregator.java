package com.br.flavioreboucassantos.camel_aggregate;

import java.time.Instant;

import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelAggregator {

	static final private String nameParamCorrelationId = "correlation_id";

	static final private Processor processorProduceByTimer = (exchange) -> {
		// simula a produção de exchange com header(nameParamCorrelationId)
		final Message message = exchange.getMessage();
		// define DINAMICAMENTE um valor para header(nameParamCorrelationId)
		message.setHeader(nameParamCorrelationId, "1");
		message.setBody(Instant.now().toEpochMilli());
	};

	public static void main(String[] args) throws Exception {
		/*
		 * STRUCTURING CAMEL ROUTE & CONTEXT
		 */
		final RouteBuilder routeBuilder = new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("timer:timerProducer?period=500") // Deve concluir 3 agregações a cada 1500ms
						.process(processorProduceByTimer)
						/*
						 * o valor de correlationExpression define a CHAVE DE COLETA E ENTREGA para
						 * realizar a aggregationStrategy
						 */
						.aggregate(header(nameParamCorrelationId), new MyAggregationStrategy())
						/*
						 * continua a pipeline a cada 3 agregações para cada correlationExpression
						 */
						.completionSize(3)
						.log("header." + nameParamCorrelationId + " = ${header." + nameParamCorrelationId + "}")
						.log("body ---> ${body}");
			}
		};

		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		defaultCamelContext.start();
		defaultCamelContext.addRoutes(routeBuilder);

		// MANTÉM A APLICAÇÃO RODANDO
		while (true)
			Thread.sleep(200);
	}
}
