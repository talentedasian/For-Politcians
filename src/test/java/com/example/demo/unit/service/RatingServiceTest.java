package com.example.demo.unit.service;

import static com.example.demo.baseClasses.AbstractPoliticianControllerTest.withRepoAndId;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.baseClasses.AbstractEntitiesServiceTest;
import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.jwt.JwtProvider;
import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.RatingService;

@ExtendWith(SpringExtension.class)
public class RatingServiceTest extends AbstractEntitiesServiceTest{

	@Mock
	public RatingRepository ratingRepo;
	@Mock
	public PoliticiansRepository politicianRepo;
	@Mock
	public HttpServletRequest req;
	@Mock
	public AverageCalculator calculator;
	
	public RatingService service;
	public PoliticiansRating rating;
	public Politicians politician;
	
	@BeforeEach
	public void setup() {
		service = new RatingService(ratingRepo, politicianRepo);
		
		List<PoliticiansRating> listOfPoliticiansRating = new ArrayList<>();
		politician = withRepoAndId
				(ratingRepo, 
				1,
				"Mirriam",
				"Defensor",
				listOfPoliticiansRating,
				new Rating(0.01D, 0.01D, calculator));
		
		rating = new PoliticiansRating();
		rating.setId(1);
		rating.setPolitician(politician);
		rating.calculateRater("test@gmail", "test", "DDS");
		rating.setRating(0.01D);
	}
	
	@Test
	public void verifyRatingRepoCalledSaved() {
		stubSaveRepo();
		
		service.saveRatings(new AddRatingDTORequest(BigDecimal.valueOf(0.00D), 
				"Mirriam", "Defensor", PoliticalParty.DDS.toString()),
				req);
		
		verify(ratingRepo, times(1)).save(any());
	}
	
	@Test
	public void assertSavedRepo() {
		stubSaveRepo(); 
		
		PoliticiansRating rating = service.saveRatings(new AddRatingDTORequest(BigDecimal.valueOf(0.01D), 
				"Mirriam", "Defensor", PoliticalParty.DDS.toString()),
				req);
		
		assertThat(rating.getRating(),
				equalTo(rating.getRating()));
	}
	
	@Test
	public void assertSavedRepoUserRater() {
		stubSaveRepo();
		
		PoliticiansRating rating = service.saveRatings(new AddRatingDTORequest(BigDecimal.valueOf(0.01D), 
				"Mirriam", "Defensor", PoliticalParty.DDS.toString()),
				req);
		
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
		
		PoliticiansRating ratings = service.findById("1");
		
		assertThat(ratings.getRating(), 
				equalTo(rating.getRating()));
	}
	
	@Test
	public void assertEqualsQueriedRepoUserRater() {
		when(ratingRepo.findById(1)).thenReturn(Optional.of(rating));
		
		PoliticiansRating ratings = service.findById("1");
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
		
		PoliticiansRating ratings = service.findById("1");
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
		
		List<PoliticiansRating> politicianRatingQueried = service.findRatingsByFacebookEmail("test@gmail.com");
		
		assertThat(listOfPoliticiansRating,
				equalTo(politicianRatingQueried));
	}
	
	
	private void stubSaveRepo() {
		when(politicianRepo.findByLastNameAndFirstName("Mirriam", "Defensor")).thenReturn(Optional.of(politician));
		when(ratingRepo.save(any())).thenReturn(rating);
		
		String jsonWebToken = JwtProvider.createJwtWithFixedExpirationDate("test@gmail.com", "test", "000");
		when(req.getHeader("Authorization")).thenReturn("Bearer " + jsonWebToken);
	}
	
}
