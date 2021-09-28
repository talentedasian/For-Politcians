package com.example.demo.domain.entities;

import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.domain.AverageRating;
import com.example.demo.domain.Score;
import com.example.demo.domain.TotalRatingAccumulated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.demo.domain.AverageRating.NO_RATING_YET;


public class Politicians {

	private Name name;

	private List<PoliticiansRating> politiciansRating = new ArrayList<>();;

	private Rating rating;

	private AverageRating averageRating;

	private PoliticianNumber politicianNumber;

	private Politicians.Type type;

	private TotalRatingAccumulated totalRatingAccumulated;

	// count of ratings regardless of deletion of ratings.
	private int totalCountsOfRating;

	public String retrievePoliticianNumber() {
		return politicianNumber == null ? null : politicianNumber.politicianNumber();
	}

	public List<PoliticiansRating> getPoliticiansRating() {
		return List.copyOf(politiciansRating);
	}

	public void setPoliticiansRating(List<PoliticiansRating> politiciansRating) {
		this.politiciansRating = politiciansRating;
	}

	public Rating getRating() {
		return this.rating;
	}

	public double averageRating() {
		return Objects.equals(averageRating, NO_RATING_YET) ? 0 : averageRating.averageRating();
	}

	public AverageRating average() {
		return this.averageRating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}
	
	public Politicians.Type getType() {
		return type;
	}


	Politicians(Name name,List<PoliticiansRating> politiciansRating, Rating rating, AverageRating averageRating,
						  TotalRatingAccumulated totalRatingAccumulated, PoliticianNumber politicianNumber, Type polType) {
		this.name = name;
		this.politiciansRating.addAll(politiciansRating == null ? List.of() : politiciansRating);
		this.totalCountsOfRating = politiciansRating.size();
		this.rating = rating;
		this.averageRating = averageRating;
		this.politicianNumber = politicianNumber;
		this.type = polType;
		this.totalRatingAccumulated = (totalRatingAccumulated == null)
				? TotalRatingAccumulated.ZERO : totalRatingAccumulated;
	}

	@Override
	public String toString() {
		return "Politicians{ " +
				"name= " + name +
				", rating= " + rating +
				", totalCountsOfRating= " + totalCountsOfRating +
				", politicianNumber= " + politicianNumber +
				", totalRatingAccumulated= "  + totalRatingAccumulated +
				", type=" + type + " }";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Politicians that = (Politicians) o;

		if (!politicianNumber.equals(that.politicianNumber)) return false;
		if (rating == null) return that.rating == null;
		return Objects.equals(rating.averageRating, that.rating.averageRating);
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

	public AverageRating calculateAverageRating(Score ratingToAdd) {
		if (isAverageRatingPresent()) {
			BigDecimal totalScoreAccumulated = totalRatingAccumulated.addTotalRating(ratingToAdd).totalRating();
			return AverageRating.of(totalScoreAccumulated,totalCountsOfRating, averageRating);
		}

		return AverageRating.of(BigDecimal.valueOf(ratingToAdd.rating()));
	}

	public boolean isAverageRatingPresent() {
		return averageRating != NO_RATING_YET;
	}

	void rate(PoliticiansRating rating) {
		addCountsOfTotalRating();

		double calculatedAverageRating = calculateAverageRating(Score.of(rating.getRating())).averageRating();
		changeAverageRating(AverageRating.of(BigDecimal.valueOf(calculatedAverageRating)));

		changeTotalRatingAccumulated(Score.of(rating.getRating()));

		politiciansRating.add(rating);
	}

	private void addCountsOfTotalRating() {
		totalCountsOfRating++;
	}

	private void changeTotalRatingAccumulated(Score score) {
		this.totalRatingAccumulated = totalRatingAccumulated.addTotalRating(score);
	}

	private void changeAverageRating(AverageRating averageRating) {
		this.averageRating = averageRating;
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

    protected PoliticianNumber politicianNumber() {
		return this.politicianNumber;
    }

	public TotalRatingAccumulated totalRatingAccumulated() {
		return totalRatingAccumulated;
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

		private TotalRatingAccumulated totalRatingAccumulated;

		private BigDecimal totalRating;

		private AverageRating averageRating;

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

		public PoliticiansBuilder setTotalRating(BigDecimal totalRatingAccumulated) {
			this.totalRating = totalRatingAccumulated;
			return this;
		}

		public PoliticiansBuilder setAverageRating(AverageRating averageRating) {
			this.averageRating = averageRating;
			return this;
		}

		public PoliticiansBuilder setAverageRating(double averageRating) {
			this.averageRating = AverageRating.of(BigDecimal.valueOf(averageRating));
			return this;
		}

		public PoliticiansBuilder setRatingRepository(RatingRepository ratingRepo) {
			this.ratingRepo = ratingRepo;
			return this;
		}

		public Politicians build() {
			var name = new Name(firstName, lastName);
			if (politiciansRating == null) politiciansRating = List.of();
			if (totalRating != null)
				this.totalRatingAccumulated = TotalRatingAccumulated.of(totalRating, averageRating);

			return new Politicians(name, politiciansRating, rating, averageRating, totalRatingAccumulated, new PoliticianNumber(politicianNumber), null);
		}
	}
	
}