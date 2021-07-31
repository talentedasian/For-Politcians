package com.example.demo.unit.dtoMapper;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dto.PresidentialPoliticianDTO;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.model.enums.Rating;
import com.example.demo.unit.dto.BaseClassForPoliticianDTOTests;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PresidentialDtoMapperTest extends BaseClassForPoliticianDTOTests {
	
	@Test
	public void testDtoMapperWithSingleValue() {
		PoliticianDTO actual = new PresidentialPoliticianDTO(presidentialBuilder.build(), Rating.LOW, "Rice Law");
	
		assertEquals(actual,
				new PoliticiansDtoMapper().mapToDTO(presidentialBuilder
						.setMostSignificantLawPassed("Rice Law")
						.build()));
	}
	
	@Test
	public void testDtoMapperWithMultipleValues() {
		assertThat(streamOfPresidentialPoliticianDTO())
			.containsAll(new PoliticiansDtoMapper().mapToDTO(streamOfPresidentialPoliticians()));
	}
	
}
