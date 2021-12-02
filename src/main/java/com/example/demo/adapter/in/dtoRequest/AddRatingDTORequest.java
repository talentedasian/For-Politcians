package com.example.demo.adapter.in.dtoRequest;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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

	public String getRating() {
		return rating.toString();
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

	@ExcludeFromJacocoGeneratedCoverage
	@Override
	public String toString() {
		return "AddRatingDTORequest [rating=" + rating + ", id=" + id + ", politicalParty=" + politicalParty + "]";
	}
	
}
