package com.example.demo.model.userRaterNumber.facebook;

import static com.example.demo.model.userRaterNumber.facebook.FacebookUserRaterNumberImplementor.with;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;

public class FacebookUserRaterNumberImplementorTest {
	
	FacebookUserRaterNumberImplementor calculator = with("random test", "123321");
	FacebookUserRaterNumberImplementor calculatorWithSingleWordSingleName = with("random", "123321");
	
	@Test
	@ExcludeFromJacocoGeneratedCoverage
	public void assertLogicOfCalculatingFirstSectionOfPattern() {
		String firstSectionToBeTested = calculator.calculateFirstSectionOfPattern();
		String actualFirstSection = "RTFLM";
		
		assertEquals(firstSectionToBeTested, actualFirstSection);
	}
	
	@Test
	@ExcludeFromJacocoGeneratedCoverage
	public void assertLogicOfCalculatingFirstSectionOfPatternWithSingleWordName() {
		String firstSectionToBeTested = calculatorWithSingleWordSingleName.calculateFirstSectionOfPattern();
		String actualFirstSection = "RGFLM";
		
		assertEquals(firstSectionToBeTested, actualFirstSection);
	}
	
	@Test
	@ExcludeFromJacocoGeneratedCoverage
	public void assertLogicOfCalculatingSecondSectionOfPattern() {
		String secondSectionToBeTested = calculator.calculateSecondSectionOfPattern();
		String actualSecondSection = "00000000123321";
		
		assertEquals(secondSectionToBeTested, actualSecondSection);
	}
	
	@Test
	@ExcludeFromJacocoGeneratedCoverage
	public void assertLogicOfCalculatingNameInitialsOfFirstSectionOfPattern() {
		String initialsToBeTested = calculator.convertFAndLOfPatternToNameInitials();
		String actualInitials= "RT";
		
		assertEquals(initialsToBeTested, actualInitials);
	}

	@Test
	@ExcludeFromJacocoGeneratedCoverage
	public void assertLogicOfCalculatingNameInitialsOfFirstSectionOfPatternWithSingleWordName() {
		String initialsToBeTested = calculatorWithSingleWordSingleName.convertFAndLOfPatternToNameInitials();
		String actualInitials= "RG";
		
		assertEquals(initialsToBeTested, actualInitials);
	}
	
	@Test
	@ExcludeFromJacocoGeneratedCoverage
	public void assertLogicOfCalculatingOauth2InitialsOfFirstSectionOfPattern() {
		String initialsToBeTested = calculator.convertOAndPOfPatternToLoginMechanisms();
		String actualInitials= "FLM";
		
		assertEquals(initialsToBeTested, actualInitials);
	}	
	
}
