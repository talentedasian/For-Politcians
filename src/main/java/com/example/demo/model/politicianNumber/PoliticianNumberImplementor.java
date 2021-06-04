package com.example.demo.model.politicianNumber;

import com.example.demo.model.AbstractPoliticianNumber;

public class PoliticianNumberImplementor extends AbstractPoliticianNumber{

	private final String pattern = "PN00-PK00-0000";
	private final String polNumber;
	
	public PoliticianNumberImplementor(String firstName, String lastName, String fullName, String polNumber) {
		super(firstName, lastName, fullName);
		this.polNumber = polNumber;
	}

	@Override
	public PoliticianNumberImplementor calculatePoliticianNumber(int sequenceNumber) {
		String politicianNumber = pattern.substring(0, lastString(pattern));
		
		return new PoliticianNumberImplementor(getFirstName(), getLastName(), getFullName(), politicianNumber);
	}
	
	private int lastString(String string) { 
		return string.length() - 1;
	}
	
	

}
