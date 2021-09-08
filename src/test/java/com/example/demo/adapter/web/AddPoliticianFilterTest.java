package com.example.demo.adapter.web;

import com.example.demo.adapter.in.web.PoliticianController;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.filter.AddPoliticianFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.event.ApplicationEventsTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PoliticianController.class)
@TestExecutionListeners(
		value = { ServletTestExecutionListener.class,
				ApplicationEventsTestExecutionListener.class,
				})
public class AddPoliticianFilterTest {

	MockMvc mvc;
	
	private final String content = """
			{
			    "first_name": "test",
			    "last_name": "fullName",
			    "rating": 0.01,
			    "type": "SENATORIAL",
			    "months_of_service": 99
			}
			""";
	
	private Politicians politician; 
	
	@BeforeEach
	public void setup() {
		politician = new SenatorialBuilder(new Politicians.PoliticiansBuilder(POL_NUMBER())
				.setFirstName("Test")
				.setLastName("Name")
				.setFullName()
				.setRating(new Rating(0.01D, 0.01D))
				.setPoliticiansRating(List.of(new PoliticiansRating()))
				.build())
				.setTotalMonthsOfService(12)
				.build();
				
		mvc = MockMvcBuilders.standaloneSetup(new PoliticianController(null, null))
				.addFilter(new AddPoliticianFilter(), "/api/politicians/politician")
				.alwaysDo(print())
				.build();
	}
	
	@Test 
	public void shouldReturn401AuthorizationRequiredMessageIfAuthorizationIsIncorrect() throws Exception {
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
	public void shouldThrowAnyKindOfExceptionMeaningRequestHasSuccessfullyPassedAuthorization() throws URISyntaxException, Exception {
		NestedServletException exception = assertThrows(NestedServletException.class, () -> mvc.perform(post(URI.create("/api/politicians/politician"))
				.header("Politician-Access", "password")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)));

		assertThat(exception)
				.hasCauseInstanceOf(NullPointerException.class);
	}
	
}
