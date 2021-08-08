package com.example.demo.integration;

import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import com.example.demo.model.entities.RateLimit;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RateLimitRepository;
import com.example.demo.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

import static com.example.demo.jwt.JwtProvider.createJwtWithFixedExpirationDate;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RateLimitIntegrationTest extends BaseClassTestsThatUsesDatabase {
	
	@Autowired RestTemplate template;
	@Autowired RateLimitRepository rateLimitRepo;
	@Autowired RatingService ratingService;
	@Autowired PoliticiansRepository polRepo;
	RateLimit rateLimitToBeSaved = new RateLimit();

	final String POLITICIAN_NUMBER = "123polNumber";

	PresidentialPolitician politician = new PresidentialBuilder(new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
				.setId(1)
				.setRating(new Rating(1D, 1D))
				.setFirstName("test")
				.setLastName("name")
				.setFullName())
			.build();

	@BeforeEach
	public void setup() {
		rateLimitToBeSaved.setDateCreated(LocalDate.now().minusDays(5L));
		rateLimitToBeSaved.setId(FacebookUserRaterNumberImplementor.with("test name", "1").calculateEntityNumber().getAccountNumber());
		rateLimitToBeSaved.setPoliticianNumber(POLITICIAN_NUMBER);
	}
	
	@Test
	public void shouldThrowRateLimitedExceptionWhenUserIsRateLimited() throws UserRateLimitedOnPoliticianException {
		polRepo.save(politician);
		rateLimitRepo.save(rateLimitToBeSaved);
		
		var requestContent = new AddRatingDTORequest(valueOf(1L), POLITICIAN_NUMBER, "dds");
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getHeader("Authorization")).thenReturn("Bearer " + createJwtWithFixedExpirationDate("test@gmail.com", "1", "test name"));
		
		assertThrows(UserRateLimitedOnPoliticianException.class, 
				() -> ratingService.saveRatings(requestContent, req));
		
		/*
		 * We are dealing with a real database here. Delete the entities
		 * before the test finishes. 
		 */
		rateLimitRepo.delete(rateLimitToBeSaved);
		polRepo.delete(politician);
	}

}
