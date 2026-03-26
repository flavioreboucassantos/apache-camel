package com.br.flavioreboucassantos.camel_whatsapp_webhook;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class CamelWhatsAppWebHook {

	static public void main(String[] args) throws Exception {
		Quarkus.run(args);
	}
}
