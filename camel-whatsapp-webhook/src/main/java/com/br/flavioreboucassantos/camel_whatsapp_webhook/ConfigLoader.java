package com.br.flavioreboucassantos.camel_whatsapp_webhook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
	private Properties properties = new Properties();

	public ConfigLoader() {
		// Carrega o arquivo application.properties do classpath
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
			if (input == null) {
				System.out.println("Desculpe, arquivo application.properties não encontrado.");
				return;
			}
			properties.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
