package com.example.demo.dtoRequest;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class AddRatingDTORequest {

	@NotNull
	@DecimalMin("0.01")
	@DecimalMax("10.00")
	private BigDecimal rating;
	
	@NotNull
	private String politicianName;
	
	@NotNull
	private String politicalParty;

	public String getPoliticalParty() {
		return politicalParty;
	}

	public void setPoliticialParty(String politicalParty) {
		this.politicalParty = politicalParty;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public String getPoliticianName() {
		return politicianName;
	}

	public void setPoliticianName(String politicianName) {
		this.politicianName = politicianName;
	}

	public AddRatingDTORequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddRatingDTORequest(@NotNull @DecimalMin("0.01") @DecimalMax("10.00") BigDecimal rating,
			@NotNull String politicianName, @NotNull String politicalParty) {
		super();
		this.rating = rating;
		this.politicianName = politicianName;
		this.politicalParty = politicalParty;
	}

	@Override
	public String toString() {
		return "AddRatingDTORequest [rating=" + rating + ", politicianName=" + politicianName + ", politicalParty="
				+ politicalParty + "]";
	}
	
}
