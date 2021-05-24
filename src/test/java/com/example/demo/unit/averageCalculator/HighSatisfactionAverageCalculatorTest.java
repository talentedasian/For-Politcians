package com.example.demo.unit.averageCalculator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.model.averageCalculator.HighSatisfactionAverageCalculator;

public class HighSatisfactionAverageCalculatorTest extends AbstractAverageCalculatorTest{

	@Test
	public void testLogicOfCalculator() {
		calculator = new HighSatisfactionAverageCalculator(2.275D, 0D);
		assertThat(calculator.calculateAverage(),
				equalTo(2.27D));
	}
	
}
