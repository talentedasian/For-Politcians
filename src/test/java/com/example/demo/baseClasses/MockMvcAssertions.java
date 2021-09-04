package com.example.demo.baseClasses;

import org.assertj.core.api.AbstractAssert;

public class MockMvcAssertions extends AbstractAssert<MockMvcAssertions, MockMvcResult> {

    private static MockMvcResult actual;

    protected MockMvcAssertions(MockMvcResult mockMvcResult) {
        super(mockMvcResult, MockMvcAssertions.class);
        this.actual = mockMvcResult;
    }

    public static MockMvcAssertions assertThat(MockMvcResult actual) {
        return new MockMvcAssertions(actual);
    }

    public MockMvcAssert hasPath(String path) {
        return MockMvcAssert.of(path, actual);
    }

}

