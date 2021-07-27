package com.example.demo.unit.politicianNumber;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.demo.model.politicianNumber.PoliticianNumberImplementor;

public class PoliticianNumberImplementorTest {

	final String FIRST_NAME = "firstName";
	final String LAST_NAME = "lastName";
	final String POLITICIAN_NUMBER = "99";
	
	@Test
	public void shouldThrowIllegalStateExceptionWhenCriteriaNotMet() {
		assertThrows(IllegalStateException.class, 
				() -> PoliticianNumberImplementor.with(FIRST_NAME, LAST_NAME, "not number test"));
	}
	
	@Test
	public void shouldCreatePoliticianNumberObjectWithPoliticianNumber() {
		PoliticianNumberImplementor polNumberObject = PoliticianNumberImplementor.with
				("firstName", 
				"lastName", 
				POLITICIAN_NUMBER);
		
		assertEquals(polNumberObject.getPoliticianNumber(), POLITICIAN_NUMBER);
	}
	
	@Test
	public void assertLogicOfPoliticianNumberPatternCreatorMethod() {
		PoliticianNumberImplementor polNumberObject = PoliticianNumberImplementor.with
				("Test", 
				"Politician", 
				POLITICIAN_NUMBER);
		
		assertThat(polNumberObject.calculatePoliticianNumber().getPoliticianNumber(), 
				containsString("T"));
		assertThat(polNumberObject.calculatePoliticianNumber().getPoliticianNumber(), 
				containsString("P"));
		assertThat(polNumberObject.calculatePoliticianNumber().getPoliticianNumber(), 
				containsString(POLITICIAN_NUMBER));
	}
	
}
