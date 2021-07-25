package com.example.demo.dtoRequest;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddRatingDTORequest {

	@NotNull
	@DecimalMin("0.01")
	@DecimalMax("10.00")
	private BigDecimal rating;
	
	@NotNull
	private String id;

	@NotNull
	@JsonProperty(value = "political_party")
	private String politicalParty;

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPoliticalParty() {
		return politicalParty;
	}

	public void setPoliticalParty(String politicalParty) {
		this.politicalParty = politicalParty;
	}

	public AddRatingDTORequest(@NotNull @DecimalMin("0.01") @DecimalMax("10.00") BigDecimal rating, @NotNull String id,
			@NotNull String politicalParty) {
		super();
		this.rating = rating;
		this.id = id;
		this.politicalParty = politicalParty;
	}

	public AddRatingDTORequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AddRatingDTORequest [rating=" + rating + ", id=" + id + ", politicalParty=" + politicalParty + "]";
	}

		
	
}
