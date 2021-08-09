package com.example.demo.unit.averageCalculator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.demo.domain.averageCalculator.AverageCalculator;
import com.example.demo.domain.averageCalculator.LowSatisfactionAverageCalculator;

public class AbstractAverageCalculatorTest {

	public AverageCalculator calculator;
	
	@Test
	public void shouldReturnDouble() {
		calculator = new LowSatisfactionAverageCalculator(0.0D, 0.0D);
		assertThat(Double.valueOf(calculator.calculateAverage()).getClass(),
				is(Double.class));
	}
	
	@ParameterizedTest
	@ValueSource(doubles = { -0.22 , -1, -100, -0D })
	public void shouldThrowIllegalStateException(double totalRating) {
		assertThrows(IllegalStateException.class,
				() -> new LowSatisfactionAverageCalculator(totalRating, 0.0D));
	}
	
	@ParameterizedTest
	@ValueSource(doubles = { 0 , -1, -100, -0D })
	public void countShouldBeZero(double count) {
		calculator = new LowSatisfactionAverageCalculator(0.0D, count);
		assertEquals(0, calculator.getCount());
	}
	
}
