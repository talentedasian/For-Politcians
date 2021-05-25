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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.PoliticiansService;

@ExtendWith(SpringExtension.class)
public class PoliticianServiceTest {

	@Mock
	public PoliticiansRepository repo;
	@Mock
	public RatingRepository ratingRepo;
	
	public PoliticiansService service;
	
	public PoliticiansRating rating;
	public Politicians politician;
	
	@BeforeEach
	public void setup() {
		service = new PoliticiansService(repo);
		
		List<PoliticiansRating> listOfPoliticiansRating = new ArrayList<>();
		politician =  new Politicians
				(ratingRepo,
				1,
				"Mirriam", 
				"Defensor", 
				listOfPoliticiansRating);
		politician.setRating(new Rating(0.01D, 0.01D, new LowSatisfactionAverageCalculator(0.01D, 0L)));
		rating = new PoliticiansRating
				(1, 8.023D, 
				new UserRater("test", PoliticalParty.DDS, "test@gmail.com"),
				politician);
		politician.calculateListOfRaters(rating);
	}
	
	@Test
	public void verifyRepoCalledSaveMethod() {
		when(repo.save(politician)).thenReturn(politician); 
		when(repo.countByLastNameAndFirstName("Mirriam", "Defensor")).thenReturn(1L);
		service.savePolitician(new AddPoliticianDTORequest("Mirriam", "Defensor", BigDecimal.valueOf(0.01D)));
		
		verify(repo, times(1)).save(Mockito.any());	
	}
	
	@Test
	public void shouldAddTotalRatingAndCorrectAverageRating() {
		politician.calculateAverageRating();
		when(repo.findByLastNameAndFirstName("Mirriam", "Defensor")).thenReturn(Optional.of(politician));
		
		Politicians politicianQueried = service.findPoliticianByName("Mirriam", "Defensor");
		
		assertThat(0.01D,
				equalTo(politicianQueried.getRating().getAverageRating()));
	}
	
	@Test
	public void shouldEqualDTOOutputs() {
		when(repo.findByLastNameAndFirstName("Mirriam", "Defensor")).thenReturn(Optional.of(politician));
		
		Politicians politicianQueried = service.findPoliticianByName("Mirriam", "Defensor");
		
		assertDtoOutputsUtil(politician, politicianQueried);
	}
	
	
	
	@Test
	public void shouldEqualDTOOutputsWhenSaved() {
		when(repo.save(any(Politicians.class))).thenReturn(politician); 
		when(repo.countByLastNameAndFirstName("Mirriam", "Defensor")).thenReturn(1L);
		
		Politicians politicianQueried = service.savePolitician(new AddPoliticianDTORequest("Mirriam", "Defensor", BigDecimal.valueOf(0.01D)));
		
		assertDtoOutputsUtil(politician, politicianQueried);
	}
	
	public void assertDtoOutputsUtil(Politicians politicianToAssert, Politicians politicianToCompare) {
		assertThat(politicianToAssert.getFirstName(),
				equalTo(politicianToCompare.getFirstName()));
		assertThat(politicianToAssert.getLastName(),
				equalTo(politicianToCompare.getLastName()));
		assertThat(politicianToAssert.getRating().getAverageRating(),
				equalTo(politicianToCompare.getRating().getAverageRating()));
		assertThat(politicianToAssert.getRating().getTotalRating(),
				equalTo(politicianToCompare.getRating().getTotalRating()));
	}
	
}