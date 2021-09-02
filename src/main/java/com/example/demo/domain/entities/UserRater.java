package com.example.demo.domain.entities;

import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.enums.PoliticalParty;
import com.example.demo.domain.politicians.PoliticianNumber;

import java.time.LocalDate;
import java.util.*;

public class UserRater {

	private String facebookName;

	private PoliticalParty politicalParties;
	
	private String email;

	private AccountNumber userAccountNumber;

	private Map<PoliticianNumber, RateLimit> rateLimit;

	public String returnUserAccountNumber() {
		return userAccountNumber.accountNumber();
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

	public List<RateLimit> getRateLimits() {
		return List.copyOf(rateLimit.values());
	}

	public UserRater(String facebookName, PoliticalParty politicalParties, String email,
					 AccountNumber userAccountNumber, Map<PoliticianNumber, RateLimit> rateLimit) {
		this.facebookName = facebookName;
		this.politicalParties = politicalParties;
		this.email = email;
		this.userAccountNumber = userAccountNumber;
		this.rateLimit = rateLimit;
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
		if (!Objects.equals(new AccountNumber(other.returnUserAccountNumber()), userAccountNumber)) return false;
		if (!Objects.equals(other.rateLimit, rateLimit)) return false;
		return true;
	}

	public boolean canRate(String polNumber) {
		return !isRateLimited(new PoliticianNumber(polNumber));
	}

	public void rateLimitUser(PoliticianNumber polNumber) {
		rateLimit.put(polNumber, new RateLimit(userAccountNumber.accountNumber(), polNumber, LocalDate.now()));
	}

	public Optional<RateLimit> findRateLimit(PoliticianNumber politicianNumber) {
		return Optional.ofNullable(rateLimit.get(politicianNumber));
	}

	private boolean isRateLimited(PoliticianNumber politicianNumber) {
		return rateLimit.containsKey(politicianNumber) ? !rateLimit.get(politicianNumber).isNotRateLimited() : false;
	}

    public long daysLeftToRate(String polNumber) {
		var rl = rateLimit.get(new PoliticianNumber(polNumber));

		try {
			return	rl.daysLeftOfBeingRateLimited();
		} catch (IllegalStateException e) {
			return 0l;
		}
    }

    public static class Builder {

		private String name, email;

		private PoliticalParty politicalParty;

		private String userAccountNumber;

		private RateLimitRepository repo;

		private Map<PoliticianNumber, RateLimit> rateLimit = null;

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

		public Builder setRateLimit(List<RateLimit> rateLimits) {
			Map<PoliticianNumber, RateLimit> map = new HashMap<>();
			if (rateLimits == null) {
				this.rateLimit = map;
				return this;
			}
			rateLimits.stream()
					.forEach(it -> map.put(new PoliticianNumber(it.politicianNumber()), it));
			this.rateLimit = map;
			return this;
		}

		public UserRater build() {
			return new UserRater(name, politicalParty, email, new AccountNumber(userAccountNumber), rateLimit);
		}


	}

	
}
