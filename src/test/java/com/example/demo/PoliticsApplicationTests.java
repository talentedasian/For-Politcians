package com.example.demo;

import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PoliticsApplicationTests {

	@Autowired
	public MockMvc mvc;
	
	@Test
	public void shouldReturn200IsOk() throws Exception {
		mvc.perform(get("/swagger-ui/index.html"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturnExpectedInfo() throws Exception {
		String infoMessage = "use this jwt ";
		mvc.perform(get("/v3/api-docs"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("info.description", 
					containsStringIgnoringCase(infoMessage)));
	}

}
