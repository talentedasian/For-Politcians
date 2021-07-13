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
			return false;
		}
	}
	
	private boolean isRateLimited(String politicianNumber) {
		return !limitingService.isNotRateLimited(userAccountNumber, politicianNumber);
	}
	
}
