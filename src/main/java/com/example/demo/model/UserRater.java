package com.example.demo.model;

import javax.persistence.Embeddable;

import com.example.demo.model.enums.PoliticalParty;

@Embeddable
public class UserRater {

	private String facebookName;
	
	private PoliticalParty politicalParties;

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

	public UserRater(String facebookName, PoliticalParty politicalParties) {
		super();
		this.facebookName = facebookName;
		this.politicalParties = politicalParties;
	}

	public UserRater() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "UserRater [facebookName=" + facebookName + ", politicalParties=" + politicalParties + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
