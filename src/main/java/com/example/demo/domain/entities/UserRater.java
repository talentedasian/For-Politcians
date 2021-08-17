package com.example.demo.domain.entities;

import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.enums.PoliticalParty;
import com.example.demo.domain.userRaterNumber.facebook.FacebookUserRaterNumberImplementor;

import java.util.Optional;

public class UserRater {

	private RateLimitRepository rateLimitRepository;

	private String facebookName;

	private PoliticalParty politicalParties;
	
	private String email;

	private AccountNumber userAccountNumber;

	public String returnUserAccountNumber() {
		return userAccountNumber.retrieveAccountNumber();
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

	public PoliticalParty getPoliticalParties() {
		return politicalParties;
	}

	public RateLimitRepository getRateLimitRepository() {
		return rateLimitRepository;
	}

	UserRater(String facebookName, PoliticalParty politicalParties, String email,
			  String accNumber, RateLimitRepository rateLimitRepository) {
		super();
		this.facebookName = facebookName;
		this.politicalParties = politicalParties;
		this.email = email;  												// TODO : change with dependency injection rather than constructing itself.
		this.userAccountNumber = new AccountNumber(accNumber, FacebookUserRaterNumberImplementor.with(facebookName, accNumber));
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
		if (other.returnUserAccountNumber() == null) {
			return false;
		} else {
			if (!other.returnUserAccountNumber().equals(userAccountNumber)) {
				return false;
			}
		}
		return false;
	}

	public boolean canRate(String polNumber) {
		return isRateLimited(polNumber);
	}
	private boolean isRateLimited(String politicianNumber) {
		Optional<RateLimit> rateLimit = rateLimitRepository.findUsingIdAndPoliticianNumber(userAccountNumber.retrieveAccountNumber(), politicianNumber);

		return rateLimit.isPresent() ? !rateLimit.get().isNotRateLimited() : false;
	}

    public long daysLeftToRate(String polNumber) {
		return rateLimitRepository.findUsingIdAndPoliticianNumber(userAccountNumber.retrieveAccountNumber(), polNumber).get().daysLeftOfBeingRateLimited().longValue();
    }

    public static class Builder {

		private String name, email;

		private PoliticalParty politicalParty;

		private String userAccountNumber;

		private RateLimitRepository repo;

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setPoliticalParty(PoliticalParty politicalParty) {
			this.politicalParty = politicalParty;
			return this;
		}

		public Builder setRateLimitRepo(RateLimitRepository repo) {
			this.repo = repo;
			return this;
		}

		public Builder setAccountNumber(String accNumber) {
			this.userAccountNumber = accNumber;
			return this;
		}

		public UserRater build() {
			return new UserRater(name, politicalParty, email, userAccountNumber, repo);
		}


	}

	
}
