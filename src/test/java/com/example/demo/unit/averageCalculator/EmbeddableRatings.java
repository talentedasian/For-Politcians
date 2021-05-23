package com.example.demo.unit.averageCalculator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.averageCalculator.DecentSatisfactionAverageCalculator;
import com.example.demo.model.averageCalculator.HighSatisfactionAverageCalculator;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Rating;
import com.example.demo.repository.RatingRepository;

@ExtendWith(MockitoExtension.class)
public class EmbeddableRatings {

	@Mock
	public RatingRepository repo;
	
	@Test
	public void test() {
		when(repo.countByPolitician_Id(1)).thenReturn(0L);
		
		Politicians politician = new Politicians();
		politician.setRepo(repo);
		politician.setId(1);
		politician.setFirstName("Mirriam");
		politician.setLastName("Defensor");
		politician.setPoliticiansRating(new ArrayList<>());
		politician.setRating(new Rating(0.012D,
				2.022D, 
				new LowSatisfactionAverageCalculator(0.012D, repo.countByPolitician_Id(1))));
		
		assertThat(politician.getRating().calculateAverage(convertLongToDouble(repo.countByPolitician_Id(1))).getAverageRating(), 
				equalTo(0.02D));
	}
	
	private Double convertLongToDouble(long longValue) {
		return Double.valueOf(String.valueOf(longValue));
	}
	
}
