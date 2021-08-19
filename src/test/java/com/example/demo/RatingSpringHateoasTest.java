package com.example.demo;

import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.entities.UserRater;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.enums.PoliticalParty;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static java.net.URI.create;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.hateoas.MediaTypes.HAL_FORMS_JSON;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RatingSpringHateoasTest extends BaseSpringHateoasTest{

	UserRater rater = new UserRater.Builder()
			.setAccountNumber("123accNumber")
			.setEmail("test@gmail.com")
			.setPoliticalParty(PoliticalParty.DDS)
			.setName("test")
			.setRateLimitRepo(rateLimitRepo)
			.build();
	
	Politicians politician = new Politicians.PoliticiansBuilder("dummy")
			.setFirstName("test")
			.setLastName("politician")
			.setFullName()
			.setRating(new Rating(1.00D, 1.00D))
			.build();
	Politicians savePol = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politician)
			.setTotalMonthsOfService(12)
			.setMostSignificantLawMade("law Law Law")
			.build();
	PoliticiansRating politiciansRating = new PoliticiansRating(1, 1.00D, rater, savePol);

	@Transactional
	@Test
	public void testHalFormsSaveRatingResponse() throws Exception {
		repo.save(savePol);
		String id = ratingRepo.save(politiciansRating).getId().toString();
		
		this.mvc.perform(get(create("/api/ratings/ratings/123accNumber")))
			.andExpect(status().isOk())
			.andExpect(content().contentType(HAL_FORMS_JSON))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].rating", equalTo(1.0D)))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].id", equalTo(id)))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].rater.email", equalTo(rater.getEmail())))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].rater.name", equalTo(rater.getFacebookName())))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].rater.political_party", equalTo(rater.getPoliticalParties().toString())))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].politician.name", equalTo(politician.name())))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].politician.id", equalTo(politician.retrievePoliticianNumber())))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].politician.rating", equalTo(1.0D)))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].politician.most_significant_law_made", containsStringIgnoringCase("law law law")))
			.andExpect(jsonPath("_embedded.ratingDTOList[0].politician.months_of_service", equalTo(12)))
				.andDo(document("find-rate", links(halLinks(),
					linkWithRel("self").description("Link to a rating"))));
		
		/*
		 * We are dealing with a real database here. Delete the entities
		 * before the test finishes. 
		 */
		repo.deleteByPoliticianNumber(politician.retrievePoliticianNumber());
		ratingRepo.deleteById(politiciansRating.getId());
	}
	
}
