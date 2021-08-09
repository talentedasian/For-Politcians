package com.example.demo.integration;

import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.RateLimit;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;
import com.example.demo.repository.*;
import com.example.demo.service.RateLimitingService;
import com.example.demo.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static com.example.demo.jwt.JwtProvider.createJwtWithFixedExpirationDate;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class RateLimitIntegrationTest {

	@Mock RateLimitRepository rateLimitRepo;
	RatingService ratingService;
	PoliticiansRepository polRepo;
	RatingRepository ratingRepo;
	RateLimitingService rateLimitService;
	RateLimit rateLimitToBeSaved = new RateLimit();

	final String EMAIL = "test@gmail.com";

	PresidentialPolitician politician = new PresidentialBuilder(new Politicians.PoliticiansBuilder("dummy")
				.setId(1)
				.setPoliticiansRating(new ArrayList<PoliticiansRating>())
				.setRating(new Rating(1D, 1D))
				.setFirstName("test")
				.setLastName("name")
				.setFullName())
			.build();

	final String POLITICIAN_NUMBER = politician.getPoliticianNumber();

	@BeforeEach
	public void setup() {
		polRepo = new FakePoliticianRepository();

		ratingRepo = new FakeRatingRepo();

		rateLimitService = new RateLimitingService(rateLimitRepo);

		ratingService = new RatingService(ratingRepo, polRepo, rateLimitService);

		rateLimitToBeSaved.setDateCreated(LocalDate.now().minusDays(5L));
		rateLimitToBeSaved.setId(FacebookUserRaterNumberImplementor.with("test name", "1").calculateEntityNumber().getAccountNumber());
		rateLimitToBeSaved.setPoliticianNumber(POLITICIAN_NUMBER);
	}
	
	@Test
	public void shouldThrowRateLimitedExceptionWhenUserIsRateLimited() throws UserRateLimitedOnPoliticianException {
		polRepo.save(politician);

		when(rateLimitRepo.findByIdAndPoliticianNumber(anyString(), anyString())).thenReturn(Optional.of(rateLimitToBeSaved));
		
		var requestContent = new AddRatingDTORequest(valueOf(1L), POLITICIAN_NUMBER, "dds");
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getHeader("Authorization")).thenReturn("Bearer " + createJwtWithFixedExpirationDate("test@gmail.com", "1", "test name"));
		
		assertThrows(UserRateLimitedOnPoliticianException.class, 
				() -> ratingService.saveRatings(requestContent, req));
	}

	@Test
	public void shouldSaveAsUserIsNotRateLimited() throws UserRateLimitedOnPoliticianException {
		polRepo.save(politician);

		rateLimitToBeSaved.setDateCreated(LocalDate.now().minusDays(8));
		when(rateLimitRepo.findByIdAndPoliticianNumber(anyString(), anyString())).thenReturn(Optional.of(rateLimitToBeSaved));

		var requestContent = new AddRatingDTORequest(valueOf(1L), POLITICIAN_NUMBER, "dds");
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getHeader("Authorization")).thenReturn("Bearer " + createJwtWithFixedExpirationDate(EMAIL, "1", "test name"));

		ratingService.saveRatings(requestContent, req);

		assertThat(ratingRepo.findByRater_Email(EMAIL))
				.isNotEmpty();
	}

}
