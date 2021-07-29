package com.example.demo.unit.entities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Politicians.PoliticiansBuilder;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;

public class PresidentialPoliticianTest {

	final String POLITICIAN_NUMBER = "123polNumber";
	
	PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
			.setFirstName("Test")
			.setLastName("Name");
	
	PresidentialBuilder presidentialBuilder = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder);
	
	@Test
	public void shouldReturnTrueWithSamePoliticianNumber() {
		var actualPolitician = presidentialBuilder.build();
		
		var samePoliticianNumber = presidentialBuilder.build();
		
		var polWrongNumber = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder.setPoliticianNumber("123differentNumber"))
				.build();
		
		assertTrue(actualPolitician.equals(samePoliticianNumber));
		assertFalse(actualPolitician.equals(polWrongNumber));
	}
	
	@Test
	public void shouldReturnFalseWithDifferentPoliticianType() {
		var differentPoliticianType = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politicianBuilder)
				.build();
		
		assertFalse(presidentialBuilder.build().equals(differentPoliticianType));
	}
	
}
