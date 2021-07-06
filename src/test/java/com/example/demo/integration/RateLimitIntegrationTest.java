package com.example.demo.integration;

import static com.example.demo.jwt.JwtProvider.createJwtWithFixedExpirationDate;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.RateLimit;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RateLimitRepository;
import com.example.demo.service.RatingService;

@SpringBootTest
public class RateLimitIntegrationTest {
	
	@Autowired public RestTemplate template;
	@Autowired public RateLimitRepository rateLimitRepo;
	@Autowired public RatingService ratingService;
	@Autowired public PoliticiansRepository polRepo;
	RateLimit rateLimitToBeSaved = new RateLimit();
	
	
	@BeforeEach
	public void setup() {
		polRepo.save(new Politicians.PoliticiansBuilder()
				.setId(1)
				.setRating(new Rating(1D, 1D, new LowSatisfactionAverageCalculator(1D, 0D)))
				.setPoliticianNumber("1number")
				.setFirstName("test")
				.setLastName("name")
				.setFullName()
				.build());
		
		rateLimitToBeSaved.setDateCreated(LocalDate.now().minusDays(5L));
		rateLimitToBeSaved.setId(FacebookUserRaterNumberImplementor.with("test name", "1").calculateEntityNumber().getAccountNumber());
		rateLimitToBeSaved.setPoliticianNumber("1number");
	}
	
	@Test
	public void assertLogicOfRateLimitSupposedToThrow() throws UserRateLimitedOnPoliticianException {
		rateLimitRepo.save(rateLimitToBeSaved);
		
		var requestContent = new AddRatingDTORequest(valueOf(1L), "1number", "dds");
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getHeader("Authorization")).thenReturn("Bearer " + createJwtWithFixedExpirationDate("test@gmail.com", "1", "test name"));
		
		assertThrows(UserRateLimitedOnPoliticianException.class, 
				() -> ratingService.saveRatings(requestContent, req));
	}	
	
	@Test
	public void assertLogicOfRateLimitShouldSuccessful() {
		rateLimitRepo.save(rateLimitToBeSaved);
		
		var requestContent = new AddRatingDTORequest(valueOf(1L), "1number", "dds");
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getHeader("Authorization")).thenReturn("Bearer " + createJwtWithFixedExpirationDate("test@gmail.com", "1", "test name"));
		
		assertThrows(UserRateLimitedOnPoliticianException.class, 
				() -> ratingService.saveRatings(requestContent, req));
	}

}
