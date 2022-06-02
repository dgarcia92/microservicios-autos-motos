package com.usuario.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
	@LoadBalanced  //Se añade el balanceo de carga, este micro llama a los demas micros
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
