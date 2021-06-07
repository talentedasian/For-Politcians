package com.example.demo.model.politicianNumber;

import com.example.demo.model.AbstractPoliticianNumber;

import io.jsonwebtoken.lang.Assert;

public class PoliticianNumberImplementor extends AbstractPoliticianNumber{

	private final String pattern = "PN00-PK00-0000";
	private final String polNumber;
	
	public String getPattern() {
		return pattern;
	}

	public String getPolNumber() {
		return polNumber;
	}

	public PoliticianNumberImplementor(String firstName, String lastName, String polNumber) {
		super(firstName, lastName);
		
		Assert.state(polNumber.matches("\\d+"), 
				"Number should be a valid digit");
		this.polNumber = polNumber;
	}

	@Override
	public PoliticianNumberImplementor calculatePoliticianNumber(int sequenceNumbers) {
		String politicianNumber = pattern.substring(0, lastString(pattern));
		
		return new PoliticianNumberImplementor(getFirstName(), getLastName(), politicianNumber);
	}
	
	private int lastString(String string) { 
		return string.length() - 1;
	}
	
	

}
