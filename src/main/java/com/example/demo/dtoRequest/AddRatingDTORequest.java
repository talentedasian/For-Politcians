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
	private String politicianLastName;
	
	@NotNull
	private String politicianFirstName;
	
	@NotNull
	private String politicalParty;

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public String getPoliticianLastName() {
		return politicianLastName;
	}

	public void setPoliticianLastName(String politicianLastName) {
		this.politicianLastName = politicianLastName;
	}

	public String getPoliticianFirstName() {
		return politicianFirstName;
	}

	public void setPoliticianFirstName(String politicianFirstName) {
		this.politicianFirstName = politicianFirstName;
	}

	public String getPoliticalParty() {
		return politicalParty;
	}

	public void setPoliticalParty(String politicalParty) {
		this.politicalParty = politicalParty;
	}

	public AddRatingDTORequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddRatingDTORequest(@NotNull @DecimalMin("0.01") @DecimalMax("10.00") BigDecimal rating,
			@NotNull String politicianLastName, @NotNull String politicianFirstName, @NotNull String politicalParty) {
		super();
		this.rating = rating;
		this.politicianLastName = politicianLastName;
		this.politicianFirstName = politicianFirstName;
		this.politicalParty = politicalParty;
	}

	@Override
	public String toString() {
		return "AddRatingDTORequest [rating=" + rating + ", politicianLastName=" + politicianLastName
				+ ", politicianFirstName=" + politicianFirstName + ", politicalParty=" + politicalParty + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((politicalParty == null) ? 0 : politicalParty.hashCode());
		result = prime * result + ((politicianFirstName == null) ? 0 : politicianFirstName.hashCode());
		result = prime * result + ((politicianLastName == null) ? 0 : politicianLastName.hashCode());
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
		if (politicianFirstName == null) {
			if (other.politicianFirstName != null)
				return false;
		} else if (!politicianFirstName.equals(other.politicianFirstName))
			return false;
		if (politicianLastName == null) {
			if (other.politicianLastName != null)
				return false;
		} else if (!politicianLastName.equals(other.politicianLastName))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		return true;
	}
	
}
