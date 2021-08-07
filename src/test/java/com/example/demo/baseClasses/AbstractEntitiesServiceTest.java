package com.example.demo.baseClasses;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.dtoRequest.AddRatingDTORequest;
import com.example.demo.dtoRequest.AddSenatorialPoliticianDTORequest;
import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.repository.FakePoliticianRepository;
import com.example.demo.repository.FakeRatingRepo;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;
import com.example.demo.service.PoliticiansService;
import com.example.demo.service.RateLimitingService;
import com.example.demo.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;

public class AbstractEntitiesServiceTest {

	public RatingRepository ratingRepo;
	public PoliticiansRepository politicianRepo;
	@Mock public HttpServletRequest req;
	@Mock public AverageCalculator calculator;
	@Mock public RateLimitingService rateLimitService;
	
	public PoliticiansService politicianService;
	public RatingService ratingService;
	public PoliticiansRating rating;
	public Politicians politician;
	public AddRatingDTORequest ratingDtoRequest;
	public AddPoliticianDTORequest politicianDtoRequest;

	public final String EMAIL = "test@gmail.com";
	public final String ACCOUNT_NUMBER = "123accountNumber";
	public final String FIRST_NAME = "Miriam Palma";
	public final String LAST_NAME = "Defensor-Santiago";
	
	@BeforeEach
	public void setup() {
		politicianRepo = new FakePoliticianRepository();

		ratingRepo = new FakeRatingRepo();

		politicianService = new PoliticiansService(politicianRepo);
		
		ratingService = new RatingService(ratingRepo, politicianRepo, rateLimitService);

		politician = new SenatorialBuilder(new Politicians.PoliticiansBuilder("dummy")
					.setRatingRepository(ratingRepo)
					.setId(1)
					.setFirstName(FIRST_NAME)
					.setLastName(LAST_NAME)
					.setPoliticiansRating(new ArrayList<PoliticiansRating>())
					.setRating(new Rating(0.01D, 0.01D, calculator)))
				.setTotalMonthsOfService(12)
				.build();
		
		rating = new PoliticiansRating();
		rating.setId(1);
		rating.setPolitician(politician);
		rating.calculateRater(EMAIL, "test", "DDS", ACCOUNT_NUMBER, rateLimitService);
		rating.setRating(0.01D);
		
		ratingDtoRequest = new AddRatingDTORequest
		(BigDecimal.valueOf(0.00D),
		politician.getPoliticianNumber(),
	 	PoliticalParty.DDS.toString());
		
		politicianDtoRequest = new AddSenatorialPoliticianDTORequest
				(FIRST_NAME,
				LAST_NAME,
				BigDecimal.valueOf(0.01D),
	12,
		"Anti Corrupt Law");
	}
	
}
