package com.example.demo.integration.springDoc;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;

public class SpringDocVersionTest extends SpringDocAbstractTest{

	@Test
	public void versionShouldEqualTo100() throws Exception {
		mvc.perform(get(create("/v3/api-docs")))
		.andExpect(jsonPath("info.version", 
				equalTo("1.0.0")));
	}
}
