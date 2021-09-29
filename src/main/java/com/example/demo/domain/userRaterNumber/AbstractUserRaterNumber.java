package com.example.demo.domain.userRaterNumber;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.entityNumber.EntityNumberInterface;

public abstract class AbstractUserRaterNumber implements EntityNumberInterface{
	
	private final String firstName, lastName;
	
	protected final String accountNumber;
	
	private final LoginMechanism loginMechanism;
	
	protected String firstName() {
		return this.firstName;
	}
	
	protected String lastName() {
		return this.lastName;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public LoginMechanism getLoginMechanism() {
		return loginMechanism;
	}

	public AbstractUserRaterNumber(String firstName, String lastName, String accountNumber,
			LoginMechanism loginMechanism) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountNumber = accountNumber;
		this.loginMechanism = loginMechanism;
	}

	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "AbstractUserRaterNumber [firstName=" + firstName + ", lastName=" + lastName + ", accountNumber="
				+ accountNumber + ", loginMechanism=" + loginMechanism + "]";
	}

	@Override
	public AbstractUserRaterNumber calculateEntityNumber() {
		return calculateUserAccountNumber();
	}

	protected abstract AbstractUserRaterNumber calculateUserAccountNumber();

}
