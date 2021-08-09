package com.example.demo.unit.entities;

import org.junit.jupiter.api.Test;

import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicians.Politicians.PoliticiansBuilder;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PresidentialPoliticianTest {

	final String POLITICIAN_NUMBER = "123polNumber";
	
	PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
			.setFirstName("Test")
			.setLastName("Name");
	
	PresidentialBuilder presidentialBuilder = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder);
	
	@Test
	public void shouldReturnTrueWithSamePoliticianNumber() {
		var actualPolitician = presidentialBuilder.build();
		
		var samePoliticianNumber = presidentialBuilder.setBuilder(politicianBuilder.setPoliticianNumber(POLITICIAN_NUMBER))
				.build();

		assertTrue(actualPolitician.equals(samePoliticianNumber));
	}

	/*
		basically testing the hashcode functionality
	 */
	@Test
	public void shouldReturnObjectInHashMap() {
		var actualPolitician = presidentialBuilder.build();

		Map<Politicians,Politicians> map = new HashMap<>();
		map.put(actualPolitician,actualPolitician);

		var samePoliticianNumber = presidentialBuilder.setBuilder(politicianBuilder.setPoliticianNumber(POLITICIAN_NUMBER))
				.build();

		assertEquals(actualPolitician, map.get(samePoliticianNumber));
	}

	@Test
	public void keyShouldNotBeInMapEvenWithSamePoliticianNumber() {
		var actualPolitician = presidentialBuilder.build();

		Map<Politicians,Politicians> map = new HashMap<>();
		map.put(actualPolitician,actualPolitician);

		var differentPoliticianType = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politicianBuilder)
				.setTotalMonthsOfService(89)
				.build();

		assertFalse(map.containsKey(differentPoliticianType));
	}

	@Test
	public void shouldReturnFalseWithDifferentPoliticianNumber() {
		var actualPolitician = presidentialBuilder.build();

		var polWrongNumber = presidentialBuilder.setBuilder(politicianBuilder.setPoliticianNumber("differentNumber"))
				.build();

		assertFalse(actualPolitician.equals(polWrongNumber));
	}
	
	@Test
	public void shouldReturnFalseWithDifferentPoliticianType() {
		var differentPoliticianType = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politicianBuilder)
				.setTotalMonthsOfService(89)
				.build();
		
		assertFalse(presidentialBuilder.build().equals(differentPoliticianType));
	}
	
}
