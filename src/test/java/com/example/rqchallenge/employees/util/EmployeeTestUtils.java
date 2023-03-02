package com.example.rqchallenge.employees.util;

import java.util.HashMap;
import java.util.Map;

public class EmployeeTestUtils {

    public static final String TEST_EMPLOYEE_ID = "3";
    public static final int TEST_HIGHEST_SALARY = 433060;

    public static Map<String, Object> getCreateEmployeeRequest() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("name", "Demo");
        objectMap.put("age", 66);
        objectMap.put("salary", 86000);

        return objectMap;
    }
}
