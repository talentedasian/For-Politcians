package com.example.demo.domain.domainModel;

import com.example.demo.domain.politicians.PoliticianNumber;
import com.example.demo.domain.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicians.Politicians.PoliticiansBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.example.demo.baseClasses.NumberTestFactory.POL_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PoliticiansTest {

	final PoliticianNumber POLITICIAN_NUMBER = POL_NUMBER();
	
	PoliticiansBuilder politicianBuilder = new PoliticiansBuilder(POLITICIAN_NUMBER)
			.setFirstName("Test")
			.setLastName("Name");

	@Test
	public void testNullLastNameInBuilder() {
		var politicianWithNoLastName = new PoliticiansBuilder(POLITICIAN_NUMBER)
				.setFirstName("Test")
				.setFullName()
				.build();

		assertEquals("Test", politicianWithNoLastName.fullName());
	}

	@Test
	public void testFullNameInBuilderWithNoFirstName() throws Exception{
		var politicianWithNullFirstName = new PoliticiansBuilder(POLITICIAN_NUMBER)
				.setLastName("Name")
				.build();

		assertThrows(IllegalStateException.class, () -> politicianWithNullFirstName.fullName());
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
		var senatorial = politician.setType(Politicians.Type.SENATORIAL);
		int actualHashCode = politician.hashCode();

		Assertions.assertEquals(expectedHashCode, actualHashCode);
	}

}