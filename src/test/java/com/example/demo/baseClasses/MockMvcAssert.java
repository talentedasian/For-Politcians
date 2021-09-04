package com.example.demo.baseClasses;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.*;

public class MockMvcAssert {

    private final ObjectMapper mapper;
    private final String path;
    private final MockMvcResult json;

    private MockMvcAssert(String path, MockMvcResult json) {
        this.json = json;
        this.mapper = new ObjectMapper();
        this.path = path;
    }

    public static MockMvcAssert of(String path, MockMvcResult json) {
        return new MockMvcAssert(path, json);
    }

    public MockMvcAssertions isEqualTo(String expected) throws Exception {
        String value = getValueAsString();

        assertThat(value)
                .isEqualTo(expected);

        return MockMvcAssertions.assertThat(json);
    }

    public MockMvcAssertions isEqualTo(double expected) throws IOException {
        double value = getValueAsDouble();

        assertThat(value)
                .isEqualTo(expected);

        return MockMvcAssertions.assertThat(json);
    }

    private JsonNode getValue() throws IOException {
        try {
            return mapper.readTree(json.getJsonValue()).get(path);
        } catch (NullPointerException e) {
            fail(failMessage());
        }
        return null;
    }

    private String getValueAsString() throws IOException {
        return getValue().asText();
    }

    private Double getValueAsDouble() throws IOException {
        return getValue().asDouble();
    }

    private String failMessage() {
        return format("No \"%s\" path from json value provided", path);
    }

}
