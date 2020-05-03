package br.com.higor.vendas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {

	@Bean(name="applicationName")
	public String applicationName() {
		return "Sistema de Vendas";
	}
	
	
}
