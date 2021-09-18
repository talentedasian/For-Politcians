package com.example.demo.domain.politicianNumber;

import com.example.demo.domain.entities.Name;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.Politicians;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.demo.domain.entities.Politicians.Type.PRESIDENTIAL;
import static com.example.demo.domain.entities.Politicians.Type.SENATORIAL;
import static org.assertj.core.api.Assertions.assertThat;

public class PoliticianNumberCalculatorTest {

	final String FIRST_NAME = "firstName";
	final String LAST_NAME = "lastName";

	PoliticianNumberCalculator polNumberCalculator;

	final Name name = new Name(FIRST_NAME, LAST_NAME);

	@BeforeEach
	public void setup() {
		polNumberCalculator = null;
	}

	@Test
	public void shouldCreatePoliticianNumberConformingToPoliticianNumberConstraintsByPresidential() {
		polNumberCalculator = PoliticianNumberCalculatorFactory.politicianCalculator(PRESIDENTIAL);
		String EXPECTED_POLITICIAN_NUMBER = "FLPP-LFPP-".concat(recreateMethodForPoliticianNumber(new Name(FIRST_NAME, LAST_NAME), PRESIDENTIAL));

		assertThat(polNumberCalculator.calculatePoliticianNumber(name))
				.isEqualTo(new PoliticianNumber(EXPECTED_POLITICIAN_NUMBER));
	}

	@Test
	public void shouldCreatePoliticianNumberConformingToPoliticianNumberConstraintsBySenatorial() {
		polNumberCalculator = PoliticianNumberCalculatorFactory.politicianCalculator(SENATORIAL);
		String EXPECTED_POLITICIAN_NUMBER = "FLSS-LFSS-".concat(recreateMethodForPoliticianNumber(new Name(FIRST_NAME, LAST_NAME), SENATORIAL));

		assertThat(polNumberCalculator.calculatePoliticianNumber(name))
				.isEqualTo(new PoliticianNumber(EXPECTED_POLITICIAN_NUMBER));
	}

	private String recreateMethodForPoliticianNumber(Name name, Politicians.Type type) {
		int result = name.firstName().hashCode();
		result = 31 * result + name.lastName() == null ? "lastname".hashCode() : name.lastName().hashCode();
		result = 31 * result + name.fullName().hashCode();
		result = 31 * result + type.toString().hashCode();
		return String.valueOf(Math.abs(result)).substring(0, 4);
	}

}
