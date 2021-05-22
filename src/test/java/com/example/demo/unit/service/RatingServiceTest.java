package com.example.demo.unit.service;

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
		
		List<PoliticiansRating> listOfPoliticiansRating = new ArrayList<>();
		politicianToBeSaved = new Politicians(1, 0.01D,"Mirriam", "Defensor", listOfPoliticiansRating, 0.01D, politicianRepo);
		
		ratingToBeSaved = new PoliticiansRating();
		ratingToBeSaved.setId(1);
		ratingToBeSaved.setPolitician(politicianToBeSaved);
		ratingToBeSaved.setRater(new UserRater("test", PoliticalParty.DDS, "test@gmail.com"));
		ratingToBeSaved.setRating(0.01D);
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
				equalTo(ratingToBeSaved.getRating()));
	}
	
	@Test
	public void assertSavedRepoUserRater() {
		stubSaveRepo();
		
		PoliticiansRating rating = service.saveRatings(new AddRatingDTORequest(BigDecimal.valueOf(0.01D), 
				"Mirriam", "Defensor", PoliticalParty.DDS.toString()),
				req);
		
		assertThat(rating.getRater().getEmail(),
				equalTo(ratingToBeSaved.getRater().getEmail()));
		assertThat(rating.getRater().getFacebookName(),
				equalTo(ratingToBeSaved.getRater().getFacebookName()));
		assertThat(rating.getRater().getPoliticalParties(),
				equalTo(ratingToBeSaved.getRater().getPoliticalParties()));
	}
	
	@Test
	public void assertEqualsQueriedRepo() {
		when(ratingRepo.findById(1)).thenReturn(Optional.of(ratingToBeSaved));
		
		PoliticiansRating ratings = service.findById("1");
		
		assertThat(ratings.getRating(), 
				equalTo(ratingToBeSaved.getRating()));
	}
	
	@Test
	public void assertEqualsQueriedRepoUserRater() {
		when(ratingRepo.findById(1)).thenReturn(Optional.of(ratingToBeSaved));
		
		PoliticiansRating ratings = service.findById("1");
		UserRater rater = ratings.getRater();
		
		assertThat(rater.getEmail(), 
				equalTo(ratingToBeSaved.getRater().getEmail()));
		assertThat(rater.getFacebookName(), 
				equalTo(ratingToBeSaved.getRater().getFacebookName()));
		assertThat(rater.getPoliticalParties(), 
				equalTo(ratingToBeSaved.getRater().getPoliticalParties()));
	}
	
	@Test
	public void assertEqualsQueriedRepoPolitician() {
		when(ratingRepo.findById(1)).thenReturn(Optional.of(ratingToBeSaved));
		
		PoliticiansRating ratings = service.findById("1");
		Politicians pol = ratings.getPolitician();
		String polFullName = pol.calculateFullName();
		
		assertThat(pol.getFirstName(), 
				equalTo(ratingToBeSaved.getPolitician().getFirstName()));
		assertThat(pol.getLastName(), 
				equalTo(ratingToBeSaved.getPolitician().getLastName()));
		assertThat(polFullName, 
				equalTo(ratingToBeSaved.getPolitician().getFullName()));
		assertThat(pol.getRating(), 
				equalTo(ratingToBeSaved.getPolitician().getRating()));
		assertThat(pol.getTotalRating(), 
				equalTo(ratingToBeSaved.getPolitician().getTotalRating()));
	}
	
	@Test
	public void assertEqualsQueriedRepoPoliticianRatings() {
		when(ratingRepo.findById(1)).thenReturn(Optional.of(ratingToBeSaved));
		
		PoliticiansRating ratings = service.findById("1");
		Politicians pol = ratings.getPolitician();
		pol.calculateTotalAmountOfRating(ratingToBeSaved.getRating());
		pol.calculateAverageRating();
		
		assertThat(pol.getTotalRating(),
				equalTo(ratingToBeSaved.getPolitician().getTotalRating()));
		assertThat(pol.getRating(),
				equalTo(ratingToBeSaved.getPolitician().getRating()));
	}
	
	@Test
	public void assertEqualsDtoOutputs() {
		List<PoliticiansRating> listOfPoliticiansRating = List.of(ratingToBeSaved);
		when(ratingRepo.findByRater_Email("test@gmail.com")).thenReturn(listOfPoliticiansRating);
		
		List<PoliticiansRating> politicianRatingQueried = service.findRatingsByFacebookEmail("test@gmail.com");
		
		assertThat(listOfPoliticiansRating,
				equalTo(politicianRatingQueried));
		
	}
	
	@Test
	public void assertAverageRatingLogic() {
		Politicians pol = new Politicians();
		pol.setRepo(politicianRepo);
		pol.setTotalRating(0.00D);
		pol.calculateTotalAmountOfRating(2.20D);
		double averageRating = pol.calculateAverageRating();
		
		assertThat(averageRating,
				equalTo(2.2D));
	}
	
	private void stubSaveRepo() {
		when(politicianRepo.findByLastNameAndFirstName("Mirriam", "Defensor")).thenReturn(Optional.of(politicianToBeSaved));
		when(ratingRepo.save(any())).thenReturn(ratingToBeSaved);
		
		String jsonWebToken = JwtProvider.createJwtWithFixedExpirationDate("test@gmail.com", "test");
		when(req.getHeader("Authorization")).thenReturn("Bearer " + jsonWebToken);
	}
	
}
