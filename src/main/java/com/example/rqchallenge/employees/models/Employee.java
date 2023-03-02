package com.example.rqchallenge.employees.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Employee {

    @JsonProperty("id")
    private int id;
    @JsonProperty("employee_age")
    private int age;
    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("employee_salary")
    private int salary;
    @JsonProperty("profile_image")
    private String image;
}
