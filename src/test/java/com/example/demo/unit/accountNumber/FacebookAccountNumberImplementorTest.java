package com.example.demo.unit.accountNumber;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.model.userRaterNumber.AbstractUserRaterNumber;
import com.example.demo.model.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;

public class FacebookAccountNumberImplementorTest {

	@Test
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethod() {
		final String number = "39812732123";
		
		AbstractUserRaterNumber accountNumberImplementor = FacebookUserRaterNumberImplementor
				.with("Test Name Anything", number);
		
		assertThat(accountNumberImplementor.calculateEntityNumber().getAccountNumber(), 
				equalTo("TNFLM-00039812732123"));
	}
	
	@Test
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethodWithOneWordName() {
		final String number = "39812732123";
		
		AbstractUserRaterNumber accountNumberImplementor = FacebookUserRaterNumberImplementor
				.with("Test", number);
		
		assertThat(accountNumberImplementor.calculateEntityNumber().getAccountNumber(), 
				equalTo("TGFLM-00039812732123"));
	}
	
}
