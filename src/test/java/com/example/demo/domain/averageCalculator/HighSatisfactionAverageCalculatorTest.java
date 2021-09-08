package com.example.demo.domain.averageCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HighSatisfactionAverageCalculatorTest extends AbstractAverageCalculatorTest{

	@Test
	public void testLogicOfCalculator() {
		calculator = new HighSatisfactionAverageCalculator(2.279D, 0D);
		assertEquals(calculator.calculateAverage(), 2.27D);
	}
	
}
