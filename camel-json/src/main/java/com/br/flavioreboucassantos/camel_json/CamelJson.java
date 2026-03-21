package com.br.flavioreboucassantos.camel_json;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.impl.DefaultCamelContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CamelJson {

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

	public static void main(String[] args) throws Exception {
		/*
		 * STRUCTURING CAMEL ROUTE & CONTEXT
		 */

		final RouteBuilder routeBuilder1 = new RouteBuilder() {

			long nsStart;

			@Override
			public void configure() throws Exception {				
				final JacksonDataFormat jsonDataFormat = new JacksonDataFormat(JSONEmployees.class);

				from("file:file-employees1?include=.*\\.json&noop=true") // Lê o arquivo JSON da pasta file-employees sem mover ou remover
						.log("Lendo arquivo: ${file:name}")
						.process(ex -> {
							nsStart = System.nanoTime();
						})
						.unmarshal(jsonDataFormat)
						.process(ex -> {
							print("------- VERSÃO COM unmarshal de JacksonDataFormat e JSONEmployees.class");
							final JSONEmployees jsonEmployees = ex.getIn().getBody(JSONEmployees.class);
							for (JSONEmployee jsonEmployee : jsonEmployees.employees)
								print(jsonEmployee.toString());
						})
						.process(ex -> {
							printWipe3("Ns Time To Complete 1: " + String.format("%,d", (System.nanoTime() - nsStart)));
						})
						.end();
			}
		};

		final RouteBuilder routeBuilder2 = new RouteBuilder() {

			long nsStart;
			int countMessages;

			@Override
			public void configure() throws Exception {
				final ObjectMapper mapper = new ObjectMapper();

				from("file:file-employees2?include=.*\\.json&noop=true") // Lê o arquivo JSON da pasta file-employees sem mover ou remover
						.log("Lendo arquivo: ${file:name}")
						.process(ex -> {
							print("------- VERSÃO COM .split().jsonpath e JsonNode");
							nsStart = System.nanoTime();
						})
						.split().jsonpath("$.employees[*]")
						.process(ex -> {
							JsonNode node = ex.getIn().getBody(JsonNode.class);
							JSONEmployee jsonEmployee = mapper.treeToValue(node, JSONEmployee.class);
							print(jsonEmployee.toString());
						})
						.process(ex -> {
							if (++countMessages == 4) {
								printWipe3("Ns Time To Complete 2: " + String.format("%,d", (System.nanoTime() - nsStart)));
								countMessages = 0;
							}
						})
						.end();
			}
		};

		final DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		defaultCamelContext.start();

		defaultCamelContext.addRoutes(routeBuilder1);
		Thread.sleep(2500);

		defaultCamelContext.addRoutes(routeBuilder2);
		Thread.sleep(2500);

		final ProducerTemplate producerTemplate = defaultCamelContext.createProducerTemplate();
		final ConsumerTemplate consumerTemplate = defaultCamelContext.createConsumerTemplate();

		printWipe("aguarda 300 segundos... (1)");
		Thread.sleep(300000);

		printWipe2("CLOSES ALL");
		producerTemplate.close();
		consumerTemplate.close();
		defaultCamelContext.close();
	}
}
