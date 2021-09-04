package com.example.demo.baseClasses;

import org.assertj.core.api.AbstractAssert;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class MockMvcAssert extends AbstractAssert<MockMvcAssert, MockMvcResult> {

    private final ObjectMapper mapper;
    private final String path;
    private final com.example.demo.baseClasses.MockMvcResult json;

    private MockMvcAssert(String path, com.example.demo.baseClasses.MockMvcResult json) {
        super(json, MockMvcAssert.class);
        this.json = json;
        this.mapper = new ObjectMapper();
        this.path = path;
    }

    public static MockMvcAssert of(String path, com.example.demo.baseClasses.MockMvcResult json) {
        return new MockMvcAssert(path, json);
    }

    public <T> MockMvcAssertions isEqualToObject(T expected) throws IOException {
        JsonNode value = getValue();

        if (!Objects.equals(value, mapper.readTree(mapper.writeValueAsString(expected)))) {
            String val = value.toString().replace('{', '[').replace('}', '[');
            String exp = expected.toString().replace('{', '[').replace('}', '[');
            failWithMessage("Expected object to be %s but was %s", exp, val);
        }

        return MockMvcAssertions.assertThat(DefaultMvcResult.of(json.getJsonValue()));
    }

    public MockMvcAssertions isEqualTo(String expected) throws Exception {
        String value = getValueAsString();

        assertThat(value)
                .isEqualTo(expected);

        return MockMvcAssertions.assertThat(DefaultMvcResult.of(json.getJsonValue()));
    }

    public MockMvcAssertions isEqualTo(double expected) throws IOException {
        double value = getValueAsDouble();

        assertThat(value)
                .isEqualTo(expected);

        return MockMvcAssertions.assertThat(DefaultMvcResult.of(json.getJsonValue()));
    }

    private JsonNode getValue() throws IOException {
        try {
            var value = mapper.readTree(json.getJsonValue()).get(path);
            if (value == null) {
                throw new IllegalStateException(failMessage());
            }
            return value;
        } catch (NullPointerException e) {
            throw new IllegalStateException(failMessage());
        }
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

    /*
        Default implementation for MvcResult(spring framework) that only deals with responses
        since that's all we need for custom MockMvc assertions
     */
    public static class DefaultMvcResult implements MvcResult{

        private final String json;

        private DefaultMvcResult(String json) {
            this.json = json;
        }


        public static MvcResult of(String json) {
            return new DefaultMvcResult(json);
        }

        @Override
        public MockHttpServletRequest getRequest() {
            return null;
        }

        @Override
        public MockHttpServletResponse getResponse() {
            var object = new MockHttpServletResponse();
            try {
                object.getWriter().write(json);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return object;
        }

        @Override
        public Object getHandler() {
            return null;
        }

        @Override
        public HandlerInterceptor[] getInterceptors() {
            return new HandlerInterceptor[0];
        }

        @Override
        public ModelAndView getModelAndView() {
            return null;
        }

        @Override
        public Exception getResolvedException() {
            return null;
        }

        @Override
        public FlashMap getFlashMap() {
            return null;
        }

        @Override
        public Object getAsyncResult() {
            return null;
        }

        @Override
        public Object getAsyncResult(long timeToWait) {
            return null;
        }
    }

}
