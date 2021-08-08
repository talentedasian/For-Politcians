package com.example.demo.unit;

import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmbeddableRatingsTest {

	@Mock
	public RatingRepository repo;
	@Mock
	public AverageCalculator calculator; 
	
	public Politicians politician;
	
	@BeforeEach
	public void setup() {
		politician = new Politicians.PoliticiansBuilder("123polNumber")
			.setRatingRepository(repo)
			.setId(1)
			.setFirstName("Mirriam")
			.setLastName("Defensor")
			.setPoliticiansRating(new ArrayList<>())
			.setRating(new Rating
				(0.012D,
				2.022D))
			.build();
	}
	
	@Test
	public void testLogicOfAverage() {
		when(repo.countByPolitician_Id(1)).thenReturn(0L);
		
		politician.calculateTotalAmountOfRating(9.8822D);
		
		assertEquals(politician.getRating().calculateAverage(), (9.9D));
	}
	
	@Test
	public void testLogicOfTotalAmount() {
		when(repo.countByPolitician_Id(1)).thenReturn(0L);
		
		assertEquals(politician.getRating().calculateTotalAmountOfRating(8.8876D, convertLongToDouble(repo.countByPolitician_Id(1))), 
				(8.9D));
	}
	
	private Double convertLongToDouble(long longValue) {
		return Double.valueOf(String.valueOf(longValue));
	}
	
}
