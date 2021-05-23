package com.example.demo.model.entities;

import javax.persistence.Embeddable;

import com.example.demo.model.enums.PoliticalParty;
import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class UserRater {

	@JsonProperty("facebook_name")
	private String facebookName;
	
	@JsonProperty("political_party")
	private PoliticalParty politicalParties;
	
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookName() {
		return facebookName;
	}

	public void setFacebookName(String facebookName) {
		this.facebookName = facebookName;
	}

	public PoliticalParty getPoliticalParties() {
		return politicalParties;
	}

	public void setPoliticalParties(PoliticalParty politicalParties) {
		this.politicalParties = politicalParties;
	}

	public UserRater(String facebookName, PoliticalParty politicalParties, String email) {
		super();
		this.facebookName = facebookName;
		this.politicalParties = politicalParties;
		this.email = email;
	}

	public UserRater() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "{facebookName=" + facebookName + ", politicalParties=" + politicalParties + ", email=" + email
				+ "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((facebookName == null) ? 0 : facebookName.hashCode());
		result = prime * result + ((politicalParties == null) ? 0 : politicalParties.hashCode());
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
		UserRater other = (UserRater) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (facebookName == null) {
			if (other.facebookName != null)
				return false;
		} else if (!facebookName.equals(other.facebookName))
			return false;
		if (politicalParties != other.politicalParties)
			return false;
		return true;
	}
	
}
