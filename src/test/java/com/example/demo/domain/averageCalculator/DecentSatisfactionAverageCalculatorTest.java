package com.example.demo.domain.averageCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DecentSatisfactionAverageCalculatorTest extends AbstractAverageCalculatorTest{

	@Test
	public void testLogicOfCalculator() {
		calculator = new DecentSatisfactionAverageCalculator(2.275D, 0D);
		assertEquals(calculator.calculateAverage(), 2.28D);
	}
}
