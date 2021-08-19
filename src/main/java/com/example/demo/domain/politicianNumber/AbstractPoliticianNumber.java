package com.example.demo.domain.politicianNumber;

import com.example.demo.domain.entityNumber.EntityNumberInterface;
import com.example.demo.domain.politicians.Name;

public abstract class AbstractPoliticianNumber implements EntityNumberInterface {

	protected final String firstName, lastName, politicianNumber;

	protected String firstName() {
		return firstName;
	}

	protected String lastName() {
		return lastName;
	}

	public String getPoliticianNumber() {
		return politicianNumber;
	}

	public AbstractPoliticianNumber(Name name, String politicianNumber) {
		super();
		this.firstName = name.firstName();
		this.lastName = name.lastName();
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
