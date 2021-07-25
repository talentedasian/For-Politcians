package com.example.demo.dtoRequest;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;

public class AddPoliticianDTORequest {

	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	@DecimalMin("0.01")
	@DecimalMax("9.99")
	private BigDecimal rating;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public AddPoliticianDTORequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddPoliticianDTORequest(@NotNull String firstName, @NotNull String lastName,
			@NotNull @DecimalMin("0.01") @DecimalMax("9.99") BigDecimal rating) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.rating = rating;
	}

	@ExcludeFromJacocoGeneratedCoverage
	@Override
	public String toString() {
		return "AddPoliticianDTORequest [firstName=" + firstName + ", lastName=" + lastName + ", rating=" + rating
				+ "]";
	}

}
