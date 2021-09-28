package com.example.demo.domain.averageCalculator;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Domain")
public class LowSatisfactionAverageCalculatorTest extends AbstractAverageCalculatorTest{

	@Test
	public void testLogicOfCalculator() {
		calculator = new LowSatisfactionAverageCalculator(2.2342D, 1D);
		assertEquals(calculator.calculateAverage(), 2.235D);
	}

}
