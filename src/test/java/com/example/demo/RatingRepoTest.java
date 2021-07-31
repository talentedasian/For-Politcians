package com.example.demo;

import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.UserRater;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.RateLimitingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import testAnnotations.DatabaseTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@DatabaseTest
public class RatingRepoTest extends BaseClassTestsThatUsesDatabase {
	
	@Autowired RatingRepository repo;
	@Autowired PoliticiansRepository polRepo;
	
	final String EMAIL = "test@gmail.com";
	final String NAME = "test";
	final String ACCOUNT_NUMBER = "123polNumber";
	
	Politicians politician = new PresidentialBuilder(new Politicians.PoliticiansBuilder(ACCOUNT_NUMBER)
				.setRatingRepository(repo)
				.setFirstName("Test")
				.setLastName("Name")
				.setFullName()
				.setRating(new Rating(0.01D, 0.01D, mock(LowSatisfactionAverageCalculator.class)))
				.build())
			.build();
	
	@Test
	public void testCustomExistByQueryWithAccountNumber() {
		polRepo.save(politician);
		var userRater = new UserRater(NAME, PoliticalParty.DDS, EMAIL, ACCOUNT_NUMBER, mock(RateLimitingService.class));
		repo.save(new PoliticiansRating(1, 1.00D, userRater, politician));
		
		assertTrue(repo.existsByRater_UserAccountNumber(ACCOUNT_NUMBER));
	}
	
	@Test
	public void testCustomDeleteByQueryWithAccountNumber() {
		polRepo.save(politician);
		var userRater = new UserRater(NAME, PoliticalParty.DDS, EMAIL, ACCOUNT_NUMBER, mock(RateLimitingService.class));
		
		repo.save(new PoliticiansRating(1, 1.00D, userRater, politician));
		repo.save(new PoliticiansRating(2, 1.21D, userRater, politician));
		userRater.setUserAccountNumber("dummyAccountNumber");
		repo.save(new PoliticiansRating(3, 1.32D, userRater, politician));
		
		List<PoliticiansRating> NOT_CONTAINED_ELEMENTS = repo.findByRater_UserAccountNumber(ACCOUNT_NUMBER);
		
		repo.deleteByRater_UserAccountNumber(ACCOUNT_NUMBER);
		
		List<PoliticiansRating> allPoliticianRatings = repo.findAll();
		
		assertThat(allPoliticianRatings)
			.doesNotContainAnyElementsOf(NOT_CONTAINED_ELEMENTS)
			.containsAll(repo.findByRater_UserAccountNumber("dummyAccountNumber"));
	}

}
