package com.example.demo.unit.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dtoRequest.AddRatingDTORequest;
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
	
	public RatingService service;
	
	@BeforeEach
	public void setup() {
		service = new RatingService(ratingRepo, politicianRepo);
	}
	
	@Test
	public void verifyRatingRepoCalledSaved() {
		PoliticiansRating ratingToBeSaved = new PoliticiansRating();
		Politicians politicianToBeSaved = new Politicians(1, 0.00D,"Mirriam Defensor", List.of(ratingToBeSaved), 0.00D);
		
		ratingToBeSaved.setId(1);
		ratingToBeSaved.setPolitician(politicianToBeSaved);
		ratingToBeSaved.setRater(new UserRater("Dds anyone", PoliticalParty.DDS));
		
		when(politicianRepo.findByName("Mirriam Defensor")).thenReturn(Optional.of(politicianToBeSaved));
		when(ratingRepo.save(ratingToBeSaved)).thenReturn(ratingToBeSaved);
		
		service.saveRatings(new AddRatingDTORequest(BigDecimal.valueOf(0.00D), 
				"Mirriam Defensor", "DDS"),
				any());
		
	}
}
