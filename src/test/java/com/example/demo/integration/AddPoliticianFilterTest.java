package com.example.demo.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.PoliticianController;
import com.example.demo.model.Politicians;
import com.example.demo.model.PoliticiansRating;
import com.example.demo.service.PoliticiansService;

@WebMvcTest(PoliticianController.class)
@AutoConfigureMockMvc(addFilters = true, printOnlyOnFailure = false,print = MockMvcPrint.DEFAULT)
public class AddPoliticianFilterTest {

	@Autowired
	public MockMvc mvc;
	
	@MockBean
	public PoliticiansService service;
	
	private final String content = """
			{
			    "name": "test name",
			    "rating": 9.00
			}
			""";
	private Politicians politician; 
	
	@BeforeEach
	public void setup() {
		politician = new Politicians(1, 9.00D, "test name", List.of(new PoliticiansRating()), 9.00D);
	}
	
	@Test 
	public void shouldReturn401AuthorizationRequiredMessage() throws URISyntaxException, Exception {
		when(service.savePolitician(any())).thenReturn(politician);
		
		mvc.perform(post(URI.create("/api/politicians/add-politician"))
				.header("Politician-Access", "oo")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("err", 
					equalTo("Authorization Required")))
			.andExpect(jsonPath("code", 
					equalTo("401")));
	}
	
	@Test 
	public void shouldReturn201CreatedIfAuthorizationIsCorrect() throws URISyntaxException, Exception {
		when(service.savePolitician(any())).thenReturn(politician);
		
		mvc.perform(post(URI.create("/api/politicians/add-politician"))
				.header("Politician-Access", "password")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("name", 
					equalTo("test name")))
			.andExpect(jsonPath("rating", 
					equalTo(9.0)));
	}
	
}
