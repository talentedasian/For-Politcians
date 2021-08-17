package com.example.demo.domain.politicians;

import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.Rating;

import java.util.ArrayList;
import java.util.List;


public class Politicians {

	private String firstName;

	private String lastName;

	private String fullName;

	private List<PoliticiansRating> politiciansRating;

	private Rating rating;

	private String politicianNumber;

	private Politicians.Type type;

	public String getPoliticianNumber() {
		return politicianNumber;
	}

	public void setPoliticianNumber(String politicianNumber) {
		this.politicianNumber = politicianNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<PoliticiansRating> getPoliticiansRating() {
		return politiciansRating;
	}

	public void setPoliticiansRating(List<PoliticiansRating> politiciansRating) {
		this.politiciansRating = politiciansRating;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}
	
	public Politicians.Type getType() {
		return type;
	}

	public void setType(Politicians.Type type) {
		this.type = type;
	}

	protected Politicians() {
	}

	protected Politicians(String firstName, String lastName, String fullName,
			List<PoliticiansRating> politiciansRating, Rating rating, String politicianNumber, Type polType) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.politiciansRating = politiciansRating;
		this.rating = rating;
		this.politicianNumber = politicianNumber;
		this.type = polType;
	}

	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "Politicians [firstName=" + firstName + ", lastName=" + lastName + ", fullName="
				+ fullName +  ", rating=" + rating + ", politicianNumber=" + politicianNumber + "]";
	}

	public double calculateAverageRating(double ratingToAdd) {
		double rating = getRating().calculateAverage(ratingToAdd, Long.valueOf(countsOfRatings()).doubleValue());
		
		return rating;
	}

	public long countsOfRatings() {
		return politiciansRating == null ? 0 : politiciansRating.size();
	}

	public static enum Type {
		PRESIDENTIAL("presidential, PRESIDENTIAL"), SENATORIAL("senatorial, SENATORIAL"),
		MAYOR("mayorial, MAYORIAL");

		Type(String s) {
		}
	}
	
	public static class PoliticiansBuilder {
		private RatingRepository ratingRepo;
		
		private Integer id;
		
		private String firstName;
		
		private String lastName;
		
		private String fullName;
		
		private List<PoliticiansRating> politiciansRating;

		private Rating rating;
		
		private String politicianNumber;

		public PoliticiansBuilder(String politicianNumber) {
			this.politicianNumber = politicianNumber;
		}

		public PoliticiansBuilder() {}

		public PoliticiansBuilder setId(Integer id) {
			this.id = id;
			return this;
		}

		public PoliticiansBuilder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public PoliticiansBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public PoliticiansBuilder setFullName() {
			if (firstName == null && lastName == null) {
				throw new IllegalArgumentException("First and Last name cannot be null");
			}
			
			if (lastName == null) {
				this.fullName = firstName;
				return this;
			}
			
			this.fullName = firstName + " " + lastName;
			return this;
		}

		public PoliticiansBuilder setPoliticiansRating(List<PoliticiansRating> politiciansRating) {
			if (politiciansRating == null) {
				this.politiciansRating = new ArrayList<>();
				return this;
			}
			this.politiciansRating = politiciansRating;
			return this;
		}

		public PoliticiansBuilder setRating(Rating rating) {
			this.rating = rating;
			return this;
		}

		/*
		 * Politician number should not change in an object so this
		 * method returns a new Builder with the politicianNumber
		 */
		public PoliticiansBuilder setPoliticianNumber(String politicianNumber) {
			var builder = new PoliticiansBuilder(politicianNumber)
				.setId(id)
				.setFirstName(firstName)
				.setLastName(lastName)
				.setRating(rating)
				.setPoliticiansRating(politiciansRating)
				.setRatingRepository(ratingRepo);
			if (firstName.isEmpty() || firstName == null) {
				return builder;
			}
			return builder.setFullName();
		}
		
		public PoliticiansBuilder setRatingRepository(RatingRepository ratingRepo) {
			this.ratingRepo = ratingRepo;
			return this;
		}
		
		public Politicians build() {
			return new Politicians(firstName, lastName, fullName, politiciansRating, rating, politicianNumber, null);
		}

	}
	
}