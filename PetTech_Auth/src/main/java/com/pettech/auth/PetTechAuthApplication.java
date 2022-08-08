package com.pettech.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


@PropertySources({ @PropertySource("classpath:message.properties") })
@SpringBootApplication(scanBasePackages = "com.pettech")
public class PetTechAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetTechAuthApplication.class, args);
	}

}
