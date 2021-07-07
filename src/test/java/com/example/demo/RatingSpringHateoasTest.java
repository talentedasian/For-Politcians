package com.example.demo;

import static java.net.URI.create;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.RateLimitingService;

public class RatingSpringHateoasTest extends BaseSpringHateoasTest{

	@Autowired PoliticiansRepository repo;
	@Autowired RatingRepository ratingRepo;
	@Autowired RateLimitingService limitingService;
	
	UserRater rater = new UserRater("test", PoliticalParty.DDS, "test@gmail.com", "123accNumber", limitingService);
	Politicians politician = new Politicians.PoliticiansBuilder()
			.setFirstName("test")
			.setLastName("politician")
			.setFullName()
			.setPoliticianNumber("123polNumber")
			.setRating(new Rating(1.00D, 1.00D, new LowSatisfactionAverageCalculator(1.00D, 1D)))
			.build();
	private PoliticiansRating politiciansRating = new PoliticiansRating(1, 1.00D, rater, politician);
	
	@Transactional
	@Test
	public void testHalFormsSaveRating() throws Exception {
				
		
		repo.save(politician);
		ratingRepo.save(politiciansRating);
		
		this.mvc.perform(get(create("/api/ratings/ratings/123accNumber")))
			.andExpect(status().isOk())
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
