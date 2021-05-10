package com.example.demo.unit.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.model.Politicians;
import com.example.demo.model.PoliticiansRating;
import com.example.demo.model.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.PoliticiansService;
import com.example.demo.service.RatingService;

@ExtendWith(SpringExtension.class)
public class PoliticianServiceTest {

	@Mock
	public PoliticiansRepository repo;
	@Mock
	public RatingRepository ratingRepo;
	
	public PoliticiansService service;
	public RatingService ratingService;
	
	public PoliticiansRating rating;
	public Politicians politician;
	
	@BeforeEach
	public void setup() {
		service = new PoliticiansService(repo);
		ratingService = new RatingService(ratingRepo, repo);
		
		politician =  new Politicians
				(null, 0.00D,
						"Mirriam Defensor", 
						List.of(new PoliticiansRating()),
						0.00D);
		rating = new PoliticiansRating
				(1, 8.023D, 
				new UserRater("test", PoliticalParty.DDS),
				politician);
	}
	
	@Test
	public void verifyRepoCalledSaveMethod() {
		when(repo.save(politician)).thenReturn(politician); 
		
		service.savePolitician(new AddPoliticianDTORequest("Mirriam Defensor", BigDecimal.valueOf(0.00D)));
		
		verify(repo, times(1)).save(Mockito.any());
		
	}
	
	@Test
	public void shouldAddTotalRating() {
		politician.setTotalRating(8.023D);
		politician.setRating(politician.getTotalRating() / Double.valueOf(politician.getPoliticiansRating().size()));
		when(repo.findByName("Mirriam Defensor")).thenReturn(Optional.of(politician));
			
		Politicians politicianQueried = service.findPoliticianByName("Mirriam Defensor");
		
		assertThat(8.023D,
				equalTo(politicianQueried.getRating()));
	}
}