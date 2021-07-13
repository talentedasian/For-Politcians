package com.example.demo.unit.entities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.demo.model.entities.Politicians;

public class PoliticiansTest {

	Politicians politicianWithoutPolNumber = new Politicians.PoliticiansBuilder()
			.setFirstName("Test")
			.setLastName("Name")
			.build();
	
	Politicians politicianWithPolNumber = new Politicians.PoliticiansBuilder()
			.setPoliticianNumber("123polNumber")
			.setFirstName("Test")
			.setLastName("Name")
			.build();
	
	@Test
	public void assertCustomEqualsMethod() {
		var pol = new Politicians();
		pol.setPoliticianNumber("123polNumber");
		
		assertTrue(politicianWithPolNumber.equals(pol));
		assertFalse(politicianWithoutPolNumber.equals(pol));
	}
	
}
