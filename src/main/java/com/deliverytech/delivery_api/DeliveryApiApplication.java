package com.deliverytech.delivery_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Client API", version = "1.0", description = "API documentation f/ client management"))
@SpringBootApplication
public class DeliveryApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(DeliveryApiApplication.class, args);
	}
}
