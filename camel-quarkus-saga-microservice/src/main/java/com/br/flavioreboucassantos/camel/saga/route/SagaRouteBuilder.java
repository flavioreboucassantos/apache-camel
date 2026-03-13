package com.br.flavioreboucassantos.camel.saga.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public final class SagaRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:bookTrip").
		log("MEU BODY! ===>> ${body}");
		/*
		// Rota principal que inicia a Saga
		from("direct:bookTrip")
				.saga() // Define o escopo da transação longa.
				// Controla como o contexto da Saga é transmitido entre rotas (ex: REQUIRES_NEW, MANDATORY)
				.propagation(SagaPropagation.REQUIRES_NEW) // Inicia nova Saga
				.to("direct:reserveHotel")
//				.to("direct:reserveFlight")
				.log("Reserva de viagem concluída com sucesso!");

		// Participante 1: Reserva de Hotel
		from("direct:reserveHotel")
				.saga() // Define o escopo da transação longa.
				// Define a rota que será executada caso a Saga falhe e precise "desfazer" ações anteriores.
				.compensation("direct:cancelHotel") // Rota de compensação se falhar depois
				.log("Reservando hotel...")
				.to("http://localhost:8080/hotel-service/book");

		// Participante 2: Reserva de Voo
//		from("direct:reserveFlight")
//				.saga()
//				.compensation("direct:cancelFlight")
//				.log("Reservando voo...")
//				.to("http://localhost:8080/flight-service/book");

		// Rotas de Compensação (Chamadas automaticamente em caso de erro)
		from("direct:cancelHotel")
				.log("Compensando: Cancelando reserva de hotel")
				.to("http://localhost:8080/hotel-service/cancel");

//		from("direct:cancelFlight")
//				.log("Compensando: Cancelando reserva de voo")
//				.to("http://localhost:8080/flight-service/cancel");
 */
	}

}
