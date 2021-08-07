package com.example.demo.unit.politicianNumber;

import com.example.demo.model.entities.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.politicianNumber.PoliticianNumberImplementor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PoliticianNumberImplementorTest {

	final String FIRST_NAME = "firstName";
	final String LAST_NAME = "lastName";
	final String POLITICIAN_NUMBER = "99";

	Politicians.PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
			.setFirstName(FIRST_NAME)
			.setLastName(LAST_NAME)
			.setFullName();

	PoliticianNumberImplementor politicianNumberCalculator = PoliticianNumberImplementor.with(politicianBuilder.build());

//	@Test
//	public void shouldThrowIllegalStateExceptionWhenCriteriaNotMet() {
//		assertThrows(IllegalStateException.class,
//				() -> PoliticianNumberImplementor.with(FIRST_NAME, LAST_NAME, "not number test"));
//	}
	
	@Test
	public void shouldCreatePoliticianNumberObjectWithPoliticianNumber() {
		var senatorial = new SenatorialBuilder(politicianBuilder)
				.setTotalMonthsOfService(12)
				.build();

		PoliticianNumberImplementor polNumberObject = PoliticianNumberImplementor.with(senatorial);
		
		assertEquals(polNumberObject.getPoliticianNumber(), POLITICIAN_NUMBER);
	}
	
	@Test
	public void assertLogicOfPoliticianNumberPatternCreatorMethodWithSenatorial() {
		var senatorial = new SenatorialBuilder(politicianBuilder)
				.setTotalMonthsOfService(12)
				.build();

		PoliticianNumberImplementor polNumberObject = PoliticianNumberImplementor.with(senatorial);

		assertEquals(polNumberObject.calculateEntityNumber().getPoliticianNumber(), POLITICIAN_NUMBER);
	}
	
}
