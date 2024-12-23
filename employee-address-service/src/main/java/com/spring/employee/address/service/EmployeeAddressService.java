package com.spring.employee.address.service;

import com.spring.employee.address.Dto.Address;
import com.spring.employee.address.Dto.Employee;
import com.spring.employee.address.Dto.EmployeeAddressResponse;
import reactor.core.publisher.Mono;

public interface EmployeeAddressService {

    public Mono<Employee> addEmployee(Employee employee);
    public Mono<Address> addAddress(Address address);
    public Mono<EmployeeAddressResponse> employeeAddressResponseMono(Employee employee, Address address);
    public Mono<Employee> getEmployeeById(int empId);
    public Mono<Address> getAddressByEmpId(int empId);
    public Mono<EmployeeAddressResponse> getData(int empId);
    public Mono<String> deleteEmployee(int empId);
    public Mono<String> deleteAddress(int empId);
    public Mono<String> deleteEmployeeAndAddress(int empId);
    public Mono<Employee> updateEmployee(int empId,Employee employee);
    public Mono<Address> updateAddress(int empId,Address address);
    public Mono<EmployeeAddressResponse> updateEmployeeAndAddress(int empId,Employee employee,Address address);
}
