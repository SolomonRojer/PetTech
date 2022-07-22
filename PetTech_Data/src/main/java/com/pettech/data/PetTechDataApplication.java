package com.pettech.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.pettech")
public class PetTechDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetTechDataApplication.class, args);
	}

}
