package com.example.rqchallenge.employees.services;

import com.example.rqchallenge.employees.models.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    /**
     * Returns list of all employees
     * @return List of Employee
     */
    List<Employee> getAllEmployees();

    /**
     * Returns employees having name equal to the query string
     * @param searchString employee name as a search string
     * @return List of Employees having name similar to searchString
     */
    List<Employee> getEmployeesByNameSearch(String searchString);

    /**
     * Returns employee object for given employee id
     * @param id employee Id
     * @return Employee Object
     */
    Employee getEmployeeById(String id);

    /**
     * Returns the highest salary from all Employees
     * @return highest salary
     */
    Integer getHighestSalaryOfEmployees();

    /**
     * Returns top 10 highest earning employee names
     * @return List of Employee Names
     */
    List<String> getTopTenHighestEarningEmployeeNames();

    /**
     * Creates a new Employee in system
     * @param employeeInput Map of employee fields & values
     * @return Employee Object
     */
    Employee createEmployee(Map<String, Object> employeeInput);

    /**
     * Deletes the employee from system
     * @param id id of employee to be deleted
     * @return message indicating success/failure/exception
     */
    String deleteEmployeeById(String id);

}