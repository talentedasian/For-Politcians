package com.example.demo.dtoRequest;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class AddRatingDTORequest {

	@NotNull
	private String facebookName;
	
	@NotNull
	@DecimalMin("0.01")
	@DecimalMax("10.00")
	private BigDecimal rating;

	public String getFacebookName() {
		return facebookName;
	}

	public void setFacebookName(String facebookName) {
		this.facebookName = facebookName;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public AddRatingDTORequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddRatingDTORequest(@NotNull String facebookName,
			@NotNull @DecimalMin("0.01") @DecimalMax("10.00") BigDecimal rating) {
		super();
		this.facebookName = facebookName;
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "AddRatingDTORequest [facebookName=" + facebookName + ", rating=" + rating + "]";
	}
	
}
