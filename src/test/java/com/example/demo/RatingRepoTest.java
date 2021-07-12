package com.example.demo;

import static com.example.demo.model.enums.PoliticalParty.DDS;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.UserRater;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.RateLimitingService;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class RatingRepoTest {
	
	@Autowired RatingRepository repo;
	@Autowired PoliticiansRepository polRepo;
	
	Politicians politician = new Politicians.PoliticiansBuilder()
			.setRatingRepository(repo)
			.setFirstName("Test")
			.setLastName("Name")
			.setFullName()
			.setRating(new Rating(0.01D, 0.01D, mock(LowSatisfactionAverageCalculator.class)))
			.setPoliticianNumber("123polNumber")
			.build();
	
	@Test
	public void shouldReturnBooleanByAccountNumber() {
		polRepo.save(politician);
		var userRater = new UserRater("test", DDS, "test@gmail.com", "123accNumber", mock(RateLimitingService.class));
		repo.save(new PoliticiansRating(1, 1.00D, userRater, politician));
		
		assertTrue(repo.existsByRater_UserAccountNumber("123accNumber"));
	}
	
	@Test
	public void shouldReturnEmptyListWhenAfterDeletion() {
		polRepo.save(politician);
		var userRater = new UserRater("test", DDS, "test@gmail.com", "123accNumber", mock(RateLimitingService.class));
		repo.save(new PoliticiansRating(1, 1.00D, userRater, politician));
		repo.save(new PoliticiansRating(2, 1.21D, userRater, politician));
		
		repo.deleteByRater_UserAccountNumber("123accNumber");
		assertTrue(repo.findByRater_UserAccountNumber("123accNumber").isEmpty());
	}

}
