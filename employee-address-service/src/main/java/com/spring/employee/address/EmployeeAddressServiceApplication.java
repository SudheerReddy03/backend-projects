package com.spring.employee.address;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(
		info = @Info(
				title = "Employee-Address Api",
				description = "Performing Employee-Address api operations",
				termsOfService = "Employee-Address Terms of service",
				license = @License(name = "Employee-Address Licence"),
				contact = @Contact(name = "Sudheer Reddy",
						url = "http://localhost:8083/employee-address",
						email = "chintu@gmail.com" ),
				version = "v1"
		)
)
public class EmployeeAddressServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeAddressServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public WebClient webClient(){
		return WebClient.builder().build();
	}
}
