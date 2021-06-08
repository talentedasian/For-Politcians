package com.example.demo.integration.springDoc;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;

import com.example.demo.PoliticsApplicationTests;

public class SpringDocAbstractTest extends PoliticsApplicationTests {
	
	@Test
	public void testSpringDocDescriptionApi() throws Exception {
		mvc.perform(get(create("/v3/api-docs")))
			.andExpect(jsonPath("info.description", 
					containsStringIgnoringCase("Login with `oauth2/authorization/facebook` first to get a")));
	}

}
