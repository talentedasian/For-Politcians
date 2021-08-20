package com.example.demo.unit.politicianNumber;

import com.example.demo.domain.politicianNumber.PoliticianNumberImplementor;
import com.example.demo.domain.politicians.PoliticianNumber;
import com.example.demo.domain.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.politicians.Politicians;
import org.junit.jupiter.api.Test;

import static com.example.demo.domain.politicianNumber.PoliticianNumberImplementor.with;
import static org.assertj.core.api.Assertions.assertThat;

public class PoliticianNumberImplementorTest {

	final String FIRST_NAME = "firstName";
	final String LAST_NAME = "lastName";

	Politicians politicianBuilder = new Politicians.PoliticiansBuilder()
			.setFirstName(FIRST_NAME)
			.setLastName(LAST_NAME)
			.setFullName()
			.build();

	@Test
	public void assertLogicOfPoliticianNumberPatternCreatorMethodWithPresidential() {
		var presidential = new PresidentialBuilder(politicianBuilder)
				.build();

		Politicians.Type POLITICIAN_TYPE = presidential.getType();
		final String EXPECTED_POLITICIAN_NUMBER = new PoliticianNumber(with(politicianBuilder.recordName(), POLITICIAN_TYPE).calculateEntityNumber().getPoliticianNumber()).politicianNumber();

		PoliticianNumberImplementor polNumber = with(presidential.recordName(), POLITICIAN_TYPE);

		assertThat(EXPECTED_POLITICIAN_NUMBER)
				.isEqualTo(polNumber.calculateEntityNumber().getPoliticianNumber());
	}
	
	@Test
	public void assertLogicOfPoliticianNumberPatternCreatorMethodWithSenatorial() {
		var senatorial = new SenatorialBuilder(politicianBuilder)
				.setTotalMonthsOfService(12)
				.build();

		Politicians.Type POLITICIAN_TYPE = senatorial.getType();

		final String EXPECTED_POLITICIAN_NUMBER = new PoliticianNumber(with(politicianBuilder.recordName(), POLITICIAN_TYPE).calculateEntityNumber().getPoliticianNumber()).politicianNumber();

		PoliticianNumberImplementor polNumber = with(senatorial.recordName(), POLITICIAN_TYPE);

		assertThat(EXPECTED_POLITICIAN_NUMBER)
				.isEqualTo(polNumber.calculateEntityNumber().getPoliticianNumber());
	}
	
}
