package com.example.demo.model.politicianNumber;

import com.example.demo.model.AbstractPoliticianNumber;

import io.jsonwebtoken.lang.Assert;

public class PoliticianNumberImplementor extends AbstractPoliticianNumber{

	private final String pattern = "FL00-LF00-0000";
	private final String polNumber;
	
	public String getPattern() {
		return pattern;
	}

	public String getPolNumber() {
		return polNumber;
	}

	public PoliticianNumberImplementor(String firstName, String lastName, String polNumber) {
		super(firstName, lastName);
		if (polNumber.length() > 8) {
			this.polNumber = polNumber;
			return;
		}
		
		Assert.state(polNumber.matches("\\d+"), 
				"Number should be a valid digit");
		this.polNumber = polNumber;
	}

	@Override
	public PoliticianNumberImplementor calculatePoliticianNumber() {
		String trimmedPattern = convertFAndLOfPatternToNameInitials()
				.substring(0, pattern.length() - polNumber.length());
		String finalPoliticianNumber = trimmedPattern.concat(polNumber);
		
		return new PoliticianNumberImplementor(getFirstName(), getLastName(), finalPoliticianNumber);
	}
	
	private String convertFAndLOfPatternToNameInitials() {
		String initialPattern = pattern.replace('F', firstName.charAt(0));
		String finalPattern = initialPattern.replace('L', lastName.charAt(0));
		
		return finalPattern;
	}
	
	@Override
	public String toString() {
		return "PoliticianNumberImplementor [pattern=" + pattern + ", polNumber=" + polNumber + "]";
	}

}
