package com.example.demo.unit.entities;

import org.junit.jupiter.api.Test;

import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.entities.politicians.Politicians.PoliticiansBuilder;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;

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
				.buildWithDifferentBuilder();

		assertTrue(actualPolitician.equals(samePoliticianNumber));
	}

	@Test
	public void shouldReturnFalseWithDifferentPoliticianNumber() {
		var actualPolitician = presidentialBuilder.build();

		var polWrongNumber = presidentialBuilder.setBuilder(politicianBuilder.setPoliticianNumber("differentNumber"))
				.buildWithDifferentBuilder();

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
