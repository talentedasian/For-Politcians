package com.example.demo.unit.politicianNumber;

import com.example.demo.domain.politicianNumber.SenatorialNumberImplementor;
import com.example.demo.domain.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicianNumber.PoliticianNumberImplementor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PoliticianNumberImplementorTest {

	final String FIRST_NAME = "firstName";
	final String LAST_NAME = "lastName";
	final String POLITICIAN_NUMBER = "99";

	Politicians politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
			.setFirstName(FIRST_NAME)
			.setLastName(LAST_NAME)
			.setFullName()
			.build();
	@Test
	public void assertLogicOfPoliticianNumberPatternCreatorMethodWithPresidential() {
		var presidential = new PresidentialBuilder(politicianBuilder)
				.build();

		PoliticianNumberImplementor polNumber = PoliticianNumberImplementor.with(presidential.recordName(), Politicians.Type.PRESIDENTIAL);
		
		assertEquals("FLPP-LFPP-".concat(String.valueOf(presidential.hashCode()).substring(0, 4)),
				polNumber.calculateEntityNumber().getPoliticianNumber());
	}
	
	@Test
	public void assertLogicOfPoliticianNumberPatternCreatorMethodWithSenatorial() {
		var senatorial = new SenatorialBuilder(politicianBuilder)
				.setTotalMonthsOfService(12)
				.build();

		PoliticianNumberImplementor polNumber = SenatorialNumberImplementor.with(senatorial.recordName(), Politicians.Type.SENATORIAL);

		assertEquals("FLSS-LFSS-".concat(String.valueOf(senatorial.hashCode()).substring(0, 4)),
				polNumber.calculateEntityNumber().getPoliticianNumber());
	}
	
}
