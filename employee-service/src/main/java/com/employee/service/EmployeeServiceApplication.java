package com.employee.service;

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
				title = "Employee Api",
				description = "Performing Employee api operations",
				termsOfService = "Employee Terms of service",
				license = @License(name = "Employee Licence"),
				contact = @Contact(name = "Sudheer Reddy",
						url = "http://localhost:8081/employee",
						email = "chintu@gmail.com" ),
				version = "v1.0"
		)
)
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}
}
