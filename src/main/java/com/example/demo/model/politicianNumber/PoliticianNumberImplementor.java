package com.example.demo.model.politicianNumber;

import com.example.demo.model.AbstractPoliticianNumber;

public class PoliticianNumberImplementor extends AbstractPoliticianNumber{

	private final String pattern = "1N00-PK00-0000";
	
	public PoliticianNumberImplementor(String firstName, String lastName, String fullName) {
		super(firstName, lastName, fullName);
	}

	@Override
	public String calculatePoliticianNumber() {
		String politicianNumber = super.getFirstName().substring(0, 1) + "N";
				
		return super.calculatePoliticianNumber();
	}
	
	

}
