package com.example.demo.domain.entities;

import com.example.demo.domain.enums.PoliticalParty;

import java.util.Objects;

public class UserRater {

	private final String facebookName;

	private final PoliticalParty politicalParties;
	
	private final String email;

	private final AccountNumber userAccountNumber;

	public String returnUserAccountNumber() {
		return userAccountNumber.accountNumber();
	}

	public AccountNumber accountNumber() {
		return this.userAccountNumber;
	}

	public String email() {
		return email;
	}

	public String name() {
		return facebookName;
	}

	public PoliticalParty politicalParty() {
		return politicalParties;
	}

	UserRater(String facebookName, PoliticalParty politicalParties, String email, AccountNumber userAccountNumber) {
		this.facebookName = facebookName;
		this.politicalParties = politicalParties;
		this.email = email;
		this.userAccountNumber = userAccountNumber;
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
		return Objects.equals(other.userAccountNumber, userAccountNumber);
	}

	public boolean canRate(UserRateLimitService service, PoliticianNumber politicianNumber) {
		return service.isUserNotRateLimited(userAccountNumber, politicianNumber);
	}

	public void rateLimitUser(UserRateLimitService rateLimitService, PoliticianNumber polNumber) {
		rateLimitService.rateLimitUser(userAccountNumber, polNumber);
	}

	public long daysLeftToRate(UserRateLimitService service, PoliticianNumber politicianNumber) {
		return service.daysLeftToRateForUser(userAccountNumber, politicianNumber);
	}

    public static class Builder {

		private String name, email;

		private PoliticalParty politicalParty;

		private String userAccountNumber;

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

		public Builder setAccountNumber(String accNumber) {
			this.userAccountNumber = accNumber;
			return this;
		}

		public UserRater build() {
			return new UserRater(name, politicalParty, email, new AccountNumber(userAccountNumber));
		}

	}
	
}
