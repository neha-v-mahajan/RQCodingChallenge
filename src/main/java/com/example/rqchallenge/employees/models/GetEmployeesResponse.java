package com.example.rqchallenge.employees.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
public class GetEmployeesResponse {

    @JsonProperty("data")
    @NonNull
    private List<Employee> employeeList;

}
