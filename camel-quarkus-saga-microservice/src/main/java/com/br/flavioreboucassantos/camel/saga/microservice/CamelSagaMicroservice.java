package com.br.flavioreboucassantos.camel.saga.microservice;

import org.apache.camel.quarkus.main.CamelMainApplication;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain // Designates this class as the main entry point
public class CamelSagaMicroservice {

	public static void main(String[] args) {
		System.out.println("Running main method");

		Quarkus.run(CamelMainApplication.class, args);
	}

}
