package com.example.demo.baseClasses;

import org.assertj.core.api.AbstractAssert;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

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

//    public <T> MockMvcListAssert hasBasePathOfEmbeddedArray(String basePath, T clazz) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//
//        List<String> basePaths = Arrays.stream(basePath.split("\\.")).skip(1).toList();
//
//        JsonNode baseJson = mapper.readTree(actual.getJsonValue()).get(basePath.split("\\.")[0]);
//
//        JsonNode resultingNode = baseJson;
//        for (String path : basePaths) {
//            resultingNode = resultingNode.get(path);
//        }
//        System.out.println(resultingNode + " haha");
//        List<String> jsonNodes = StreamSupport.stream(resultingNode.spliterator(), false).map(it -> {
//
//            return it.asText();
//        }).toList();
//
//        List<T> nodes = mapper.readValue(resultingNode.asText(), new TypeReference<List<T>>() {
//        });
//
//        System.out.println(nodes + " haha nodes");
//
//
//        return MockMvcListAssert.of(jsonNodes);
//    }
}

