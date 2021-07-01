package com.example.demo.model.userRaterNumber.facebook;

import com.example.demo.model.userRaterNumber.AbstractUserRaterNumber;
import com.example.demo.model.userRaterNumber.LoginMechanism;

public final class FacebookUserRaterNumberImplementor extends AbstractUserRaterNumber{
	
	/*OP stands for the Oauth2 Provider that is used for logging into the application. 
	 * If facebook is used as the login mechanism or OP, OP will be replaced to FLM which 
	 * stands for Facebook Login Mechanism. FL stands for the first two initials of your facebook
	 * name. For example your facebook name is "test name politics", FL will be replaced to 'T' and 'N'.
	 * If your facebook name is only made up of one word, FL will then be replaced with the first letter
	 * of your name appended with "G".
	 */
	private final String pattern = "FLOP-00000000000000";

	public String getPattern() {
		return pattern;
	}
	
	private FacebookUserRaterNumberImplementor(String name, String accountNumber) {
		super(name.split(" ")[0], name.split(" ")[1], accountNumber, LoginMechanism.FACEBOOK);
	}
	
	/*
	 * Since there is no accurate way of knowing the facebook user's first name and last name 
	 * using its user info on facebook, the first two words in its name are considered as its Full Name.
	 */
	public static FacebookUserRaterNumberImplementor with(String name, String accountNumber) {
		String[] nameArray = name.split(" ");
		if (nameArray.length < 2) {
			String firstName = nameArray[0];
			nameArray = new String[2];
			nameArray[0] = firstName;
			nameArray[1] = "Gss"; //Assignment for "G" if name is a single word
		}
		String finalName = nameArray[0].concat(" " + nameArray[1]);
		
		return new FacebookUserRaterNumberImplementor(finalName, accountNumber);
	}

	@Override
	protected FacebookUserRaterNumberImplementor calculateUserAccountNumber() {
		String finalFirstSectionOfPattern = calculateFirstSectionOfPattern();
		
		String finalSecondSectionOfPattern = calculateSecondSectionOfPattern();
		
		String finalAccountNumber = combineSectionsOfPattern(finalFirstSectionOfPattern, "-" + finalSecondSectionOfPattern);
		
		return new FacebookUserRaterNumberImplementor(firstName() + " " + lastName(), finalAccountNumber);
	}
	
	String calculateFirstSectionOfPattern() {
		String firstSectionOfPatternWithName = convertFAndLOfPatternToNameInitials();
		String firstSectionOfPatternWithLoginMechanism = convertOAndPOfPatternToLoginMechanisms();
		
		String finalFirstSectionOfPattern = combineSectionsOfPattern(firstSectionOfPatternWithName, firstSectionOfPatternWithLoginMechanism);
		
		return finalFirstSectionOfPattern.toUpperCase();
	}
	
	String calculateSecondSectionOfPattern() {
		String secondSectionOfPattern = splitPatternIntoSections(pattern)[1];
		
		String trimmedPattern = secondSectionOfPattern.substring(0, secondSectionOfPattern.length() - accountNumber.length());
		
		String finalSecondSectionOfPattern = combineSectionsOfPattern(trimmedPattern, accountNumber);
		
		return finalSecondSectionOfPattern.toUpperCase();
	}
	
	String convertFAndLOfPatternToNameInitials() {
		String[] nameArray = (firstName() + " " + lastName()).split(" ");
		if (nameArray.length < 2) {
			String initialPattern = pattern.replace("F", nameArray[0].toUpperCase());
			String finalPattern = initialPattern.replace("G", "G");
			return finalPattern;
		}
		
		String initialPattern = pattern.replace("F", String.valueOf(nameArray[0].charAt(0)));
		String finalPattern = initialPattern.replace("L", String.valueOf(nameArray[1].charAt(0)));
		
		return splitPatternIntoSections(finalPattern)[0].substring(0,2).toUpperCase();
	}
	
	String convertOAndPOfPatternToLoginMechanisms() {
		String[] firstSectionOfPattern = splitPatternIntoSections(pattern);
		String patternWithoutFAndL = firstSectionOfPattern[0].substring(2);
		String initialPattern = patternWithoutFAndL.replace("O", String.valueOf(getLoginMechanism().toString().charAt(0)));
		String finalPattern = initialPattern.replace("P", "L").concat("M");
		
		return finalPattern;
	}
	
	private String[] splitPatternIntoSections(String section) {
		return section.split("-");
	}
	
	private String combineSectionsOfPattern(String first, String Second) {
		return first.concat(Second);
	}
	
}
