package com.example.demo.domain.politicianNumber;

import com.example.demo.domain.entityNumber.EntityNumberInterface;

public abstract class AbstractPoliticianNumber implements EntityNumberInterface {

	protected final String firstName, lastName, politicianNumber;

	protected String getFirstName() {
		return firstName;
	}

	protected String getLastName() {
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
	
	abstract AbstractPoliticianNumber calculatePoliticianNumber();

}
