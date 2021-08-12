package com.example.demo.domain.politicians;

import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.entities.Rating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "Politicians [firstName=" + firstName + ", lastName=" + lastName + ", fullName="
				+ fullName +  ", rating=" + rating + ", politicianNumber=" + politicianNumber + "]";
	}

	public double calculateAverageRating() {
		double rating = getRating().calculateAverage();
		
		return rating;
	}
	
	private Double convertLongToDouble(long longValue) {
		return Double.valueOf(String.valueOf(longValue));
	}

	
	public double calculateTotalAmountOfRating(Double rating) {
		double totalRating = getRating().calculateTotalAmountOfRating(rating, convertLongToDouble(returnCountsOfRatings()));
		
		return totalRating;
	}

	public long returnCountsOfRatings() {
		return politiciansRating.size();
	}

	public List<PoliticiansRating> calculateListOfRaters(PoliticiansRating rater) {
		List<PoliticiansRating> listOfPoliticiansRating = getPoliticiansRating();
		listOfPoliticiansRating.add(rater);
		setPoliticiansRating(listOfPoliticiansRating);
		
		return listOfPoliticiansRating;
	}

	public String calculateFullName() {
		String fullName = this.firstName + "\s" + this.lastName;
		this.fullName = fullName;
		
		return fullName;
	}
	
	public static enum Type {
		PRESIDENTIAL, SENATORIAL, MAYOR;

		static Map<String, Type> cache = new HashMap<>();

		static {
			cache.put("Presidential", PRESIDENTIAL);
			cache.put("Senatorial", SENATORIAL);
			cache.put("Mayorial", MAYOR);
		}

        public static Type mapToPoliticianType(String type) {
			if (!cache.containsKey(type)) {
				throw new IllegalArgumentException("type is non existing");
			}

			return cache.get(type);
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