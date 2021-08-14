package com.example.demo.unit.service;

import com.example.demo.adapter.in.dtoRequest.AddRatingDTORequest;
import com.example.demo.adapter.in.service.RateLimitingService;
import com.example.demo.adapter.in.service.RatingService;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

import static com.example.demo.adapter.in.web.jwt.JwtJjwtProviderAdapater.createJwtWithFixedExpirationDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class RatingServiceTest {

	final String NAME = "test";
	final String ID = "123";

	RatingRepository ratingRepo;
	PoliticiansRepository politicianRepo;
	@Mock HttpServletRequest req;
	@Mock RateLimitingService rateLimitService;

	RatingService ratingService;
	PoliticiansRating rating;
	Politicians politician;
	AddRatingDTORequest ratingDtoRequest;

	final String EMAIL = "test@gmail.com";
	final String ACCOUNT_NUMBER = "123accountNumber";

//	@BeforeEach
//	public void setup() {
//		ratingRepo = new FakeRatingRepo();
//
//		politicianRepo = new FakePoliticianRepository();
//
//		ratingService = new RatingService(ratingRepo, politicianRepo, rateLimitService);
//
//		politician = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(new Politicians.PoliticiansBuilder("dummy")
//				.setRatingRepository(ratingRepo)
//				.setId(1)
//				.setFirstName("Nancy")
//				.setLastName("Binay")
//				.setPoliticiansRating(new ArrayList<PoliticiansRating>())
//				.setRating(new Rating(0.00D, 0.00D)))
//				.setTotalMonthsOfService(12)
//				.build();
//
//		rating = new PoliticiansRating();
//		rating.setPolitician(politician);
//		rating.calculateRater(EMAIL, ID, "DDS", ACCOUNT_NUMBER, rateLimitService);
//		rating.setRating(0.01D);
//
//		ratingDtoRequest = new AddRatingDTORequest
//				(BigDecimal.valueOf(0.00D),
//				politician.getPoliticianNumber(),
//				PoliticalParty.DDS.toString());
//	}
//
//	@Test
//	public void shouldReturnEqualRatingWhenSaved() throws UserRateLimitedOnPoliticianException {
//		politicianRepo.save(politician);
//
//		when(rateLimitService.isNotRateLimited(any(), any())).thenReturn(true);
//		when(req.getHeader("Authorization")).thenReturn("Bearer " + createJwtWithFixedExpirationDate(EMAIL, ID, NAME));
//
//		PoliticiansRating ratingSaved = ratingService.saveRatings(ratingDtoRequest, req);
//
//		assertThat(ratingRepo.findById(ratingSaved.getId()))
//				.isNotEmpty()
//				.get().isEqualTo(ratingSaved);
//	}
	
	@Test
	public void shouldReturnAllRatingsWithSameAccountNumber() {
		politicianRepo.save(politician);

		List<PoliticiansRating> listOfPoliticiansRating = List.of(rating);
		ratingRepo.save(rating);
		
		List<PoliticiansRating> politiciansRatingQueried = ratingService.findRatingsByAccountNumber(ACCOUNT_NUMBER);
		
		assertThat(politiciansRatingQueried)
				.containsAll(listOfPoliticiansRating);
	}
	
	@Test
	public void shouldReturnAllRatingsWithSameFacebookEmail() {
		politicianRepo.save(politician);

		List<PoliticiansRating> listOfPoliticiansRating = List.of(rating);
		ratingRepo.save(rating);
		
		List<PoliticiansRating> politicianRatingQueried = ratingService.findRatingsByFacebookEmail(EMAIL);

		assertThat(politicianRatingQueried)
				.containsAll(listOfPoliticiansRating);
	}

	@Test
	public void shouldReturnCorrectCalculationForAverageRatingWithLowAverageRatings() throws UserRateLimitedOnPoliticianException {
		politicianRepo.save(politician);

		when(rateLimitService.isNotRateLimited(any(), any())).thenReturn(true);
		when(req.getHeader("Authorization")).thenReturn("Bearer " + createJwtWithFixedExpirationDate(EMAIL, ID, NAME));

		ratingDtoRequest.setRating(BigDecimal.valueOf(2.012));
//		ratingService.saveRatings(ratingDtoRequest, req);
		ratingDtoRequest.setRating(BigDecimal.valueOf(3.01665));
//		ratingService.saveRatings(ratingDtoRequest, req);

		assertThat(5.03D)
				.isEqualTo(politicianRepo.findByPoliticianNumber(politician.getPoliticianNumber()).get().getRating().getAverageRating());
	}


}
