package com.address.service.controller;

import com.address.service.dto.AddressDto;
import com.address.service.entity.Address;
import com.address.service.service.AddressServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@Tag(name = "Address", description = "The Address API")
public class AddressController {

    @Autowired
    private AddressServiceImpl service;

    @PostMapping("/addAddress")
    @Operation(summary = "Add a new Address data",
            description = "Provide necessary details to add a new Address data")
    public ResponseEntity<Address> createAddress(@RequestBody AddressDto addressDto){
        try{
            Address addr = service.addAddress(addressDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(addr);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getAddressById/{empId}")
    @Operation(summary = "Get Address data by Employee ID",
            description = "Provide an valid Employee ID to get the specific Address data")
    public ResponseEntity<Address> getAddress(@PathVariable int empId){
        try{
            Address address = service.getAddressByEmpId(empId);
            if (address != null){
                return ResponseEntity.status(HttpStatus.OK).body(address);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deleteByEmpId/{empId}")
    @Operation(summary = "Delete address by Employee ID",
            description = "Provide an valid Employee ID to delete the corresponding Address data")
    public ResponseEntity<String> deleteAddress(@PathVariable("empId") int empId){
        try {
            String addr = service.deleteAddressByEmpID(empId);
            return ResponseEntity.status(HttpStatus.OK).body(addr);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateAddressByEmpId/{empId}")
    @Operation(summary = "Update address by Employee ID",
            description = "Provide an valid Employee ID to update the corresponding Address data")
    public ResponseEntity<Address> updateAddress(@PathVariable int empId,@RequestBody AddressDto addressDto){
        try {
            Address address1 = service.updateAddressByEmpId(empId,addressDto);
            return ResponseEntity.status(HttpStatus.OK).body(address1);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
