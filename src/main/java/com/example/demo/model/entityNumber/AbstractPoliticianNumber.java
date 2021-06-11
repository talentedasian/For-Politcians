package com.example.demo.model.entityNumber;

public abstract class AbstractPoliticianNumber implements NumberInterface {

	protected final String firstName, lastName, politicianNumber;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPoliticianNumber() {
		return politicianNumber;
	}

	public AbstractPoliticianNumber(String firstName, String lastName, String politicianNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.politicianNumber = politicianNumber;
	}

	@Override
	public String toString() {
		return "AbstractPoliticianNumber [firstName=" + firstName + ", lastName=" + lastName + ", politicianNumber="
				+ politicianNumber + "]";
	}

	@Override
	public AbstractPoliticianNumber calculateEntityNumber() {
		return calculatePoliticianNumber();
	}
	
	public abstract AbstractPoliticianNumber calculatePoliticianNumber();

}
