package com.example.demo.integration;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.startsWithIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.PoliticianController;
import com.example.demo.controller.RatingsController;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.dtomapper.RatingDtoMapper;
import com.example.demo.service.PoliticiansService;
import com.example.demo.service.RatingService;

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
	public RatingDtoMapper ratingMapper;
	@MockBean
	public PoliticiansService politicianService;



	private final String ratingContent = """
			{
				"politicianLastName": "name",
				"politicianFirstName": "test",
				"rating": 0.00,
				"politicalParty": "dds"
			}
			""";
	
	private final String politicianContent = """
			{
				"lastNames": "name",
				"firstNames": "test",
				"rating": 0.09
			}
			""";
	
	@Test
	public void shouldReturn400BadRequestOnRatingEndpoint() throws Exception {
		mvc.perform(post(create("/api/ratings/add-rating"))
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
		mvc.perform(post(create("/api/politicians/add-politician"))
				.header("Politician-Access", "password")
				.content(politicianContent)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("code", 
					equalTo("400")))
			.andExpect(jsonPath("err", 
					containsStringIgnoringCase("bad request")))
			.andExpect(jsonPath("message", 
					hasItem(startsWithIgnoringCase("error on lastName"))))
			.andExpect(jsonPath("message", 
					hasItem(startsWithIgnoringCase("error on firstName"))));
	}
}
