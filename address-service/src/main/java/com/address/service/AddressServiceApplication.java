package com.address.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(
		info = @Info(
				title = "Address Api",
				description = "Performing Address api operations",
				termsOfService = "Address Terms of service",
				license = @License(name = "Address Licence"),
				contact = @Contact(name = "Sudheer Reddy",
						url = "http://localhost:8082/address",
						email = "chintu@gmail.com" ),
				version = "v1.0"
		)
)
public class AddressServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressServiceApplication.class, args);
	}

}
