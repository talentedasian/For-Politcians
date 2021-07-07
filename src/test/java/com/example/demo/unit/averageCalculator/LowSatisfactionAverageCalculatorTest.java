package com.example.demo.unit.averageCalculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;

public class LowSatisfactionAverageCalculatorTest extends AbstractAverageCalculatorTest{

	@Test
	public void testLogicOfCalculator() {
		calculator = new LowSatisfactionAverageCalculator(2.234D, 0D);
		assertEquals(calculator.calculateAverage(), 2.24D);
	}

}
