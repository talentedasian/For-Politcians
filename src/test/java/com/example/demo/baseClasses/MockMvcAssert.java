package com.example.demo.baseClasses;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
        JsonNode object = mapper.readTree(json.getJsonValue());
        try {
            String value = object.get(path).asText();

            assertThat(value)
                    .isEqualTo(expected);
        } catch (NullPointerException e) {
            fail(format("No \"%s\" path from json value provided", path));
        }

        return MockMvcAssertions.assertThat(json);
    }

    public MockMvcAssertions isEqualTo(double expected) throws IOException {
        JsonNode object = mapper.readTree(json.getJsonValue());
        double value = object.get(path).asDouble();

        assertThat(value)
                .isEqualTo(expected);

        return MockMvcAssertions.assertThat(json);
    }
}
