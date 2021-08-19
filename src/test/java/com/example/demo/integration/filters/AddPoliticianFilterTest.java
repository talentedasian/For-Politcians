package com.example.demo.integration.filters;

import com.example.demo.adapter.dto.PoliticianDto;
import com.example.demo.adapter.dto.SenatorialPoliticianDto;
import com.example.demo.adapter.in.service.PoliticiansService;
import com.example.demo.adapter.in.web.PoliticianController;
import com.example.demo.domain.averageCalculator.AverageCalculator;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.filter.AddPoliticianFilter;
import com.example.demo.hateoas.PoliticianAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.example.demo.domain.enums.Rating.LOW;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PoliticianController.class)
public class AddPoliticianFilterTest {

	public MockMvc mvc;

	@MockBean public PoliticiansService service;
	
	@MockBean public PoliticiansDtoMapper mapper;
	
	@MockBean public PoliticianAssembler assembler;
	
	@Mock public AverageCalculator calculator;
	
	private final String content = """
			{
			    "first_name": "test",
			    "last_name": "fullName",
			    "rating": 0.01,
			    "type": "Senatorial",
			    "months_of_service": 99
			}
			""";
	
	private Politicians politician; 
	
	@BeforeEach
	public void setup(WebApplicationContext wac) {
		politician = new SenatorialBuilder(new Politicians.PoliticiansBuilder("123polNumber")
				.setFirstName("Test")
				.setLastName("Name")
				.setFullName()
				.setRating(new Rating(0.01D, 0.01D))
				.setPoliticiansRating(List.of(new PoliticiansRating()))
				.build())
				.setTotalMonthsOfService(12)
				.build();
				
		mvc = MockMvcBuilders.webAppContextSetup(wac)
				.addFilter(new AddPoliticianFilter(), "/api/politicians/politician")
				.alwaysDo(print())
				.build();
	}
	
	@Test 
	public void shouldReturn401AuthorizationRequiredMessageIfAuthorizationIsIncorrect() throws Exception {
		when(service.savePolitician(any())).thenReturn(politician);
		
		mvc.perform(post(URI.create("/api/politicians/politician"))
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("err", 
					equalTo("Authorization Required")))
			.andExpect(jsonPath("code", 
					equalTo("401")));
	}
	
	@Test 
	public void shouldReturn401AuthorizationRequiredMessageIfHeaderRequiredIsNull() throws URISyntaxException, Exception {
		when(service.savePolitician(any())).thenReturn(politician);
		
		mvc.perform(post(URI.create("/api/politicians/politician"))
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
		var polDTO = new SenatorialPoliticianDto(politician, LOW, 12, null);
		
		when(service.savePolitician(any())).thenReturn(politician);
		when(assembler.toModel(any(PoliticianDto.class))).thenReturn(EntityModel.of(polDTO));
		
		mvc.perform(post(URI.create("/api/politicians/politician"))
				.header("Politician-Access", "password")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("name", 
					equalTo("test fullName")))
			.andExpect(jsonPath("rating", 
					equalTo(0.01)));
	}
	
}
