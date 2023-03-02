package com.example.rqchallenge.employees.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class CreateEmployeeResponse {

    @JsonProperty("data")
    @NonNull
    private Employee employee;
}
