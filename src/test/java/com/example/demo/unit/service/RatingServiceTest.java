package com.example.demo.unit.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.jwt.JwtProvider;
import com.example.demo.model.Politicians;
import com.example.demo.model.PoliticiansRating;
import com.example.demo.model.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.RatingService;

@ExtendWith(SpringExtension.class)
public class RatingServiceTest {

	@Mock
	public RatingRepository ratingRepo;
	@Mock
	public PoliticiansRepository politicianRepo;
	@Mock
	public HttpServletRequest req;
	
	public RatingService service;
	public PoliticiansRating ratingToBeSaved;
	public Politicians politicianToBeSaved;
	
	@BeforeEach
	public void setup() {
		service = new RatingService(ratingRepo, politicianRepo);
		
		politicianToBeSaved = new Politicians(1, 0.00D,"Mirriam Defensor", List.of(new PoliticiansRating()), 0.00D);
		ratingToBeSaved = new PoliticiansRating();
		ratingToBeSaved.setId(1);
		ratingToBeSaved.setPolitician(politicianToBeSaved);
		ratingToBeSaved.setRater(new UserRater("test", PoliticalParty.DDS, "test@gmail.com"));
		ratingToBeSaved.setRating(0.00D);
	}
	
	@Test
	public void verifyRatingRepoCalledSaved() {
		when(politicianRepo.findByName("Mirriam Defensor")).thenReturn(Optional.of(politicianToBeSaved));
		when(ratingRepo.save(any())).thenReturn(ratingToBeSaved);
		
		String jsonWebToken = JwtProvider.createJwtWithFixedExpirationDate("test@gmail.com", "test");
		when(req.getHeader("Authorization")).thenReturn("Bearer " + jsonWebToken);
		
		service.saveRatings(new AddRatingDTORequest(BigDecimal.valueOf(0.00D), 
				"Mirriam Defensor", PoliticalParty.DDS.toString()),
				req);
		
		verify(ratingRepo, times(1)).save(any());
	}
	
	@Test
	public void assertEqualsSavedRepoAndQueriedRepo() {
		when(ratingRepo.findById(1)).thenReturn(Optional.of(ratingToBeSaved));
		
		PoliticiansRating ratings = service.findById("1");
		
		assertThat(ratings, 
				equalTo(ratingToBeSaved));
	}
	
	@Test
	public void verifyFindByMethodNameWorks() {
		List<PoliticiansRating> listOfPoliticiansRating = List.of(ratingToBeSaved);
		when(ratingRepo.findByRater_Email("test@gmail.com")).thenReturn(listOfPoliticiansRating);
		
		String jsonWebToken = JwtProvider.createJwtWithFixedExpirationDate("test@gmail.com", "test");
		when(req.getHeader("Authorization")).thenReturn("Bearer " + jsonWebToken);
		
		List<PoliticiansRating> politicianRatingQueried = service.findRatingsByFacebookEmail("test@gmail.com");
		
		assertThat(listOfPoliticiansRating,
				equalTo(politicianRatingQueried));
		
	}
	
}
