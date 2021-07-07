package com.example.demo.unit.averageCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.demo.model.averageCalculator.DecentSatisfactionAverageCalculator;

public class DecentSatisfactionAverageCalculatorTest extends AbstractAverageCalculatorTest{

	@Test
	public void testLogicOfCalculator() {
		calculator = new DecentSatisfactionAverageCalculator(2.275D, 0D);
		assertEquals(calculator.calculateAverage(), 2.28D);
	}
}
