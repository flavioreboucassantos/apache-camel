package com.br.flavioreboucassantos.camelmicroservice;

import org.apache.camel.quarkus.main.CamelMainApplication;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain // Designates this class as the main entry point
public class CamelMicroservice_A_App {

	// A standard main method to launch from an IDE
	public static void main(String... args) {
		System.out.println("Running main method");

		Quarkus.run(CamelMainApplication.class, args);
	}

}
