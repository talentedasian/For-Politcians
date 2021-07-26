package com.example.demo.unit.service;

import static com.example.demo.jwt.JwtProvider.createJwtWithFixedExpirationDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.baseClasses.AbstractEntitiesServiceTest;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;
import com.example.demo.jwt.JwtProvider;
import com.example.demo.model.entities.PoliticiansRating;

@ExtendWith(SpringExtension.class)
public class RatingServiceTest extends AbstractEntitiesServiceTest{

	final String NAME = "test";
	final String ID = "123";
	
	@Test
	public void verifyRatingRepoCalledSaved() throws UserRateLimitedOnPoliticianException {
		stubSaveRepo();
		
		when(rateLimitService.isNotRateLimited(any(), any())).thenReturn(true);
		when(req.getHeader("Authorization")).thenReturn("Bearer " + createJwtWithFixedExpirationDate("test@gmail.com", "123", "test"));
		
		ratingService.saveRatings(ratingDtoRequest,req);
		
		verify(ratingRepo, times(1)).save(any());
	}
	
	@Test
	public void assertSavedRepo() throws UserRateLimitedOnPoliticianException {
		stubSaveRepo(); 
		
		when(rateLimitService.isNotRateLimited(any(), any())).thenReturn(true);
		when(req.getHeader("Authorization")).thenReturn("Bearer " + createJwtWithFixedExpirationDate(EMAIL, ID, NAME));
		
		PoliticiansRating rating = ratingService.saveRatings(ratingDtoRequest,req);
		
		assertEquals(rating.getRating(), rating.getRating());
	}
	
	@Test
	public void assertEqualsQueriedRepo() {
		when(ratingRepo.findByRater_UserAccountNumber(ACCOUNT_NUMBER)).thenReturn(List.of(rating));
		
		PoliticiansRating ratings = ratingService.findRatingsByAccountNumber(ACCOUNT_NUMBER).get(0);
		
		assertEquals(ratings.getRating(), rating.getRating());
	}
	
	@Test
	public void assertEqualsDtoOutputs() {
		List<PoliticiansRating> listOfPoliticiansRating = List.of(rating);
		when(ratingRepo.findByRater_Email(EMAIL)).thenReturn(listOfPoliticiansRating);
		
		List<PoliticiansRating> politicianRatingQueried = ratingService.findRatingsByFacebookEmail(EMAIL);
		
		assertEquals(listOfPoliticiansRating, politicianRatingQueried);
	}

	private void stubSaveRepo() {
		when(politicianRepo.findByPoliticianNumber(POLITICIAN_NUMBER)).thenReturn(Optional.of(politician));
		when(ratingRepo.save(any())).thenReturn(rating);
		
		String jsonWebToken = JwtProvider.createJwtWithFixedExpirationDate(EMAIL, ID, NAME);
		when(req.getHeader("Authorization")).thenReturn("Bearer " + jsonWebToken);
	}
	
}
