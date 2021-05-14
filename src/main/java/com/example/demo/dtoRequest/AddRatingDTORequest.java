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

	public void setPoliticalParty(String politicalParty) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((politicalParty == null) ? 0 : politicalParty.hashCode());
		result = prime * result + ((politicianName == null) ? 0 : politicianName.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddRatingDTORequest other = (AddRatingDTORequest) obj;
		if (politicalParty == null) {
			if (other.politicalParty != null)
				return false;
		} else if (!politicalParty.equals(other.politicalParty))
			return false;
		if (politicianName == null) {
			if (other.politicianName != null)
				return false;
		} else if (!politicianName.equals(other.politicianName))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		return true;
	}
	
}
