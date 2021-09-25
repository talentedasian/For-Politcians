package com.example.demo.domain.entities;

import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.Score;
import com.example.demo.exceptions.UserRateLimitedOnPoliticianException;

public class PoliticiansRating {

	private RateLimitRepository rateLimitRepo;

	private Integer id;

	private Double rating; 

	private UserRater rater;

	private Politicians politician;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public UserRater getRater() {
		return rater;
	}

	public void setRater(UserRater rater) {
		this.rater = rater;
	}

	public RateLimitRepository getRateLimitRepo() {
		return rateLimitRepo;
	}

	public Politicians getPolitician() {
		return politician;
	}

	public void setPolitician(Politicians politician) {
		this.politician = politician;
	}

	public PoliticiansRating() {
		super();
	}

	public PoliticiansRating(Integer id, double rating, UserRater rater, Politicians politician) {
		super();
		this.id = id;
		this.rating = rating;
		this.rater = rater;
		this.politician = politician;
	}

	@Override
	public String toString() {
		return "PoliticiansRating [id=" + id + ", rating=" + rating + ", rater=" + rater +
				", politician=" + politician + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PoliticiansRating other = (PoliticiansRating) obj;
		if (other.id == null) return false;
		if (other.rater == null) return false;
		return other.id.equals(id) && other.rater.equals(rater);
	}

	public PoliticiansRating(Integer id, double rating, UserRater rater, Politicians politician, RateLimitRepository rateLimitRepository) {
		super();
		this.id = id;
		this.rating = rating;
		this.rater = rater;
		this.politician = politician;
		this.rateLimitRepo = rateLimitRepository;
	}

	public void ratePolitician(UserRateLimitService rateLimitService) throws UserRateLimitedOnPoliticianException {
		if (rater.canRate(rateLimitService, politician.politicianNumber())) {
			politician.rate(this);
			rater.rateLimitUser(rateLimitService, politician.politicianNumber());
			return;
		}

		long daysLeft = rater.daysLeftToRate(rateLimitService, PoliticianNumber.of(politician.retrievePoliticianNumber()));
		throw new UserRateLimitedOnPoliticianException(daysLeft, politician.politicianNumber());
	}

	public void deleteRating() {
		politician.deleteRate(this);
	}

	public static class Builder {
		private String id;

		private double rating;

		private UserRater rater;

		private Politicians politician;

		private RateLimitRepository rateLimitRepo;

		public Builder () {}

		public Builder (Politicians politicians) {
			this.politician = politicians;
		}

		public Builder (Politicians.PoliticiansBuilder politiciansBuilder) {
			this.politician = politiciansBuilder.build();
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

		public Builder setRating(Score rating) {
			this.rating = rating.rating();
			return this;
		}

		public Builder setRater(UserRater rater) {
			this.rater = rater;
			return this;
		}

		public Builder setPolitician(Politicians politician) {
			this.politician = politician;
			return this;
		}

		public Builder setRepo(RateLimitRepository repo) {
			rateLimitRepo = repo;
			return this;
		}

		public PoliticiansRating build() {
			try {
				return new PoliticiansRating(Integer.valueOf(id),rating, rater, politician, rateLimitRepo);
			} catch (NumberFormatException e) {
				return new PoliticiansRating(null, rating, rater, politician, rateLimitRepo);
			}
		}

	}

}
