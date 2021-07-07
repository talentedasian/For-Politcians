package com.example.demo.model.entities;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.example.demo.jwt.JwtProvider;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.service.RateLimitingService;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.jsonwebtoken.JwtException;

@Embeddable
public class UserRater {

	@Transient
	private transient RateLimitingService limitingService;
	
	public void setLimitingService(RateLimitingService limitingService) {
		this.limitingService = limitingService;
	}

	@JsonProperty("name")
	private String facebookName;
	
	@JsonProperty("political_party")
	private PoliticalParty politicalParties;
	
	private String email;
	
	@JsonProperty("id")
	private String userAccountNumber;
	
	public String getUserAccountNumber() {
		return userAccountNumber;
	}

	public void setUserAccountNumber(String userAccountNumber) {
		this.userAccountNumber = userAccountNumber;
	}

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

	public UserRater(String facebookName, PoliticalParty politicalParties, String email, 
			String accNumber, RateLimitingService limitingService) {
		super();
		this.facebookName = facebookName;
		this.politicalParties = politicalParties;
		this.email = email;
		this.userAccountNumber = accNumber;
		this.limitingService = limitingService;
	}

	public UserRater() {
		this.limitingService = null;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "UserRater [facebookName=" + facebookName + ", politicalParties=" + politicalParties + ", email=" + email
				+ ", userAccountNumber=" + userAccountNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((facebookName == null) ? 0 : facebookName.hashCode());
		result = prime * result + ((politicalParties == null) ? 0 : politicalParties.hashCode());
		result = prime * result + ((userAccountNumber == null) ? 0 : userAccountNumber.hashCode());
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
		if (userAccountNumber == null) {
			if (other.userAccountNumber != null)
				return false;
		} else if (!userAccountNumber.equals(other.userAccountNumber))
			return false;
		return true;
	}
	
	public boolean canRate(String jwt, String polNumber) {
		if (jwt == null | jwt.isBlank() | jwt.isEmpty()) {
			return false;
		}
		
		if (!isJwtValid(jwt)) {
			return false;
		} else {
			if (isRateLimited(polNumber)) {
				return false;
			}			
		}
		return true;
	}
	
	private boolean isJwtValid(String jwt) {
		try {
			JwtProvider.decodeJwt(jwt);
			return true;
		} catch (JwtException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean isRateLimited(String politicianNumber) {
		if (limitingService.isNotRateLimited(userAccountNumber, politicianNumber)) {
			System.out.println(limitingService.isNotRateLimited(userAccountNumber, politicianNumber) + " tanga ka gago");
		}
		return !limitingService.isNotRateLimited(userAccountNumber, politicianNumber);
	}
	
}
