package com.spring.employee.address.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.spring.employee.address.Dto.Address;
import com.spring.employee.address.Dto.Employee;
import com.spring.employee.address.EmployeeAddressServiceApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(classes = EmployeeAddressServiceApplication.class)
class EmployeeAddressServiceImplTest {

    private WireMockServer wireMockServer;

    @Autowired
    private EmployeeAddressServiceImpl service;

    private Employee employee;
    private Address address;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(9000); // Port for employee service
        wireMockServer.start();
        configureFor("localhost", 9000);

        employee = new Employee(1234,"Bharath",
                "Developer",25000);
        address = new Address(0,"ponnuru","guntur",
                "AP",556644,1234);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void testAddEmployee() {
        stubFor(post(urlEqualTo("/employee/addEmployee"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"empId\" : 1234,\"empName\" : \"Bharath\"," +
                                "\"empDesg\" : \"Developer\",\"empSalary\" : 25000}")));
        Mono<Employee> result = service.addEmployee(employee);

        StepVerifier.create(result)
                .expectNextMatches(employee1 -> employee.getEmpId()==1234
                        && "Bharath".equals(employee.getEmpName())
                        && "Developer".equals(employee.getEmpDesg())
                        && employee.getEmpSalary() == 25000)
                .verifyComplete();
    }

    @Test
    void testAddEmployeeException() {
        stubFor(post(urlEqualTo("/employee/addEmployee"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\" : \"Internal Server Error\"}")));

        Mono<Employee> result = service.addEmployee(employee);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && "Failed to add employee".equals("Failed to add employee"))
                .verify();
    }

    @Test
    void testGetEmployeeById() {
        stubFor(get(urlEqualTo("/employee/getEmployeeDataById/1234"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"empId\" : 1234,\"empName\" : \"Bharath\"," +
                                "\"empDesg\" : \"Developer\",\"empSalary\" : 25000}")));

        Mono<Employee> result = service.getEmployeeById(1234);

        StepVerifier.create(result)
                .expectNextMatches(emp -> employee.getEmpId() == 1234 &&
                        "Bharath".equals(employee.getEmpName()) &&
                        "Developer".equals(employee.getEmpDesg()) &&
                        employee.getEmpSalary() == 25000)
                .verifyComplete();
    }

    @Test
    void testGetEmployeeByIdException() {
        stubFor(post(urlEqualTo("/employee/getEmployeeDataById/1234"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\" : \"Internal Server Error\"}")));

        Mono<Employee> result = service.getEmployeeById(1234);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && "Failed to get employee data".equals("Failed to get employee data"))
                .verify();
    }

    @Test
    void testDeleteEmployee() {
        stubFor(delete(urlEqualTo("/employee/deleteEmployeeById/1234"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Employee deleted successfully")));

        Mono<String> result = service.deleteEmployee(1234);

        StepVerifier.create(result)
                .expectNext("Employee deleted successfully")
                .verifyComplete();
    }

    @Test
    void testDeleteEmployeeException() {
        stubFor(post(urlEqualTo("/employee/deleteEmployeeById/1234"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\" : \"Internal Server Error\"}")));

        Mono<String> result = service.deleteEmployee(1234);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && "Error deleting employee records".equals("Error deleting employee records"))
                .verify();
    }

    @Test
    void testUpdateEmployee() {
        stubFor(put(urlEqualTo("/employee/updateEmployeeDetailByEmpID/1234"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"empId\" : 1234,\"empName\" : \"Bharath\"," +
                                "\"empDesg\" : \"Developer\",\"empSalary\" : 25000}")));

        Mono<Employee> result = service.updateEmployee(1234, employee);

        StepVerifier.create(result)
                .expectNextMatches(employee1 -> employee.getEmpId() == 1234 &&
                        "Bharath".equals(employee.getEmpName()) &&
                        "Developer".equals(employee.getEmpDesg()) &&
                        employee.getEmpSalary() == 25000)
                .verifyComplete();
    }

    @Test
    void testUpdateEmployeeException() {
        stubFor(post(urlEqualTo("/employee/updateEmployeeDetailByEmpID/1234"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\" : \"Internal Server Error\"}")));

        Mono<Employee> result = service.updateEmployee(1234,employee);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                        && "Error updating employee details".equals("Error updating employee details"))
                .verify();
    }

    @Test
    void addAddress() {
        stubFor(post(urlEqualTo("/address/addAddress"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"addressId\" : 0,\"cityName\" : \"ponnuru\"," +
                                "\"district\" : \"guntur\",\"state\" : \"AP\"," +
                                "\"zipcode\" : 556644,\"empId\" : 1234}")));
        Mono<Address> result = service.addAddress(address);
        StepVerifier.create(result)
                .expectNextMatches(address1 -> address.getAddressId() == 0 &&
                        "ponnuru".equals(address.getCityName()) &&
                        "guntur".equals(address.getDistrict()) &&
                        "AP".equals(address.getState()) &&
                        address.getZipcode() == 556644 &&
                        address.getEmpId() == 1234)
                .verifyComplete();
    }

    @Test
    void getAddressByEmpId() {
        stubFor(get(urlEqualTo("/address/getAddressById/1234"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"addressId\" : 0,\"cityName\" : \"ponnuru\"," +
                                "\"district\" : \"guntur\",\"state\" : \"AP\"," +
                                "\"zipcode\" : 556644,\"empId\" : 1234}")));
        Mono<Address> result = service.getAddressByEmpId(1234);
        StepVerifier.create(result)
                .expectNextMatches(address1 -> address.getAddressId() == 0 &&
                        "ponnuru".equals(address.getCityName()) &&
                        "guntur".equals(address.getDistrict()) &&
                        "AP".equals(address.getState()) &&
                        address.getZipcode() == 556644 &&
                        address.getEmpId() == 1234)
                .verifyComplete();
    }

    @Test
    void deleteAddress() {
        stubFor(delete(urlEqualTo("/address/deleteByEmpId/1234"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("Address deleted successfully")));
        Mono<String> result = service.deleteAddress(1234);
        StepVerifier.create(result)
                .expectNext("Address deleted successfully")
                .verifyComplete();
    }

    @Test
    void updateAddress() {
        stubFor(put(urlEqualTo("/address/updateAddressByEmpId/1234"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"addressId\" : 0,\"cityName\" : \"ponnuru\"," +
                                "\"district\" : \"guntur\",\"state\" : \"AP\"," +
                                "\"zipcode\" : 556644,\"empId\" : 1234}")));
        Mono<Address> result = service.updateAddress(1234,address);
        StepVerifier.create(result)
                .expectNextMatches(address1 -> address.getAddressId() == 0 &&
                        "ponnuru".equals(address.getCityName()) &&
                        "guntur".equals(address.getDistrict()) &&
                        "AP".equals(address.getState()) &&
                        address.getZipcode() == 556644 &&
                        address.getEmpId() == 1234)
                .verifyComplete();
    }
}