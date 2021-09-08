package com.example.demo.domain.politicians;

import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.domain.averageCalculator.AverageCalculator;
import com.example.demo.domain.averageCalculator.DecentSatisfactionAverageCalculator;
import com.example.demo.domain.averageCalculator.HighSatisfactionAverageCalculator;
import com.example.demo.domain.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.Rating;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Politicians {

	private Name name;

	private List<PoliticiansRating> politiciansRating;

	private Rating rating;

	private PoliticianNumber politicianNumber;

	private Politicians.Type type;

	// count of ratings regardless of deletion of ratings.
	private int totalCountsOfRating;

	public String retrievePoliticianNumber() {
		return politicianNumber == null ? null : politicianNumber.politicianNumber();
	}

	public List<PoliticiansRating> getPoliticiansRating() {
		return this.politiciansRating;
	}

	public void setPoliticiansRating(List<PoliticiansRating> politiciansRating) {
		this.politiciansRating = politiciansRating;
	}

	public Rating getRating() {
		return this.rating;
	}

	public double averageRating() {
		return rating.getAverageRating();
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

	protected Politicians(Name name,List<PoliticiansRating> politiciansRating, Rating rating, PoliticianNumber politicianNumber, Type polType) {
		super();
		this.name = name;
		this.politiciansRating = politiciansRating;
		this.rating = rating;
		this.politicianNumber = politicianNumber;
		this.type = polType;
	}

	@Override
	public String toString() {
		return "Politicians{" +
				"name=" + name +
				", politiciansRating=" + politiciansRating +
				", rating=" + rating +
				", politicianNumber=" + politicianNumber +
				", type=" + type +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Politicians that = (Politicians) o;

		if (!politicianNumber.equals(that.politicianNumber)) return false;
		return Objects.equals(rating, that.rating);
	}

	@Override
	public int hashCode() {
		int result = rating != null ? rating.hashCode() : 0;
		result = 31 * result + politicianNumber.hashCode();
		return result;
	}

	public int totalCountsOfRatings() {
		return this.totalCountsOfRating;
	}

	public double calculateAverageRating(double ratingToAdd) {
		double rating = getRating().calculateAverage(ratingToAdd,
				returnAverageCalculator(getRating().getAverageRating(), getRating().calculateTotalAmountOfRating(ratingToAdd)));
		return rating;
	}


	public AverageCalculator returnAverageCalculator(double averageRating, double totalRating) {
		if (averageRating < 5D) {
			return new LowSatisfactionAverageCalculator(totalRating, totalCountsOfRatings());
		} else if (averageRating < 8.89D) {
			return new DecentSatisfactionAverageCalculator(totalRating, totalCountsOfRatings());
		} else if (averageRating >= 8.89D) {
			return new HighSatisfactionAverageCalculator(totalRating, totalCountsOfRatings());
		}
		return null;
	}

	public void rate(PoliticiansRating rating) {
		totalCountsOfRating++;
		calculateAverageRating(rating.getRating());
		politiciansRating.add(rating);
	}

	// INFO : DOES NOT CHANGE OVERALL BEHAVIOUR OF POLITICIAN. DELETING A RATING DOES NOT CHANGE THE TOTAL RATING AND THE AVERAGE RATING
	public void deleteRate(PoliticiansRating secondRating) {
		politiciansRating.remove(secondRating);
	}

	public long countsOfRatings() {
		return politiciansRating == null ? 0 : politiciansRating.size();
	}

	public Name recordName() {
		return this.name;
	}

	public String fullName() {
		return name.fullName();
	}

	public String firstName() {
		return name.firstName();
	}

	public String lastName() {
		return name.lastName();
	}

    protected PoliticianNumber getPoliticianNumber() {
		return this.politicianNumber;
    }

    public enum Type {
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

		@Deprecated(forRemoval = true) // GOAL : use constructor below instead
		public PoliticiansBuilder(String politicianNumber) {
			this.politicianNumber = politicianNumber;
		}

		public PoliticiansBuilder(PoliticianNumber politicianNumber) {
			this.politicianNumber = politicianNumber.politicianNumber();
		}

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
				throw new IllegalArgumentException("First and Last fullName cannot be null");
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
			var builder = new PoliticiansBuilder(PoliticianNumber.of(politicianNumber))
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
			Assert.state(firstName != null | !firstName.isEmpty() | !firstName.isBlank(), "first name cannot be left unspecified");

			var name = new Name(firstName, lastName);
			return new Politicians(name, politiciansRating, rating, new PoliticianNumber(politicianNumber), null);
		}

	}
	
}