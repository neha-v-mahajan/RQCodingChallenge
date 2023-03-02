package com.example.rqchallenge.employees.services;

import com.example.rqchallenge.employees.exceptions.EmployeeCreationException;
import com.example.rqchallenge.employees.exceptions.EmployeeNotFoundException;
import com.example.rqchallenge.employees.exceptions.InvalidInputException;
import com.example.rqchallenge.employees.models.Employee;
import com.example.rqchallenge.employees.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        return employeeRepository.findByNameContaining(searchString);
    }

    @Override
    public Employee getEmployeeById(String id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id);
    }

    @Override
    public Integer getHighestSalaryOfEmployees() {
        return employeeRepository.findHighestSalary();
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        return employeeRepository.findTop10ByOrderBySalaryDesc();
    }

    @Override
    public Employee createEmployee(Map<String, Object> employeeInput) throws InvalidInputException {
        try {
            // Validate input
            String name = (String) employeeInput.get("name");
            Integer age = (Integer) employeeInput.get("age");
            Integer salary = (Integer) employeeInput.get("salary");

            if (name == null || name.isEmpty() || age == null || salary == null) {
                throw new InvalidInputException("Invalid input for creating employee");
            }

            // Create Employee object
            Employee employee = new Employee();
            employee.setName(name);
            employee.setAge(age);
            employee.setSalary(salary);

            // Call repository to create employee
            Employee createdEmployee = employeeRepository.save(employee);
            if (createdEmployee == null) {
                throw new EmployeeCreationException("Encountered an error creating employee");
            }

            return createdEmployee;
        } catch (InvalidInputException e) {
            log.error("Invalid input for creating employee", e);
            throw e;
        } catch (EmployeeCreationException e) {
            log.error("Error creating employee", e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error creating employee", e);
            throw new EmployeeCreationException("Unexpected error creating employee");
        }
    }

    @Override
    public String deleteEmployeeById(String id) {
        String msg;
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            msg = String.format("Employee with ID " + id + " has been deleted.", id);
        } else {
            msg = String.format("Employee with ID " + id + " not exist.", id);
        }
        return msg;
    }
}
