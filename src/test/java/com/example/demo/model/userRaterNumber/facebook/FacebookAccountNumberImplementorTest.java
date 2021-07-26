package com.example.demo.model.userRaterNumber.facebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.demo.model.userRaterNumber.AbstractUserRaterNumber;

public class FacebookAccountNumberImplementorTest {
	
	final String ACCOUNT_NUMBER = "TNFLM-00039812732123";
	final String ACCOUNT_NUMBER_SINGLE_WORD = "TGFLM-00039812732123";

	@Test
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethod() {
		final String number = "39812732123";
		
		AbstractUserRaterNumber accountNumberImplementor = FacebookUserRaterNumberImplementor
				.with("Test Name Anything", number);
		
		assertEquals(accountNumberImplementor.calculateEntityNumber().getAccountNumber(), ACCOUNT_NUMBER);
	}
	
	@Test
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethodWithSingleWordName() {
		final String number = "39812732123";
		
		AbstractUserRaterNumber accountNumberImplementor = FacebookUserRaterNumberImplementor
				.with("Test", number);
		
		assertEquals(accountNumberImplementor.calculateEntityNumber().getAccountNumber(), ACCOUNT_NUMBER_SINGLE_WORD);
	}
	
}
