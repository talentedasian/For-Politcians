package com.example.demo.unit.accountNumber;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.model.userRaterNumber.AbstractUserRaterNumber;
import com.example.demo.model.userRaterNumber.FacebookUserRaterNumberImplementor;

public class FacebookAccountNumberImplementorTest {

	@Test
	public void assertBehaviourOfFacebookAccountNumberPatternCreatorMethod() {
		final String number = "39812732123";
		
		AbstractUserRaterNumber accountNumberImplementor = new FacebookUserRaterNumberImplementor("Test Name Anything", number);
		
		assertThat(accountNumberImplementor.calculateUserRaterAccountNumber().returnAccountNumber(), 
				equalTo("TNFLM-00039812732123"));
	}	
}
