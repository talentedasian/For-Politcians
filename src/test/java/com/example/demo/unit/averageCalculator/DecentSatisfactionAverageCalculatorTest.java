package com.example.demo.unit.averageCalculator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.model.averageCalculator.DecentSatisfactionAverageCalculator;

public class DecentSatisfactionAverageCalculatorTest extends AbstractAverageCalculatorTest{

	@Test
	public void testLogicOfCalculator() {
		calculator = new DecentSatisfactionAverageCalculator(2.275D, 0D);
		assertThat(calculator.calculateAverage(),
				equalTo(2.28D));
	}
}
