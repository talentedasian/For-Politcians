package com.example.demo.domain.politicianNumber;

import com.example.demo.domain.politicians.Politicians;

public class PoliticianNumberImplementor extends AbstractPoliticianNumber{

	/*
		F stands for Firstname
		L stands for Lastname
		T stands for what type the politician is e.g. Presidential
		The leading zeroes are the last 4 numbers of the hashcode of the politician
	 */
	final String pattern = "FLTT-LFTT-0000";

	final char FIRSTNAME_INITIAL = 'F';
	final char LASTNAME_INITIAL = 'L';
	final char TYPE_INITIAL = 'T';

	private final Politicians politician;

	Politicians getPolitician() {
		return this.politician;
	}

	protected PoliticianNumberImplementor(Politicians politician) {
		super(politician.getFirstName(), politician.getLastName(), politician.getPoliticianNumber());
		this.politician = politician;
	}

	public static PoliticianNumberImplementor with(Politicians politician) {
//		if (!(politician.getPoliticianNumber().length() < 12)) {
//			Assert.state(politician.getPoliticianNumber().matches("\\d+"),
//					"Politician Number must be a digit");
//		}
		
		return new PoliticianNumberImplementor(politician);
	}

	@Override
	PoliticianNumberImplementor calculatePoliticianNumber() {
		org.springframework.util.Assert.state(politician.getType() != null,
				"politician  must be a subclass of class \"Politicians\"");

		switch (politician.getType()) {
			case PRESIDENTIAL -> {return new PresidentialNumberImplementor(politician).calculatePoliticianNumber();}
			case SENATORIAL -> {return new SenatorialNumberImplementor(politician).calculatePoliticianNumber();}
			default -> throw new IllegalStateException("type is non existent");
		}
	}

}
