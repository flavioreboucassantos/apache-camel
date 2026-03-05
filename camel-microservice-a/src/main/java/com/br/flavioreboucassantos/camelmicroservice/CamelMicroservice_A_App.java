package com.br.flavioreboucassantos.camelmicroservice;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain // Designates this class as the main entry point
public class CamelMicroservice_A_App implements QuarkusApplication {

	@Override
	public int run(String... args) throws Exception {
		System.out.println("Doing some startup logic and then exiting...");
		// Place your application logic here
		Quarkus.waitForExit(); // Keep the application running if needed
		return 0; // Exit code
	}

	// A standard main method to launch from an IDE
	public static void main(String... args) {
		System.out.println("Running main method");
		
		Quarkus.run(CamelMicroservice_A_App.class, args);
	}

}
