package com.example.demo.integration;

import com.example.demo.adapter.in.web.PoliticianController;
import com.example.demo.adapter.in.web.RatingsController;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.hateoas.PoliticianAssembler;
import com.example.demo.hateoas.RatingAssembler;
import com.example.demo.adapter.in.service.PoliticiansService;
import com.example.demo.adapter.in.service.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ RatingsController.class, PoliticianController.class })
@AutoConfigureMockMvc(addFilters = false, printOnlyOnFailure = false, print = MockMvcPrint.DEFAULT)
public class BadRequestTest {

	@Autowired
	public MockMvc mvc;

	@MockBean
	public RatingService service;
	@MockBean
	public PoliticiansDtoMapper polMapper;
	@MockBean
	public PoliticiansService politicianService;
	@MockBean
	public RatingAssembler assembler;
	@MockBean
	public PoliticianAssembler polAssembler;



	private final String ratingContent = """
			{
				"politicianLastName": "name",
				"politicianFirstName": "test",
				"rating": 0.00,
				"politicalParty": "dds"
			}
			""";

	final String politicianContent = """
				{
					"firstName" : "Test",
					"last_name" : "Name",
					"rating": 0.09,
					"type" : "Senatorial",
					"months_of_service" : 31  
				}
				""";
	
	@Test
	public void shouldReturn400BadRequestOnRatingEndpoint() throws Exception {
		mvc.perform(post(create("/api/ratings/rating"))
				.content(ratingContent)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("code", 
					equalTo("400")))
			.andExpect(jsonPath("err", 
					containsStringIgnoringCase("bad request")))
			.andExpect(jsonPath("message", 
					everyItem(startsWithIgnoringCase("Error on"))));
	}
	
	@Test
	public void shouldReturn400BadRequestOnPoliticianEndpoint() throws Exception {
		mvc.perform(post(create("/api/politicians/politician"))
				.header("Politician-Access", "password")
				.content(politicianContent)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("code", 
					equalTo("400")))
			.andExpect(jsonPath("err", 
					containsStringIgnoringCase("bad request")))
			.andExpect(jsonPath("message", 
					hasItem(startsWithIgnoringCase("error on firstName"))));
	}
	
}
