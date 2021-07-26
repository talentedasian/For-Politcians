package com.example.demo.model.userRaterNumber.facebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FacebookAccountNumberImplementorTest {
	
	final String ACCOUNT_NUMBER = "TNFLM-00039812732123";
	final String ACCOUNT_NUMBER_SINGLE_WORD = "TGFLM-00039812732123";
	final String NUMBER = "39812732123";
	
	@Test
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethod() {
		var accountNumberImplementor = FacebookUserRaterNumberImplementor
				.with("Test Name Anything", NUMBER);
		
		assertEquals(accountNumberImplementor.calculateEntityNumber().getAccountNumber(), ACCOUNT_NUMBER);
	}
	
	@Test
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethodWithSingleWordName() {
		var accountNumberImplementor = FacebookUserRaterNumberImplementor
				.with("Test", NUMBER);
		
		assertEquals(accountNumberImplementor.calculateEntityNumber().getAccountNumber(), ACCOUNT_NUMBER_SINGLE_WORD);
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "Jacob", "Christian", "Word", "Single" })
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethodWithSingleWordName(String name) {
		var accountNumberImplementor = FacebookUserRaterNumberImplementor
				.with(name, NUMBER);
		
		assertEquals(accountNumberImplementor.calculateEntityNumber().getAccountNumber().substring(1),
				ACCOUNT_NUMBER_SINGLE_WORD.substring(1));
	}
	
}
