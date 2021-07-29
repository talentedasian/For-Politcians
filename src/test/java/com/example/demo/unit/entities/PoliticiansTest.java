package com.example.demo.unit.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Politicians.PoliticiansBuilder;

public class PoliticiansTest {

	final String POLITICIAN_NUMBER = "123polNumber";
	
	PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
			.setFirstName("Test")
			.setLastName("Name");
	
	@Test
	public void shouldReturnTrueWithSamePoliticianNumber() {
		var actualPolitician = politicianBuilder.build();
		
		var samePoliticianNumber = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER).build();
		
		var polWrongNumber = politicianBuilder.setPoliticianNumber("999polNumber").build();
		
		assertTrue(actualPolitician.equals(samePoliticianNumber));
		assertFalse(actualPolitician.equals(polWrongNumber));
	}
	
	@Test
	public void testNullLastNameInBuilder() {
		var politicianWithNoLastName = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
				.setFirstName("Test")
				.setFullName()
				.build();
		
		assertEquals("Test", politicianWithNoLastName.getFullName());
	}
	
	@Test
	public void testFullNameInBuilder() {
		var politicianWithFirstAndLastName = politicianBuilder
				.setFullName()
				.build();
		
		assertEquals("Test Name", politicianWithFirstAndLastName.getFullName());
	}
	
	@Test
	public void testNullFirstAndLastNameInBuilder() {
		assertThrows(IllegalArgumentException.class, 
				() -> new Politicians.PoliticiansBuilder("123polNumber")
				.setFullName());
	}
	
}
