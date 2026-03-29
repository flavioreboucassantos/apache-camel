package com.br.flavioreboucassantos.camel_consumes_whatsapp_webhook;

import org.apache.camel.quarkus.main.CamelMainApplication;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class CamelConsumesWhatsAppWebHook {

	static public void main(String[] args) throws Exception {
		/*
		 * Attention:
		 * 
		 * - The `quarkusApplication` parameter of `Quarkus.run` should be `CamelMainApplication.class`.
		 * 
		 */
		Quarkus.run(CamelMainApplication.class, args);
	}
}
