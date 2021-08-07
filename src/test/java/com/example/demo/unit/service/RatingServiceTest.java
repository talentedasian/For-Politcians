package com.example.demo.unit.service;

import com.example.demo.baseClasses.AbstractEntitiesServiceTest;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import com.example.demo.model.entities.PoliticiansRating;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.example.demo.jwt.JwtProvider.createJwtWithFixedExpirationDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class RatingServiceTest extends AbstractEntitiesServiceTest{

	final String NAME = "test";
	final String ID = "123";
	
	@Test
	public void shouldReturnEqualRatingWhenSaved() throws UserRateLimitedOnPoliticianException {
		politicianRepo.save(politician);

		when(rateLimitService.isNotRateLimited(any(), any())).thenReturn(true);
		when(req.getHeader("Authorization")).thenReturn("Bearer " + createJwtWithFixedExpirationDate(EMAIL, ID, NAME));

		PoliticiansRating ratingSaved = ratingService.saveRatings(ratingDtoRequest, req);

		assertEquals(rating, ratingSaved);
	}
	
	@Test
	public void shouldReturnAllRatingsWithSameAccountNumber() {
		List<PoliticiansRating> listOfPoliticiansRating = List.of(rating);
		ratingRepo.save(rating);
		
		List<PoliticiansRating> politiciansRatingQueried = ratingService.findRatingsByAccountNumber(ACCOUNT_NUMBER);
		
		assertThat(politiciansRatingQueried)
				.containsAll(listOfPoliticiansRating);
	}
	
	@Test
	public void shouldReturnAllRatingsWithSameFacebookEmail() {
		List<PoliticiansRating> listOfPoliticiansRating = List.of(rating);
		ratingRepo.save(rating);
		
		List<PoliticiansRating> politicianRatingQueried = ratingService.findRatingsByFacebookEmail(EMAIL);

		assertThat(politicianRatingQueried)
				.containsAll(listOfPoliticiansRating);
	}
	
}
