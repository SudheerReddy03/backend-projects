package com.employee.service.service;

import com.employee.service.dto.EmployeeDto;
import com.employee.service.entity.Employee;
import com.employee.service.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDto employeeDto;
    @BeforeEach
    void setUp() {
        employee = new Employee(1234,"vishnu",
                "developer",30000);
        employeeDto = new EmployeeDto(1234,"vishnu",
                "developer",30000);
    }

    @Test
    void testAddEmployee() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = employeeService.addEmployee(employeeDto);

        assertNotNull(savedEmployee);
        assertEquals(employeeDto.getEmpId(), savedEmployee.getEmpId());
        assertEquals(employeeDto.getEmpName(), savedEmployee.getEmpName());
        assertEquals(employeeDto.getEmpDesg(), savedEmployee.getEmpDesg());
        assertEquals(employeeDto.getEmpSalary(), savedEmployee.getEmpSalary());

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testAddEmployee_Exception(){
        doThrow(new RuntimeException("Database error")).when(employeeRepository).save(employee);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->{
           employeeService.addEmployee(employeeDto);
        });
        assertEquals("Failed to add employee",exception.getMessage());
        verify(employeeRepository,times(0)).save(null);
    }

    @Test
    void testGetEmployeeByEmpId() {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));

        Employee fetchedEmployee = employeeService.getEmployeeByEmpId(anyInt());

        assertNotNull(fetchedEmployee);
        assertEquals(employee.getEmpId(), fetchedEmployee.getEmpId());
        assertEquals(employee.getEmpName(), fetchedEmployee.getEmpName());
        assertEquals(employee.getEmpDesg(), fetchedEmployee.getEmpDesg());
        assertEquals(employee.getEmpSalary(), fetchedEmployee.getEmpSalary());
        assertEquals(employee.getEmpName(), fetchedEmployee.getEmpName());
        verify(employeeRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetEmployeeByEmpIdNotFound() {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            employeeService.getEmployeeByEmpId(anyInt());
        });

        assertEquals("Employee with ID "+employee.getEmpId()+" not found",
                "Employee with ID "+employee.getEmpId()+" not found");
        verify(employeeRepository, times(1)).findById(anyInt());
    }

    @Test
    void testDeleteEmployeeById() {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.ofNullable(employee));
        doNothing().when(employeeRepository).deleteById(anyInt());

        String result = employeeService.deleteEmployeeById(anyInt());

        assertEquals("Employee deleted successfully", result);
        verify(employeeRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteEmployeeById_NotFound(){
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->{
            employeeService.deleteEmployeeById(anyInt());
        });
        assertEquals("Employee records not found for ID: "+employee.getEmpId(),
                "Employee records not found for ID: "+employee.getEmpId());
        verify(employeeRepository,times(0)).deleteById(anyInt());
    }

    @Test
    void testUpdateByEmpId() {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee updatedEmployee = employeeService.updateByEmpId(anyInt(), employeeDto);

        assertNotNull(updatedEmployee);
        assertEquals(employeeDto.getEmpId(), updatedEmployee.getEmpId());
        assertEquals(employeeDto.getEmpName(), updatedEmployee.getEmpName());
        assertEquals(employeeDto.getEmpDesg(), updatedEmployee.getEmpDesg());
        assertEquals(employeeDto.getEmpSalary(), updatedEmployee.getEmpSalary());

        verify(employeeRepository, times(1)).findById(anyInt());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateByEmpIdNotFound() {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            employeeService.updateByEmpId(anyInt(), employeeDto);
        });

        assertEquals("Employee with ID "+employee.getEmpId()+" not found",
                "Employee with ID "+employee.getEmpId()+" not found");
        verify(employeeRepository, times(1)).findById(anyInt());
    }
}
