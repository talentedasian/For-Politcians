package com.example.demo.unit.averageCalculator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;

public class AbstractAverageCalculatorTest {

	public AverageCalculator calculator;
	
	@Test
	public void shouldReturnDouble() {
		calculator = new LowSatisfactionAverageCalculator(0.0D, 0.0D);
		assertThat(Double.valueOf(calculator.calculateAverage()).getClass(),
				is(Double.class));
	}
	
}
