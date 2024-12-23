package com.employee.service.service;

import com.employee.service.dto.EmployeeDto;
import com.employee.service.entity.Employee;

public interface EmployeeService {

    public abstract Employee addEmployee(EmployeeDto employeeDto);

    public abstract Employee getEmployeeByEmpId(int empId);

    public abstract String deleteEmployeeById(int empId);

    public abstract Employee updateByEmpId(int empId,EmployeeDto employeeDto);
}
