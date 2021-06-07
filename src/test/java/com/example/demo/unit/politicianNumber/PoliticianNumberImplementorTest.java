package com.example.demo.unit.politicianNumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.demo.model.politicianNumber.PoliticianNumberImplementor;

public class PoliticianNumberImplementorTest {

	@Test
	public void shouldThrowIllegalStateException() {
		assertThrows(IllegalStateException.class, 
				() -> new PoliticianNumberImplementor("firstName", "lastName", "not a number"));
	}
	
	@Test
	public void shouldCreatePoliticianNumberObject() {
		PoliticianNumberImplementor polNumberObject = new PoliticianNumberImplementor
				("firstName", 
				"lastName", 
				"00");
		
		assertEquals(polNumberObject.getPolNumber(), "00");
	}
	
}
