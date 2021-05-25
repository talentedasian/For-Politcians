package com.example.demo.model;

public abstract class AbstractPoliticianNumber implements PoliticianNumberInterface{

	private final String firstName, lastName, fullName;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public AbstractPoliticianNumber(String firstName, String lastName, String fullName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
	}

	@Override
	public String calculatePoliticianNumber() {
		// TODO Auto-generated method stub
		return null;
	}

}
