package com.example.demo.unit.politicianNumber;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.demo.model.politicianNumber.PoliticianNumberImplementor;

public class PoliticianNumberImplementorTest {

	@Test
	public void shouldThrowIllegalStateException() {
		assertThrows(IllegalStateException.class, 
				() -> PoliticianNumberImplementor.with("firstName", "lastName", "not number test"));
	}
	
	@Test
	public void shouldCreatePoliticianNumberObject() {
		PoliticianNumberImplementor polNumberObject = PoliticianNumberImplementor.with
				("firstName", 
				"lastName", 
				"99");
		
		assertEquals(polNumberObject.getPoliticianNumber(), "99");
	}
	
	@Test
	public void assertBehaviourOfPoliticianNumberPatternCreatorMethod() {
		PoliticianNumberImplementor polNumberObject = PoliticianNumberImplementor.with
				("Test", 
				"Politician", 
				"99");
		
		assertThat(polNumberObject.calculatePoliticianNumber().getPoliticianNumber(), 
				containsString("T"));
		assertThat(polNumberObject.calculatePoliticianNumber().getPoliticianNumber(), 
				containsString("P"));
		assertThat(polNumberObject.calculatePoliticianNumber().getPoliticianNumber(), 
				containsString("99"));
	}
	
}
