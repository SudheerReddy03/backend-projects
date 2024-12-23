package com.spring.employee.address.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.spring.employee.address.Dto.Address;
import com.spring.employee.address.Dto.Employee;
import com.spring.employee.address.Dto.EmployeeAddressResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WebMvcTest(EmployeeAddressController.class)
@ComponentScan(basePackages = "com.spring.employee.address.service")
class EmployeeAddressControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private WireMockServer wireMockServer;
    private Employee employee;
    private Address address;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(9002);
        wireMockServer.start();
        configureFor("localhost",9002);

        objectMapper = new ObjectMapper();
        employee = new Employee(1234,"Bharath",
                "Developer",25000);
        address = new Address(0,"ponnuru","guntur",
                "AP",556688,1234);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void addEmployeeAddress() throws Exception {
        EmployeeAddressResponse response = new EmployeeAddressResponse(employee,address);
        String responseJson = objectMapper.writeValueAsString(response);

        wireMockServer.stubFor(post(urlEqualTo("/employee-address/addEmployeeAddress"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseJson)));

        webTestClient.post()
                .uri("/employee-address/addEmployeeAddress")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(response)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(EmployeeAddressResponse.class)
                .isEqualTo(response);
    }

    @Test
    void getDataById() throws Exception {
        EmployeeAddressResponse response = new EmployeeAddressResponse(employee, address);
        String responseJson = objectMapper.writeValueAsString(response);

        wireMockServer.stubFor(get(urlEqualTo("/employee-address/getEmployeeAddressData/1234"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseJson)));

        webTestClient.get()
                .uri("/employee-address/getEmployeeAddressData/1234")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(EmployeeAddressResponse.class)
                .isEqualTo(response);
    }

    @Test
    void deleteData() {
        wireMockServer.stubFor(delete(urlEqualTo("/employee-address/deleteByEmpId/1234"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Employee and Address deleted successfully")));

        webTestClient.delete()
                .uri("/employee-address/deleteByEmpId/1234")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("Employee and Address deleted successfully");
    }

    @Test
    void updateData() throws Exception {
        EmployeeAddressResponse response = new EmployeeAddressResponse(employee,address);
        String responseJson = objectMapper.writeValueAsString(response);

        wireMockServer.stubFor(put(urlEqualTo("/employee-address/updateByEmpId/1234"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseJson)));

        webTestClient.put()
                .uri("/employee-address/updateByEmpId/1234")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(response)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(EmployeeAddressResponse.class)
                .isEqualTo(response);
    }

}