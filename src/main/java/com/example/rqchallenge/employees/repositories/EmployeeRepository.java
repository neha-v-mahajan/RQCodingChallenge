package com.example.rqchallenge.employees.repositories;

import com.example.rqchallenge.employees.exceptions.EmployeeNotFoundException;
import com.example.rqchallenge.employees.models.Employee;

import java.util.List;

public interface EmployeeRepository {

    /**
     * fetch list of Employees in system
     * @return List of Employee objects
     */
    List<Employee> findAll();

    /**
     * Fetch list of employees containing name as requested parameter
     * @param name search string to be checked with all employee's names
     * @return list of employees that match names with search string
     */
    List<Employee> findByNameContaining(String name);

    /**
     * Fetch an employee whose Id equals to requested paramter
     * @param id id of an employee
     * @return Employee whose id matches with query param
     * @throws EmployeeNotFoundException if employee id is invalid
     */
    Employee findById(String id) throws EmployeeNotFoundException;

    /**
     * Returns highest salary among all employees
     * @return highest salary
     */
    Integer findHighestSalary();

    /**
     * Returns top 10 highest earning employee names
     * @return List of Employee Names
     */
    List<String> findTop10ByOrderBySalaryDesc();

    /**
     * Save the employee to the system
     * @param employee record to be added
     * @return Employee
     */
    Employee save(Employee employee);

    /**
     * Delete employee from system whose id matches with query param
     * @param id id of employee
     */
    void deleteById(String id) ;

    /**
     * checks if Employee with given ID exists in system or not
     * @param id Id of an employee
     * @return true/false
     */
    boolean existsById(String id);
}
