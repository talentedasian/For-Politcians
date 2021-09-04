package com.example.demo.baseClasses;

import org.assertj.core.api.AbstractAssert;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

public class MockMvcAssertions extends AbstractAssert<MockMvcAssertions, MockMvcResult> {

    private static MockMvcResult actual;

    protected MockMvcAssertions(MvcResult mockMvcResult) throws UnsupportedEncodingException {
        super(MockMvcResult.of(mockMvcResult.getResponse().getContentAsString()), MockMvcAssertions.class);
        this.actual = MockMvcResult.of(mockMvcResult.getResponse().getContentAsString());
    }

    public static MockMvcAssertions assertThat(MvcResult actual) throws UnsupportedEncodingException {
        return new MockMvcAssertions(actual);
    }

    public MockMvcAssert hasPath(String path) {
        return MockMvcAssert.of(path, actual);
    }


}

