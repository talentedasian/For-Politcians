package com.example.demo.model;

public abstract class AbstractPoliticianNumber implements PoliticianNumberInterface{

	private final String firstName, lastName;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}


	public AbstractPoliticianNumber(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

}
