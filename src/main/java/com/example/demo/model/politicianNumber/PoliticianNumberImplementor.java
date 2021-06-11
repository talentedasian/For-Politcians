package com.example.demo.model.politicianNumber;

import io.jsonwebtoken.lang.Assert;

public class PoliticianNumberImplementor extends AbstractPoliticianNumber{

	private final String pattern = "FL00-LF00-0000";

	private PoliticianNumberImplementor(String firstName, String lastName, String polNumber) {
		super(firstName, lastName, polNumber);
	}
	
	public static PoliticianNumberImplementor with(String firstName, String lastName, String politicianNumber) {
		if (!(politicianNumber.length() < 12)) {
			Assert.state(politicianNumber.matches("\\d+"),
					"Politician Number must be a digit");
		}
		
		return new PoliticianNumberImplementor(firstName, lastName, politicianNumber);
	}

	@Override
	public PoliticianNumberImplementor calculatePoliticianNumber() {
		String trimmedPattern = convertFAndLOfPatternToNameInitials()
				.substring(0, pattern.length() - politicianNumber.length());
		String finalPoliticianNumber = trimmedPattern.concat(politicianNumber);
		
		return new PoliticianNumberImplementor(getFirstName(), getLastName(), finalPoliticianNumber);
	}
	
	private String convertFAndLOfPatternToNameInitials() {
		String initialPattern = pattern.replace('F', firstName.charAt(0));
		String finalPattern = initialPattern.replace('L', lastName.charAt(0));
		
		return finalPattern;
	}
	
	
	

}
