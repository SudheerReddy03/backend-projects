package com.employee.service.controller;

import com.employee.service.dto.EmployeeDto;
import com.employee.service.entity.Employee;
import com.employee.service.service.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@Tag(name = "Employee", description = "The Employee API")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl service;

    @PostMapping("/addEmployee")
    @Operation(summary = "Add a new Employee data",
            description = "Provide necessary details to add a new Employee data")
    public ResponseEntity<Employee> createData(@RequestBody EmployeeDto employeeDto){
        try {
            Employee employee1 = service.addEmployee(employeeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(employee1);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getEmployeeDataById/{empId}")
    @Operation(summary = "Get Employee data by Employee ID",
            description = "Provide an valid Employee ID to get the specific Employee data")
    public ResponseEntity<Employee> getDataById(@PathVariable int empId){
        try {
            Employee employee = service.getEmployeeByEmpId(empId);
            if (employee != null){
                return ResponseEntity.ok(employee);
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

    @DeleteMapping("/deleteEmployeeById/{empId}")
    @Operation(summary = "Delete employee by Employee ID",
            description = "Provide an valid Employee ID to delete the corresponding Employee data")
    public ResponseEntity<String> deleteEmpData(@PathVariable int empId){
        try{
            String data = service.deleteEmployeeById(empId);
            return ResponseEntity.status(HttpStatus.OK).body(data);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateEmployeeDetailByEmpID/{empId}")
    @Operation(summary = "Update employee by Employee ID",
            description = "Provide an valid Employee ID to update the corresponding Employee data")
    public ResponseEntity<Employee> updateEmployeeDetailsById(@PathVariable int empId,@RequestBody EmployeeDto employeeDto){
        try {
            Employee employee1 = service.updateByEmpId(empId,employeeDto);
            return ResponseEntity.status(HttpStatus.OK).body(employee1);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
