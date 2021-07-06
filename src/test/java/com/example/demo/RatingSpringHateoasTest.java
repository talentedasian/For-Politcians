package com.example.demo;

import static java.net.URI.create;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;

import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;

public class RatingSpringHateoasTest extends BaseSpringHateoasTest{

	@Autowired PoliticiansRepository repo;
	@Autowired RatingRepository ratingRepo;
	
	@Test
	public void testHalFormsSaveRating() {
		var rater = new UserRater("test", PoliticalParty.DDS, "test@gmail.com", "123accNumber");
		var politician = new Politicians.PoliticiansBuilder()
				.setFirstName("test")
				.setLastName("politician")
				.setFullName()
				
				.setPoliticianNumber("123polNumber")
				.setRating(new Rating(1.00D, 1.00D, new LowSatisfactionAverageCalculator(1.00D, 1D)))
				.build();
				
		
		repo.save(politician);
		ratingRepo.save(new PoliticiansRating(1, 1.00D, rater, politician));
		
		webTestClient.get()
			.uri(create("http://localhost:8080/api/ratings/rating/123accNumber"))
			.accept(MediaTypes.HAL_FORMS_JSON)
			.exchange()
			.expectStatus().isOk().expectBody()
			.consumeWith(document("find-rate", links(halLinks(),
					linkWithRel("rating").description("Link to rating a politician"))));
	}
	
}
