package com.example.demo.unit.averageCalculator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;

public class LowSatisfactionAverageCalculatorTest extends AbstractAverageCalculatorTest{

	@Test
	public void testLogicOfCalculator() {
		calculator = new LowSatisfactionAverageCalculator(2.234D, 0D);
		assertThat(calculator.calculateAverage(),
				equalTo(2.24D));
	}

}
