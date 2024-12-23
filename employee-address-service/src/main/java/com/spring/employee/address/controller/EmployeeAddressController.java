package com.spring.employee.address.controller;

import com.spring.employee.address.Dto.EmployeeAddressResponse;
import com.spring.employee.address.service.EmployeeAddressServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee-address")
@Tag(name = "Employee-Address", description = "The Employee-Address API")
public class EmployeeAddressController {

    @Autowired
    private EmployeeAddressServiceImpl service;

    @PostMapping("/addEmployeeAddress")
    @Operation(summary = "Add a new Employee-Address data",
            description = "Provide necessary details to add a new Employee-Address data")
    public ResponseEntity<EmployeeAddressResponse> addEmployeeAddress(@RequestBody EmployeeAddressResponse response){
        try{
            EmployeeAddressResponse response1 =
                    service.employeeAddressResponseMono(response.getEmployee(),response.getAddress()).block();
            return ResponseEntity.status(HttpStatus.CREATED).body(response1);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getEmployeeAddressData/{empId}")
    @Operation(summary = "Get Employee-Address data by Employee ID",
            description = "Provide an valid Employee ID to get the specific Employee-Address data")
    public ResponseEntity<EmployeeAddressResponse> getDataById(@PathVariable int empId){
        try{
            EmployeeAddressResponse response = service.getData(empId).block();
            if (response == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            else {
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteByEmpId/{empId}")
    @Operation(summary = "Delete employee-address by Employee ID",
            description = "Provide an valid Employee ID to delete the corresponding Employee-Address data")
    public ResponseEntity<String> deleteData(@PathVariable int empId){
        try {
            String deleteResponse = service.deleteEmployeeAndAddress(empId).block();
            return ResponseEntity.status(HttpStatus.OK).body(deleteResponse);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateByEmpId/{empId}")
    @Operation(summary = "Update employee-address by Employee ID",
            description = "Provide an valid Employee ID to update the corresponding Employee-Address data")
    public ResponseEntity<EmployeeAddressResponse> updateData(@PathVariable int empId,@RequestBody EmployeeAddressResponse response){
        try {
            EmployeeAddressResponse response1 = service.updateEmployeeAndAddress(empId,response.getEmployee(),response.getAddress()).block();
            return ResponseEntity.status(HttpStatus.OK).body(response1);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
