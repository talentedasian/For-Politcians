package com.example.demo.baseClasses;

public class MockMvcResult {

    private final String jsonValue;

    private MockMvcResult(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    public static MockMvcResult of(String jsonValue) {
        return new MockMvcResult(jsonValue);
    }

    public String getJsonValue() {
        return jsonValue;
    }
}
