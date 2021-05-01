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
	
}
