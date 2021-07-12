package com.example.demo;

import static com.example.demo.model.enums.Rating.LOW;
import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.hateoas.MediaTypes.HAL_FORMS_JSON;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.UserRater;
import com.example.demo.model.enums.PoliticalParty;

public class RatingSpringHateoasTest extends BaseSpringHateoasTest{
	
	UserRater rater = new UserRater("test", PoliticalParty.DDS, "test@gmail.com", "123accNumber", limitingService);
	Politicians politician = new Politicians.PoliticiansBuilder()
			.setFirstName("test")
			.setLastName("politician")
			.setFullName()
			.setPoliticianNumber("123polNumber")
			.setRating(new Rating(1.00D, 1.00D, new LowSatisfactionAverageCalculator(1.00D, 1D)))
			.build();
	PoliticiansRating politiciansRating = new PoliticiansRating(1, 1.00D, rater, politician);
	
	@Transactional
	@Test
	public void testHalFormsSaveRating() throws Exception {
		repo.save(politician);
		String id = ratingRepo.save(politiciansRating).getId().toString();
		
		this.mvc.perform(get(create("/api/ratings/ratings/123accNumber")))
			.andExpect(status().isOk())
			.andExpect(content().contentType(HAL_FORMS_JSON))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].rating", equalTo(1.0D)))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].id", equalTo(id)))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].rater.email", equalTo(rater.getEmail())))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].rater.name", equalTo(rater.getFacebookName())))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].rater.political_party", equalTo(rater.getPoliticalParties().toString())))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].politician.name", equalTo(politician.getFullName())))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].politician.id", equalTo(politician.getPoliticianNumber())))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].politician.rating", equalTo(1.0D)))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].politician.satisfaction_rate", equalTo(LOW.toString())))
			.andDo(document("find-rate", links(halLinks(),
					linkWithRel("self").description("Link to a rating"))));
		
		/*
		 * We are dealing with a real database here. Delete the entities
		 * before the test finishes. 
		 */
		repo.delete(politician);
		ratingRepo.delete(politiciansRating);
	}
	
}
