package com.medilabo.diagnosis_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class DiagnosisGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiagnosisGatewayApplication.class, args);
	}

}
