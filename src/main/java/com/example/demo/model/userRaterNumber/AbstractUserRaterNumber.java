package com.example.demo.model.userRaterNumber;

import com.example.demo.model.entityNumber.EntityNumberInterface;

public abstract class AbstractUserRaterNumber implements EntityNumberInterface{
	
	protected final String firstName, lastName, accountNumber;
	
	protected final LoginMechanism loginMechanism;
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
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
	public String toString() {
		return "AbstractUserRaterNumber [firstName=" + firstName + ", lastName=" + lastName + ", accountNumber="
				+ accountNumber + ", loginMechanism=" + loginMechanism + "]";
	}

	@Override
	public AbstractUserRaterNumber calculateEntityNumber() {
		return calculateUserAccountNumber();
	}

	public abstract AbstractUserRaterNumber calculateUserAccountNumber();

}
