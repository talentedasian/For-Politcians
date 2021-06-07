package com.example.demo;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
class PoliticsApplicationTests {

	private MockMvc mvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac)
				.build();
	}
	
	@Test
	public void testSpringDocDescriptionApi() throws Exception {
		mvc.perform(get(create("/v3/api-docs")))
			.andExpect(jsonPath("info.description", 
					containsStringIgnoringCase("Login with `oauth2/authorization/facebook` first to get a")));
	}
}
