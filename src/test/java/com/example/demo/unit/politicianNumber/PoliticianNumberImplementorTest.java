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
				() -> new PoliticianNumberImplementor("firstName", "lastName", "not num"));
	}
	
	@Test
	public void shouldCreatePoliticianNumberObject() {
		PoliticianNumberImplementor polNumberObject = new PoliticianNumberImplementor
				("firstName", 
				"lastName", 
				"99");
		
		assertEquals(polNumberObject.getPolNumber(), "99");
	}
	
	@Test
	public void assertBehaviourOfPoliticianNumberPatternCreatorMethod() {
		PoliticianNumberImplementor polNumberObject = new PoliticianNumberImplementor
				("Test", 
				"Politician", 
				"99");
		
		assertThat(polNumberObject.calculatePoliticianNumber().getPolNumber(), 
				containsString("T"));
		assertThat(polNumberObject.calculatePoliticianNumber().getPolNumber(), 
				containsString("P"));
		assertThat(polNumberObject.calculatePoliticianNumber().getPolNumber(), 
				containsString("99"));
	}
	
}
