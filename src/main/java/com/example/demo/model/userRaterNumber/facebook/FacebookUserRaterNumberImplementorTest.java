package com.example.demo.model.userRaterNumber.facebook;

import static com.example.demo.model.userRaterNumber.facebook.FacebookUserRaterNumberImplementor.with;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FacebookUserRaterNumberImplementorTest {
	
	FacebookUserRaterNumberImplementor calculator = with("random test", "123321");
	

	@Test
	public void assertLogicOfCalculatingFirstSectionOfPattern() {
		String firstSectionToBeTested = calculator.calculateFirstSectionOfPattern();
		String actualFirstSection = "RTFLM";
		
		assertEquals(firstSectionToBeTested, actualFirstSection);
	}
	
	@Test
	public void assertLogicOfCalculatingSecondSectionOfPattern() {
		String secondSectionToBeTested = calculator.calculateSecondSectionOfPattern();
		String actualSecondSection = "00000000123321";
		
		assertEquals(secondSectionToBeTested, actualSecondSection);
	}
	
	@Test
	public void assertLogicOfCalculatingNameInitialsOfFirstSectionOfPattern() {
		String initialsToBeTested = calculator.convertFAndLOfPatternToNameInitials();
		String actualInitials= "RT";
		
		assertEquals(initialsToBeTested, actualInitials);
	}
	
	@Test
	public void assertLogicOfCalculatingOauth2InitialsOfFirstSectionOfPattern() {
		String initialsToBeTested = calculator.convertOAndPOfPatternToLoginMechanisms();
		String actualInitials= "FLM";
		
		assertEquals(initialsToBeTested, actualInitials);
	}
	
}