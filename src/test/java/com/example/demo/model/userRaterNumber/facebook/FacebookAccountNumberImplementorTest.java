package com.example.demo.model.userRaterNumber.facebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.demo.model.userRaterNumber.AbstractUserRaterNumber;

public class FacebookAccountNumberImplementorTest {

	@Test
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethod() {
		final String number = "39812732123";
		
		AbstractUserRaterNumber accountNumberImplementor = FacebookUserRaterNumberImplementor
				.with("Test Name Anything", number);
		
		assertEquals(accountNumberImplementor.calculateEntityNumber().getAccountNumber(), "TNFLM-00039812732123");
	}
	
	@Test
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethodWithSingleWordName() {
		final String number = "39812732123";
		
		AbstractUserRaterNumber accountNumberImplementor = FacebookUserRaterNumberImplementor
				.with("Test", number);
		
		assertEquals(accountNumberImplementor.calculateEntityNumber().getAccountNumber(), "TGFLM-00039812732123");
	}
	
}
