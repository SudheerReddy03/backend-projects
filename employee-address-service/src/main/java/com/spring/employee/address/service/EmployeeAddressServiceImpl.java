package com.spring.employee.address.service;

import com.spring.employee.address.Dto.Address;
import com.spring.employee.address.Dto.Employee;
import com.spring.employee.address.Dto.EmployeeAddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EmployeeAddressServiceImpl implements EmployeeAddressService {

    @Autowired
    WebClient webClient;

    //Post method
    @Override
    public Mono<Employee> addEmployee(Employee employee){
        return webClient.post()
                .uri("http://localhost:8081/employee/addEmployee")
                .bodyValue(employee)
                .retrieve()
                .onStatus(status ->  status.isError(), clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorMessage -> Mono.error(new RuntimeException(errorMessage))))
                .bodyToMono(Employee.class);
    }

    @Override
    public Mono<Address> addAddress(Address address){
        return webClient.post()
                .uri("http://localhost:8082/address/addAddress")
                .bodyValue(address)
                .retrieve()
                .onStatus(status ->  status.isError(), clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorMessage -> Mono.error(new RuntimeException(errorMessage))))
                .bodyToMono(Address.class);
    }

    @Override
    public Mono<EmployeeAddressResponse> employeeAddressResponseMono(Employee employee,Address address){
        Mono<Employee> employeeMono = addEmployee(employee);
        Mono<Address> addressMono = addAddress(address);
        return Mono.zip(employeeMono,addressMono).map(tuple ->
                new EmployeeAddressResponse(tuple.getT1(),tuple.getT2()));
    }

    //Get method
    @Override
    public Mono<Employee> getEmployeeById(int empId){
        return webClient.get()
                .uri("http://localhost:8081/employee/getEmployeeDataById/" + empId)
                .retrieve()
                .bodyToMono(Employee.class);
    }

    @Override
    public Mono<Address> getAddressByEmpId(int empId){
        return webClient.get()
                .uri("http://localhost:8082/address/getAddressById/" + empId)
                .retrieve()
                .bodyToMono(Address.class);
    }

    @Override
    public Mono<EmployeeAddressResponse> getData(int empId){
        Mono<Employee> employee = getEmployeeById(empId);
        Mono<Address> address = getAddressByEmpId(empId);
        return employee.zipWith(address, (employee1, address1) ->
                new EmployeeAddressResponse(employee1,address1));
    }

    //Delete method
    @Override
    public Mono<String> deleteEmployee(int empId){
        return webClient.delete()
                .uri("http://localhost:8081/employee/deleteEmployeeById/"+empId)
                .retrieve()
                .onStatus(status ->  status.isError(), clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorMessage -> Mono.error(new RuntimeException(errorMessage))))
                .bodyToMono(String.class);
    }
    @Override
    public Mono<String> deleteAddress(int empId){
        return webClient.delete()
                .uri("http://localhost:8082/address/deleteByEmpId/"+empId)
                .retrieve()
                .bodyToMono(String.class);
    }
    @Override
    public Mono<String> deleteEmployeeAndAddress(int empId){
        Mono<String> employee = deleteEmployee(empId);
        Mono<String> address = deleteAddress(empId);
        return Mono.zip(employee,address)
                .map(tuple -> "Employee and Address deleted successfully");
    }

    //Update
    @Override
    public Mono<Employee> updateEmployee(int empId, Employee employee) {
        return webClient.put()
                .uri("http://localhost:8081/employee/updateEmployeeDetailByEmpID/"+empId)
                .bodyValue(employee)
                .retrieve()
                .onStatus(status ->  status.isError(), clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorMessage -> Mono.error(new RuntimeException(errorMessage))))
                .bodyToMono(Employee.class);
    }

    @Override
    public Mono<Address> updateAddress(int empId, Address address) {
        return webClient.put()
                .uri("http://localhost:8082/address/updateAddressByEmpId/"+empId)
                .bodyValue(address)
                .retrieve()
                .bodyToMono(Address.class);
    }

    @Override
    public Mono<EmployeeAddressResponse> updateEmployeeAndAddress(int empId, Employee employee, Address address) {
        Mono<Employee> employeeMono = updateEmployee(empId,employee);
        Mono<Address> addressMono = updateAddress(empId,address);
        return Mono.zip(employeeMono,addressMono).map(tuple ->
                new EmployeeAddressResponse(tuple.getT1(),tuple.getT2()));
    }

}
