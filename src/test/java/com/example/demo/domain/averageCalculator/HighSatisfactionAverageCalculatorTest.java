package com.example.demo.domain.averageCalculator;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Domain")
public class HighSatisfactionAverageCalculatorTest extends AbstractAverageCalculatorTest{

	@Test
	public void testLogicOfCalculator() {
		calculator = new HighSatisfactionAverageCalculator(2.279D, 1D);
		assertEquals(calculator.calculateAverage(), 2.27D);
	}
	
}
