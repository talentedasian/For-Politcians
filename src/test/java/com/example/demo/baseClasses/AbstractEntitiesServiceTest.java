package com.example.demo.baseClasses;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.PoliticiansService;
import com.example.demo.service.RateLimitingService;
import com.example.demo.service.RatingService;

public class AbstractEntitiesServiceTest {

	@Mock
	public RatingRepository ratingRepo;
	@Mock
	public PoliticiansRepository politicianRepo;
	@Mock
	public HttpServletRequest req;
	@Mock
	public AverageCalculator calculator;
	@Mock
	public RateLimitingService rateLimitService;
	
	public PoliticiansService politicianService;
	public RatingService ratingService;
	public PoliticiansRating rating;
	public Politicians politician;
	public final String polNumber = "FL00-LF00-0000";
	public AddRatingDTORequest ratingDtoRequest;
	public AddPoliticianDTORequest politicianDtoRequest;
	

	@BeforeEach
	public void setup() {
		politicianService = new PoliticiansService(politicianRepo);
		
		ratingService = new RatingService(ratingRepo, politicianRepo, rateLimitService);
		
		politician = new Politicians.PoliticiansBuilder("123polNumber")
				.setRatingRepository(ratingRepo)
				.setId(1)
				.setFirstName("Mirriam")
				.setLastName("Defensor")
				.setPoliticiansRating(new ArrayList<PoliticiansRating>())
				.setRating(new Rating(0.01D, 0.01D, calculator))
				.build();
		
		rating = new PoliticiansRating();
		rating.setId(1);
		rating.setPolitician(politician);
		rating.calculateRater("test@gmail", "test", "DDS", "123accountNumber", rateLimitService);
		rating.setRating(0.01D);
		
		ratingDtoRequest = new AddRatingDTORequest
		(BigDecimal.valueOf(0.00D),
		polNumber,
		 PoliticalParty.DDS.toString());
		
		politicianDtoRequest = new AddPoliticianDTORequest
				("Mirriam", 
				"Defensor",
				BigDecimal.valueOf(0.01D));
	}
	
}
