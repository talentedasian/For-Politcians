package com.example.demo.domain.averageCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LowSatisfactionAverageCalculatorTest extends AbstractAverageCalculatorTest{

	@Test
	public void testLogicOfCalculator() {
		calculator = new LowSatisfactionAverageCalculator(2.234D, 0D);
		assertEquals(calculator.calculateAverage(), 2.24D);
	}

}
