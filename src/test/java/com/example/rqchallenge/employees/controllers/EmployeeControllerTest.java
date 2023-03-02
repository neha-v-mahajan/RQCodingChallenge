package com.example.rqchallenge.employees.controllers;

import com.example.rqchallenge.employees.models.Employee;
import com.example.rqchallenge.employees.services.EmployeeServiceImpl;
import com.example.rqchallenge.employees.util.EmployeeTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.rqchallenge.employees.util.EmployeeTestUtils.TEST_EMPLOYEE_ID;
import static com.example.rqchallenge.employees.util.EmployeeTestUtils.TEST_HIGHEST_SALARY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;
    @Mock
    EmployeeServiceImpl employeeService;

    List<Employee> expectedEmployeeResponseList;
    Employee expectedEmployee;
    Map<String, Object> objectMap;
    List<Employee> expectedEmployeeResponse;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        //getById
        expectedEmployeeResponseList = new ArrayList<>();
        expectedEmployee = new Employee();
                expectedEmployee.setName("Ashton Cox");
                expectedEmployee.setAge(66);
                expectedEmployee.setSalary(86000);
                expectedEmployee.setId(Integer.parseInt(TEST_EMPLOYEE_ID));
    }

    @Test
    void testGetAllEmployees(){

        ResponseEntity<List<Employee>> expected = new ResponseEntity<>(expectedEmployeeResponseList, null , OK);
        ResponseEntity<List<Employee>> actual = employeeController.getAllEmployees();

        assertEquals(expected.getBody(), actual.getBody());
        assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    void testSearchEmployeeById() {
        when(employeeService.getEmployeeById(TEST_EMPLOYEE_ID)).thenReturn(expectedEmployee);

        ResponseEntity<Employee> expected = new ResponseEntity<>(expectedEmployee, null , OK);
        ResponseEntity<Employee> actual = employeeController.getEmployeeById(TEST_EMPLOYEE_ID);

        assertEquals(expected.getBody(), actual.getBody());
        assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    void testCreateEmployee() {
        objectMap = EmployeeTestUtils.getCreateEmployeeRequest();

        when(employeeService.createEmployee(objectMap)).thenReturn(expectedEmployee);

        ResponseEntity<Employee> expected = new ResponseEntity<>(expectedEmployee, null, HttpStatus.CREATED);
        ResponseEntity<Employee> actual = employeeController.createEmployee(objectMap);

        assertEquals(expected.getBody(), actual.getBody());
        assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    void testSearchEmployeeByName() {
        expectedEmployeeResponse = new ArrayList<>();
        expectedEmployeeResponse.add(expectedEmployee);

        when(employeeService.getEmployeesByNameSearch("Ashton"))
                .thenReturn(expectedEmployeeResponse);

        ResponseEntity<List<Employee>> expected = new ResponseEntity<>(expectedEmployeeResponse,
                null , OK);
        ResponseEntity<List<Employee>> actual = employeeController.getEmployeesByNameSearch("Ashton");

        assertEquals(expected.getBody(), actual.getBody());
        assertEquals(expected.getStatusCode(), actual.getStatusCode());
    }

    @Test
    void testDeleteEmployee() {
        when(employeeService.deleteEmployeeById(TEST_EMPLOYEE_ID)).thenReturn("SUCCESS");

        ResponseEntity<String> expectedResponse = new ResponseEntity<>("SUCCESS", null, OK);
        ResponseEntity<String> actualResponse = employeeController.deleteEmployeeById(TEST_EMPLOYEE_ID);

        assertEquals(actualResponse.getStatusCode(), expectedResponse.getStatusCode());
        assertEquals(actualResponse.getBody(), expectedResponse.getBody());
    }

    @Test
    void testGetHighestSalary() {
        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(TEST_HIGHEST_SALARY);

        ResponseEntity<Integer> expectedResponse = new ResponseEntity<>(TEST_HIGHEST_SALARY, null, OK);
        ResponseEntity<Integer> actualResponse = employeeController.getHighestSalaryOfEmployees();

        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }


}
