package com.example.demo.unit.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.entities.politicians.Politicians.PoliticiansBuilder;
import com.example.demo.model.entities.politicians.PoliticianTypes;

public class PoliticiansTest {

	final String POLITICIAN_NUMBER = "123polNumber";
	
	PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
			.setFirstName("Test")
			.setLastName("Name");
	
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