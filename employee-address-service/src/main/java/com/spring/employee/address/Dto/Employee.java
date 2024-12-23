package com.spring.employee.address.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private int empId;
    private String empName;
    private String empDesg;
    private double empSalary;

}
