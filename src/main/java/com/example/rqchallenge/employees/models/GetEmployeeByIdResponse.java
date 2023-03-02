package com.example.rqchallenge.employees.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
public class GetEmployeeByIdResponse {

    @JsonProperty("data")
    private Employee employee;
}
