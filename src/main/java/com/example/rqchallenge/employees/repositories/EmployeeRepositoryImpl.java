package com.example.rqchallenge.employees.repositories;

import com.example.rqchallenge.employees.exceptions.EmployeeCreationException;
import com.example.rqchallenge.employees.exceptions.EmployeeNotFoundException;
import com.example.rqchallenge.employees.models.CreateEmployeeResponse;
import com.example.rqchallenge.employees.models.Employee;
import com.example.rqchallenge.employees.models.GetEmployeeByIdResponse;
import com.example.rqchallenge.employees.models.GetEmployeesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final String BASE_URL = "https://dummy.restapiexample.com/api/v1";

    private final RestTemplate restTemplate;

    @Override
    public List<Employee> findAll() {
        try {
            String url = BASE_URL + "/employees";
            GetEmployeesResponse response = restTemplate.getForObject(url, GetEmployeesResponse.class);
            return response.getEmployeeList();
        } catch (Exception e) {
            log.error("Error getting all employees", e);
            throw new RuntimeException(String.format("Error getting all employees %s", e));
        }
    }

    @Override
    public List<Employee> findByNameContaining(String name) {
        try {
            String url = BASE_URL +  "/employees?search=" + name;
            GetEmployeesResponse response = restTemplate.getForObject(url, GetEmployeesResponse.class);
            return response.getEmployeeList();
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("Employees with name {} not found", name);
            throw new EmployeeNotFoundException(String.format("Employees with name {} not found, Error: %s", name, e));
        } catch (Exception e) {
            log.error("Error getting employees by name search", e);
            throw new  RuntimeException(String.format("Error getting employees by name search", e));
        }
    }

    @Override
    public Employee findById(String id) {
        try {
            String url = BASE_URL + "/employee/" + id;
            GetEmployeeByIdResponse response = restTemplate.getForObject(url, GetEmployeeByIdResponse.class);
            return response.getEmployee();
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("Employee with id {} not found", id);
            throw new EmployeeNotFoundException(String.format("Employee with id %s not found: Exception %s", id, e));
        } catch (Exception e) {
            log.error("Error getting employee by id", e);
            throw new RuntimeException(String.format("Error getting employee by id %s, Exception: %s", id, e));
        }
    }

    @Override
    public Integer findHighestSalary() {
        try {
            String url = BASE_URL + "/employees";
            GetEmployeesResponse response = restTemplate.getForObject(url, GetEmployeesResponse.class);
            List<Employee> employees = response.getEmployeeList();
            return employees.stream()
                    .mapToInt(Employee::getSalary)
                    .max()
                    .orElse(0);
        } catch (Exception e) {
            log.error("Error getting highest salary of employees", e);
            throw new RuntimeException(String.format("Error getting highest salary of employees, Exception: %s", e));
        }
    }

    @Override
    public List<String> findTop10ByOrderBySalaryDesc() {
        try {
            String url =  BASE_URL +  "/employees";
            GetEmployeesResponse response = restTemplate.getForObject(url, GetEmployeesResponse.class);
            List<Employee> employees = response.getEmployeeList();
            return employees.stream()
                    .sorted((e1, e2) -> e2.getSalary() - e1.getSalary())
                    .limit(10)
                    .map(Employee::getName)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error getting top 10 highest earning employees", e);
            throw new RuntimeException(String.format("Error getting top 10 highest earning employees, Exception: %s", e));
        }
    }

    @Override
    public Employee save(Employee employee) {
        try {
            String url = BASE_URL + "/create";
            CreateEmployeeResponse response = restTemplate.postForObject(url, employee, CreateEmployeeResponse.class);
            return response.getEmployee();
        } catch (Exception e) {
            log.error("Error creating employee", e);
            throw new EmployeeCreationException(String.format("Error creating employee, Error %s", e));
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            String url = BASE_URL + "/delete/" + id;
            restTemplate.delete(url);
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("Employee with id {} not found", id);
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        } catch (Exception e) {
            log.error("Error deleting employee with id " + id, e);
            throw new RuntimeException("Error deleting employee with id " + id, e);
        }
    }


    @Override
    public boolean existsById(String id) {
        try {
            String url = BASE_URL  + "/employee/" + id;
            restTemplate.getForObject(url, GetEmployeeByIdResponse.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("Employee with id {} not found", id);
            return false;
        } catch (Exception e) {
            log.error("Error checking if employee exists", e);
            return false;
        }
    }

}

