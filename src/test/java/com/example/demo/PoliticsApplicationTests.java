package com.example.demo;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PoliticsApplicationTests {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testSpringDocDescriptionApi() throws Exception {
		mvc.perform(get(create("/v3/api-docs")))
			.andExpect(jsonPath("info.description", 
					containsStringIgnoringCase("Login with `oauth2/authorization/facebook` first to get a")));
	}
}
