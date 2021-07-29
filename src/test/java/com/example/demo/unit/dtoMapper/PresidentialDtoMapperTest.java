package com.example.demo.unit.dtoMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.example.demo.dto.PresidentialPoliticianDTO;
import com.example.demo.dtomapper.PresidentialDtoMapper;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Politicians.PoliticiansBuilder;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.model.enums.Rating;

public class PresidentialDtoMapperTest {

	private final String POLITICIAN_NUMBER = "123polNumber";

	PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
			.setFirstName("Test")
			.setLastName("Name")
			.setRating(new com.example.demo.model.entities.Rating(1.0D, 1.0D, mock(LowSatisfactionAverageCalculator.class)));
	
	PresidentialBuilder presidentialBuilder = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder);
	
	@Test
	public void testDtoMapperWithSingleValue() {
		var actual = new PresidentialPoliticianDTO(presidentialBuilder.build(), Rating.LOW, "Rice Law");
	
		assertEquals(actual,
				new PresidentialDtoMapper().mapToDTO(presidentialBuilder
						.setMostSignificantLawPassed("Rice Law")
						.build()));
	}
}
