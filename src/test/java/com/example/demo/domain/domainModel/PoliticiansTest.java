package com.example.demo.domain.domainModel;

import com.example.demo.domain.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicians.Politicians.PoliticiansBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		
		assertEquals("Test", politicianWithNoLastName.fullName());
	}
	
	@Test
	public void testFullNameInBuilder() {
		var politicianWithFirstAndLastName = politicianBuilder
				.setFullName()
				.build();
		
		assertEquals("Test Name", politicianWithFirstAndLastName.fullName());
	}

	@Test
	public void testHashCodeActuallyWorksAsIntended() {
		int expectedHashCode = new SenatorialBuilder(politicianBuilder)
				.setTotalMonthsOfService(12)
				.build()
				.hashCode();
		Politicians politician = politicianBuilder.build();
		politician.setType(Politicians.Type.SENATORIAL);
		int actualHashCode = politician.hashCode();

		Assertions.assertEquals(expectedHashCode, actualHashCode);
	}
	
}