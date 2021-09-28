package com.example.demo.domain.userRaterNumber.facebook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Tag("Domain")
public class FacebookAccountNumberImplementorTest {
	
	final String ACCOUNT_NUMBER = "TNFLM-00039812732123";
	final String ACCOUNT_NUMBER_SINGLE_WORD = "TGFLM-00039812732123";
	final String NUMBER = "39812732123";
	
	@Test
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethod() {
		var accountNumberImplementor = FacebookAccountNumberCalculator
				.with("Test Name Anything", NUMBER);
		
		Assertions.assertEquals(accountNumberImplementor.calculateEntityNumber().getAccountNumber(), ACCOUNT_NUMBER);
	}
	
	@Test
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethodWithSingleWordName() {
		var accountNumberImplementor = FacebookAccountNumberCalculator
				.with("Test", NUMBER);
		
		Assertions.assertEquals(accountNumberImplementor.calculateEntityNumber().getAccountNumber(), ACCOUNT_NUMBER_SINGLE_WORD);
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "Jacob", "Christian", "Word", "Single" })
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethodWithSingleWordName(String name) {
		var accountNumberImplementor = FacebookAccountNumberCalculator
				.with(name, NUMBER);
		
		Assertions.assertEquals(accountNumberImplementor.calculateEntityNumber().getAccountNumber().substring(1),
				ACCOUNT_NUMBER_SINGLE_WORD.substring(1));
	}
	
}
