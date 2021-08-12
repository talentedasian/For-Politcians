package com.example.demo.domain.entities;

import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.enums.PoliticalParty;

import java.util.Optional;

public class UserRater {

	private RateLimitRepository rateLimitRepository;

	private String facebookName;

	private PoliticalParty politicalParties;
	
	private String email;

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

	UserRater(String facebookName, PoliticalParty politicalParties, String email,
			String accNumber, RateLimitRepository rateLimitRepository) {
		super();
		this.facebookName = facebookName;
		this.politicalParties = politicalParties;
		this.email = email;
		this.userAccountNumber = accNumber;
		this.rateLimitRepository = rateLimitRepository;
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
		if (other.getUserAccountNumber() == null) {
			return false;
		} else {
			if (!other.getUserAccountNumber().equals(userAccountNumber)) {
				return false;
			}
		}
		return false;
	}

	public boolean canRate(String polNumber) {
		return isRateLimited(polNumber);
	}
	private boolean isRateLimited(String politicianNumber) {
		Optional<RateLimit> rateLimit = rateLimitRepository.findUsingIdAndPoliticianNumber(userAccountNumber, politicianNumber);
		return rateLimit.isEmpty() ? false : rateLimit.get().isNotRateLimited();
	}

	public static class UserRaterBuilder {

		private String name, email;

		private PoliticalParty politicalParty;

		private String userAccountNumber;

		private RateLimitRepository repo;

		public UserRaterBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public UserRaterBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public UserRaterBuilder setPoliticalParty(PoliticalParty politicalParty) {
			this.politicalParty = politicalParty;
			return this;
		}

		public UserRaterBuilder setRateLimitRepo(RateLimitRepository repo) {
			this.repo = repo;
			return this;
		}

		public UserRaterBuilder setAccountNumber(String accNumber) {
			this.userAccountNumber = accNumber;
			return this;
		}

		public UserRater build() {
			return new UserRater(name, politicalParty, email, userAccountNumber, repo);
		}


	}

	
}
