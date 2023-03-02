package com.example.rqchallenge.employees.services;

import com.example.rqchallenge.employees.exceptions.EmployeeCreationException;
import com.example.rqchallenge.employees.exceptions.InvalidInputException;
import com.example.rqchallenge.employees.models.Employee;
import com.example.rqchallenge.employees.repositories.EmployeeRepositoryImpl;
import com.example.rqchallenge.employees.util.EmployeeTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.rqchallenge.employees.util.EmployeeTestUtils.TEST_EMPLOYEE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @InjectMocks
    EmployeeServiceImpl employeeService;
    @Mock
    EmployeeRepositoryImpl employeeRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> response = employeeService.getAllEmployees();

        assertEquals(response.size(), employees.size());
    }

    @Test
    void testGetAllEmployeesWhenExceptionIsThrown() {
        when(employeeRepository.findAll()).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class , () -> employeeService.getAllEmployees());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testCreateEmployeeWhenCreationFailed() {
        Map<String, Object> objectMap = EmployeeTestUtils.getCreateEmployeeRequest();

        Employee e = new Employee();
        e.setId(25);
        e.setName("demo");
        e.setAge(66);
        e.setSalary(86000);
        when(employeeRepository.save(e)).thenReturn(null);

        assertThrows(EmployeeCreationException.class, () -> employeeService.createEmployee(objectMap));
        verify(employeeRepository, times(1)).save(any());
    }

    @Test
    void testCreateEmployeeWhenInputIncorrect() {
        Map<String, Object> objectMap = EmployeeTestUtils.getCreateEmployeeRequest();
        objectMap.remove("name");

        assertThrows(InvalidInputException.class, () -> employeeService.createEmployee(objectMap));
        verify(employeeRepository, times(0)).save(any());
    }

    @Test
    void testDeleteEmployeeById() {
        when(employeeRepository.existsById(TEST_EMPLOYEE_ID)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(TEST_EMPLOYEE_ID);

        String expectedResult = employeeService.deleteEmployeeById(TEST_EMPLOYEE_ID);
        assertEquals(expectedResult , "Employee with ID 3 has been deleted.");
    }

    @Test
    void testDeleteEmployeeForIncorrectId() {
        when (employeeRepository.existsById(TEST_EMPLOYEE_ID)).thenReturn(false);
        String expectedResult = employeeService.deleteEmployeeById(TEST_EMPLOYEE_ID);

        assertEquals(expectedResult, "Employee with ID 3 not exist.");
        verify(employeeRepository, times(0)).deleteById(TEST_EMPLOYEE_ID);
    }


}
