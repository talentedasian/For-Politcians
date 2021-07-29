package com.example.demo.unit.entities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Politicians.PoliticiansBuilder;
import com.example.demo.model.entities.politicians.PoliticianTypes;

public class PresidentialPoliticianTest {

	final String POLITICIAN_NUMBER = "123polNumber";
	
	PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
			.setFirstName("Test")
			.setLastName("Name");
	
	@Test
	public void shouldReturnTrueWithSamePoliticianNumber() {
		var actualPolitician = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder)
				.build();
		
		var samePoliticianNumber = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder)
				.build();
		
		var polWrongNumber = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder.setPoliticianNumber("123differentNumber"))
				.build();
		
		assertTrue(actualPolitician.equals(samePoliticianNumber));
		assertFalse(actualPolitician.equals(polWrongNumber));
	}
	
	@Test
	public void shouldReturnFalseWithDifferentPoliticianType() {
		var presidentialType = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder)
				.build();
		
		var differentPoliticianType = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politicianBuilder.setPoliticianNumber("1"))
				.build();
		
		assertFalse(presidentialType.equals(differentPoliticianType));
	}
	
}
