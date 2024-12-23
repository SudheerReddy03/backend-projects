package com.address.service.controller;

import com.address.service.dto.AddressDto;
import com.address.service.entity.Address;
import com.address.service.service.AddressServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressServiceImpl service;
    Address addressOne;
    AddressDto addressDto;

    @BeforeEach
    void setUp() {
        addressOne = new Address(1,"guntur","guntur",
                "AP",556677,1234);
        addressDto = new AddressDto(1,"guntur","guntur",
                "AP",556677,1234);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createAddress() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(addressOne);

        when(service.addAddress(addressDto)).thenReturn(addressOne);
        this.mockMvc.perform(post("/address/addAddress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    void getAddress() throws Exception {
        when(service.getAddressByEmpId(anyInt())).thenReturn(addressOne);
        this.mockMvc.perform(get("/address/getAddressById/1234"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteAddress() throws Exception {
        when(service.deleteAddressByEmpID(anyInt())).thenReturn("Deleted successfully");
        this.mockMvc.perform(delete("/address/deleteByEmpId/1234"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updateAddress() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(addressOne);

        when(service.updateAddressByEmpId(1234,addressDto)).thenReturn(addressOne);
        this.mockMvc.perform(put("/address/updateAddressByEmpId/1234")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }
}