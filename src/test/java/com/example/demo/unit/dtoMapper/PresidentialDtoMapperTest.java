package com.example.demo.unit.dtoMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.dto.PresidentialPoliticianDTO;
import com.example.demo.dtomapper.PresidentialDtoMapper;
import com.example.demo.model.averageCalculator.DecentSatisfactionAverageCalculator;
import com.example.demo.model.averageCalculator.HighSatisfactionAverageCalculator;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Politicians.PoliticiansBuilder;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.model.enums.Rating;

public class PresidentialDtoMapperTest {

	private final String POLITICIAN_NUMBER = "123polNumber";

	PoliticiansBuilder politicianBuilder;
	
	PresidentialBuilder presidentialBuilder;
	
	double TOTAL_RATING = 1.0D;
	com.example.demo.model.entities.Rating lowRating = new com.example.demo.model.entities.Rating(TOTAL_RATING, 2.0D, mock(LowSatisfactionAverageCalculator.class));
	com.example.demo.model.entities.Rating decentRating = new com.example.demo.model.entities.Rating(TOTAL_RATING, 7.9D, mock(DecentSatisfactionAverageCalculator.class));
	com.example.demo.model.entities.Rating highRating = new com.example.demo.model.entities.Rating(TOTAL_RATING, 9.22D, mock(HighSatisfactionAverageCalculator.class));
	com.example.demo.model.entities.Rating[] ratings = {lowRating, decentRating, highRating};
	String[] lawSignedSequentially = {"Rice Law", "Any Law", "Gas Oil"};
	
	@BeforeEach
	public void setup() {
		politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
				.setFirstName("Test")
				.setLastName("Name")
				.setRating(new com.example.demo.model.entities.Rating(1.0D, 1.0D, mock(LowSatisfactionAverageCalculator.class)));
		
		presidentialBuilder = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder);
	}
	
	@Test
	public void testDtoMapperWithSingleValue() {
		var actual = new PresidentialPoliticianDTO(presidentialBuilder.build(), Rating.LOW, "Rice Law");
	
		assertEquals(actual,
				new PresidentialDtoMapper().mapToDTO(presidentialBuilder
						.setMostSignificantLawPassed("Rice Law")
						.build()));
	}
	
	@Test
	public void testDtoMapperWithMultipleValues() {
		assertThat(streamOfPresidentialPoliticianDTO())
			.containsAll(new PresidentialDtoMapper().mapToDTO(streamOfPresidentialPoliticians()));
	}
	
	private List<PresidentialPoliticianDTO> streamOfPresidentialPoliticianDTO() {
		List<PresidentialPoliticianDTO> values = new ArrayList<>();
		
		int i = 0;
		while (i < 3) {
			values.add(new PresidentialDtoMapper().mapToDTO(presidentialBuilder
					.setMostSignificantLawPassed(lawSignedSequentially[i])
					.setBuilder(politicianBuilder.setRating(ratings[i++]))
					.buildWithDifferentBuilder()));
		}
		
		return values;
	}

	private List<Politicians> streamOfPresidentialPoliticians() {
		List<Politicians> values = new ArrayList<>();
		
		int i = 0;
		while (i < 3) {
			values.add(presidentialBuilder
					.setMostSignificantLawPassed(lawSignedSequentially[i])
					.setBuilder(politicianBuilder.setRating(ratings[i++]))
					.buildWithDifferentBuilder());
		}
		
		return values;
	}
	
}
