package com.example.demo.dtoRequest;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class AddPoliticianDTORequest {

	@NotNull
	private String name;
	
	@NotNull
	@DecimalMin("0.01")
	@DecimalMax("10.00")
	private BigDecimal rating;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public AddPoliticianDTORequest(@NotNull String name,
			@NotNull @DecimalMin("0.01") @DecimalMax("10.00") BigDecimal rating) {
		super();
		this.name = name;
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "AddPoliticianDTORequest [name=" + name + ", rating=" + rating + "]";
	}
	
}
