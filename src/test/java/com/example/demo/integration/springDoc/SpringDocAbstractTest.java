package com.example.demo.integration.springDoc;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.PoliticsApplicationTests;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.dtomapper.RatingDtoMapper;

public class SpringDocAbstractTest extends PoliticsApplicationTests {
	
	@MockBean
	public PoliticiansDtoMapper polMapper;
	@MockBean
	public RatingDtoMapper ratingMapper;
	
	@Test
	public void testSpringDocDescriptionApi() throws Exception {
		mvc.perform(get(create("/v3/api-docs")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("info.description", 
					containsStringIgnoringCase("Login with `oauth2/authorization/facebook` first to get a")));
	}

}
