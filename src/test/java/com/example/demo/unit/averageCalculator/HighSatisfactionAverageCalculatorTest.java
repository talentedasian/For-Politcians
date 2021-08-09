package com.example.demo.unit.averageCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.demo.domain.averageCalculator.HighSatisfactionAverageCalculator;

public class HighSatisfactionAverageCalculatorTest extends AbstractAverageCalculatorTest{

	@Test
	public void testLogicOfCalculator() {
		calculator = new HighSatisfactionAverageCalculator(2.279D, 0D);
		assertEquals(calculator.calculateAverage(), 2.27D);
	}
	
}
