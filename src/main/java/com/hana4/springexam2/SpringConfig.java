package com.hana4.springexam2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.persistence.EntityManager;

@Configuration
public class SpringConfig {
	private final EntityManager em;

	public SpringConfig(EntityManager em) {
		this.em = em;
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(info());
	}

	private Info info() {
		return new Info()
			.version("0.1.0")
			.title("Demo Api Spec.")
			.description("...");
	}
}
