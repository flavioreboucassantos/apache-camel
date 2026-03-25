package com.br.flavioreboucassantos.webhook_whatsapp;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class WebHookMain {

	public static void main(String[] args) {
		System.out.println("Iniciando a aplicação...");
		Quarkus.run(args); // Inicia o Quarkus
	}

}
