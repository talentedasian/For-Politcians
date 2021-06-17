package com.example.demo.unit.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
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
import com.example.demo.jwt.JwtProvider;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.UserRater;

@ExtendWith(SpringExtension.class)
public class RatingServiceTest extends AbstractEntitiesServiceTest{

	@Test
	public void verifyRatingRepoCalledSaved() {
		stubSaveRepo();
		
		ratingService.saveRatings(ratingDtoRequest,req);
		
		verify(ratingRepo, times(1)).save(any());
	}
	
	@Test
	public void assertSavedRepo() {
		stubSaveRepo(); 
		
		PoliticiansRating rating = ratingService.saveRatings(ratingDtoRequest,req);
		
		assertThat(rating.getRating(),
				equalTo(rating.getRating()));
	}
	
	@Test
	public void assertSavedRepoUserRater() {
		stubSaveRepo();
		
		PoliticiansRating rating = ratingService.saveRatings(ratingDtoRequest,req);
		
		assertThat(rating.getRater().getEmail(),
				equalTo(rating.getRater().getEmail()));
		assertThat(rating.getRater().getFacebookName(),
				equalTo(rating.getRater().getFacebookName()));
		assertThat(rating.getRater().getPoliticalParties(),
				equalTo(rating.getRater().getPoliticalParties()));
	}
	
	@Test
	public void assertEqualsQueriedRepo() {
		when(ratingRepo.findById(1)).thenReturn(Optional.of(rating));
		
		PoliticiansRating ratings = ratingService.findById("1");
		
		assertThat(ratings.getRating(), 
				equalTo(rating.getRating()));
	}
	
	@Test
	public void assertEqualsQueriedRepoUserRater() {
		when(ratingRepo.findById(1)).thenReturn(Optional.of(rating));
		
		PoliticiansRating ratings = ratingService.findById("1");
		UserRater rater = ratings.getRater();
		
		assertThat(rater.getEmail(), 
				equalTo(rating.getRater().getEmail()));
		assertThat(rater.getFacebookName(), 
				equalTo(rating.getRater().getFacebookName()));
		assertThat(rater.getPoliticalParties(), 
				equalTo(rating.getRater().getPoliticalParties()));
	}
	
	@Test
	public void assertEqualsQueriedRepoPolitician() {
		when(ratingRepo.findById(1)).thenReturn(Optional.of(rating));
		
		PoliticiansRating ratings = ratingService.findById("1");
		Politicians pol = ratings.getPolitician();
		String polFullName = pol.calculateFullName();
		
		assertThat(pol.getFirstName(), 
				equalTo(rating.getPolitician().getFirstName()));
		assertThat(pol.getLastName(), 
				equalTo(rating.getPolitician().getLastName()));
		assertThat(polFullName, 
				equalTo(rating.getPolitician().getFullName()));
		assertThat(pol.getRating(), 
				equalTo(rating.getPolitician().getRating()));
		assertThat(pol.getRating().getTotalRating(), 
				equalTo(rating.getPolitician().getRating().getTotalRating()));
	}
	
	@Test
	public void assertEqualsDtoOutputs() {
		List<PoliticiansRating> listOfPoliticiansRating = List.of(rating);
		when(ratingRepo.findByRater_Email("test@gmail.com")).thenReturn(listOfPoliticiansRating);
		
		List<PoliticiansRating> politicianRatingQueried = ratingService.findRatingsByFacebookEmail("test@gmail.com");
		
		assertThat(listOfPoliticiansRating,
				equalTo(politicianRatingQueried));
	}
	
	
	private void stubSaveRepo() {
		when(politicianRepo.findByPoliticianNumber(polNumber)).thenReturn(Optional.of(politician));
		when(ratingRepo.save(any())).thenReturn(rating);
		
		String jsonWebToken = JwtProvider.createJwtWithFixedExpirationDate("test@gmail.com", "test", "000");
		when(req.getHeader("Authorization")).thenReturn("Bearer " + jsonWebToken);
	}
	
}
