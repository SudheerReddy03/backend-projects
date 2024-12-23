package com.employee.service.service;

import com.employee.service.dto.EmployeeDto;
import com.employee.service.entity.Employee;
import com.employee.service.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public Employee addEmployee(EmployeeDto employeeDto) {
        try {
            Employee employee = new Employee();
            employee.setEmpId(employeeDto.getEmpId());
            employee.setEmpName(employeeDto.getEmpName());
            employee.setEmpDesg(employeeDto.getEmpDesg());
            employee.setEmpSalary(employeeDto.getEmpSalary());

            log.info("Employee added successfully: {}", employee);
            return repository.save(employee);
        } catch (Exception e) {
            log.error("Failed to add employee: {}", e);
            throw new RuntimeException("Failed to add employee", e);
        }
    }

    @Override
    public Employee getEmployeeByEmpId(int empId) {
        try {
            Optional<Employee> employeeOptional = repository.findById(empId);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                log.info("Employee with ID {} fetched successfully", empId);
                return employee;
            } else {
                log.warn("Employee with ID {} not found", empId);
                throw new RuntimeException("Employee with ID " + empId + " not found");
            }
        } catch (Exception e) {
            log.error("Failed to get employee data for ID {}", empId, e);
            throw new RuntimeException("Failed to get employee data", e);
        }
    }

    @Override
    public String deleteEmployeeById(int empId) {
        Optional<Employee> employeeOptional = repository.findById(empId);
        try {
            if (employeeOptional.isPresent()) {
                repository.deleteById(empId);
                log.info("Deleted employee records for employee ID: {}", empId);
                return "Employee deleted successfully";
            } else {
                log.warn("Employee records not found for ID: {}", empId);
                throw new RuntimeException("Employee records not found for ID: " + empId);
            }
        } catch (Exception e) {
            log.error("Error occurred while deleting employee records for employee ID {}", empId, e);
            throw new RuntimeException("Error deleting employee records for ID: " + empId, e);
        }
    }

    @Override
    public Employee updateByEmpId(int empId, EmployeeDto employeeDto) {
        try {
            Optional<Employee> optionalEmployee = repository.findById(empId);
            if (optionalEmployee.isPresent()) {
                Employee existingEmployee = optionalEmployee.get();
                existingEmployee.setEmpName(employeeDto.getEmpName());
                existingEmployee.setEmpDesg(employeeDto.getEmpDesg());
                existingEmployee.setEmpSalary(employeeDto.getEmpSalary());

                Employee updatedEmployee = repository.save(existingEmployee);
                log.info("Updated employee details for employee ID: {}", empId, updatedEmployee);
                return updatedEmployee;
            } else {
                log.warn("Employee not found with ID {} to update", empId);
                throw new RuntimeException("Employee with ID {} " + empId + " not found");
            }
        } catch (Exception e) {
            log.error("Error occurred while updating employee details for employee ID: {}", empId, e);
            throw new RuntimeException("Error updating employee details", e);
        }
    }
}
