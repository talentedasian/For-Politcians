package com.example.demo.domain.averageCalculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecentSatisfactionAverageCalculatorTest extends AbstractAverageCalculatorTest{

	@Test
	public void testLogicOfCalculator() {
		calculator = new DecentSatisfactionAverageCalculator(4.275D, 1D);
		assertEquals(calculator.calculateAverage(), 4.28D);
	}

}
