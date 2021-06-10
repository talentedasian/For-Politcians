package com.example.demo.model.userRaterNumber;

import com.example.demo.model.UserRaterNumberInterface;

public class FacebookUserRaterNumberImplementor extends AbstractUserRaterNumber{
	
	/*OP stands for the Oauth2 Provider that is used for logging into the application. 
	 * If facebook is used as the login mechanism or OP, OP will be replaced to FLM which 
	 * stands for Facebook Login Mechanism. FL stands for the first two initials of your facebook
	 * name. For example your facebook name is "test name politics", FL will be replaced to 'T' and 'N'.
	 * If your facebook name is only made up of one word, FL will then be replaced with the first letter
	 * of your name appended with "L".
	 */
	private final String pattern = "FLOP-00000000000000";

	private final String accountNumber;

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getPattern() {
		return pattern;
	}

	public FacebookUserRaterNumberImplementor(String name, String numberPattern) {
		super(name, LoginMechanism.FACEBOOK);
		this.accountNumber = numberPattern;
		
	}
	
	@Override
	public FacebookUserRaterNumberImplementor calculateUserRaterAccountNumber() {
		String firstSectionOfPatternWithName = convertFAndLOfPatternToNameInitials();
		String firstSectionOfPatternWithLoginMechanism = convertOAndPOfPatternToLoginMechanisms();
		String finalFirstSectionOfPattern = combineFirstSectionOfPattern(firstSectionOfPatternWithName, firstSectionOfPatternWithLoginMechanism);
		
		String secondSectionOfPattern = pattern.split("-")[1];
		String trimmedPattern = secondSectionOfPattern.substring(0, secondSectionOfPattern.length() - accountNumber.length());
		
		String finalSecondSectionOfPattern = trimmedPattern.concat(accountNumber);
		
		String finalAccountNumber = finalFirstSectionOfPattern.concat("-" + finalSecondSectionOfPattern);
		
		return new FacebookUserRaterNumberImplementor(name, finalAccountNumber);
	}
	
	private String convertFAndLOfPatternToNameInitials() {
		String[] nameArray = name.split(" ");
		if (nameArray.length < 2) {
			String finalPattern = pattern.replace("F", nameArray[0]);
			return finalPattern;
		}
		
		String initialPattern = pattern.replace("F", String.valueOf(nameArray[0].charAt(0)));
		String finalPattern = initialPattern.replace("L", String.valueOf(nameArray[1].charAt(0)));
		
		return finalPattern.split("-")[0].substring(0,2);
	}
	
	private String convertOAndPOfPatternToLoginMechanisms() {
		String[] firstSectionOfPattern = pattern.split("-");
		String patternWithoutFAndL = firstSectionOfPattern[0].substring(2);
		String initialPattern = patternWithoutFAndL.replace("O", String.valueOf(loginMechanism.toString().charAt(0)));
		String finalPattern = initialPattern.replace("P", "L").concat("M");
		
		return finalPattern;
	}
	
	private String combineFirstSectionOfPattern(String nameSection, String oauth2ProviderSection) {
		return nameSection.concat(oauth2ProviderSection);
	}
	
	
	
}
