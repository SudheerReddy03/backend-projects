package com.employee.service.controller;

import com.employee.service.dto.EmployeeDto;
import com.employee.service.entity.Employee;
import com.employee.service.service.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private WireMockServer wireMockServer;
    @MockBean
    private EmployeeServiceImpl service;
    private Employee employeeOne;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(9000);
        wireMockServer.start();
        WireMock.configureFor("localhost",9000);

        employeeOne = new Employee(1234,"vishnu",
                "developer",30000);
        employeeDto = new EmployeeDto(1234,"vishnu",
                "developer",30000);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void createData() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ob = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ob.writeValueAsString(employeeOne);


        when(service.addEmployee(employeeDto)).thenReturn(employeeOne);
        this.mockMvc.perform(post("/employee/addEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    void getDataById() throws Exception {
        when(service.getEmployeeByEmpId(1234)).thenReturn(employeeOne);
        this.mockMvc.perform(get("/employee/getEmployeeDataById/1234"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteEmpData() throws Exception {
        when(service.deleteEmployeeById(1234)).thenReturn("Deleted successfully");
        this.mockMvc.perform(delete("/employee/deleteEmployeeById/1234"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updateEmployeeDetailsById() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ob = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ob.writeValueAsString(employeeOne);

        when(service.updateByEmpId(1234,employeeDto)).thenReturn(employeeOne);
        this.mockMvc.perform(put("/employee/updateEmployeeDetailByEmpID/1234")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }
}