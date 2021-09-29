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

	private final Name name;

	private List<PoliticiansRating> politiciansRating = new ArrayList<>();

	private AverageRating averageRating;

	private final PoliticianNumber politicianNumber;

	private final Politicians.Type type;

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

	public double averageRating() {
		return doesPoliticianHaveRating() ? 0 : averageRating.averageRating();
	}

	private boolean doesPoliticianHaveRating() {
		return AverageRating.hasRating(averageRating);
	}

	public AverageRating average() {
		return this.averageRating;
	}

	public Politicians.Type getType() {
		return type;
	}


	Politicians(Name name, List<PoliticiansRating> politiciansRating, AverageRating averageRating,
				TotalRatingAccumulated totalRatingAccumulated, PoliticianNumber politicianNumber, Type polType) {
		this.name = name;
		this.politiciansRating.addAll(politiciansRating == null ? List.of() : politiciansRating);
		this.totalCountsOfRating = politiciansRating.size();
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
				", rating= " + averageRating +
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
		return Objects.equals(averageRating, that.averageRating)
				&& totalRatingAccumulated.equals(that.totalRatingAccumulated)
				&& totalCountsOfRating == that.totalCountsOfRating;
	}

	@Override
	public int hashCode() {
		int result = averageRating != null ? averageRating.hashCode() : 0;
		result = 31 * result + politicianNumber.hashCode();
		result = 31 * result + totalRatingAccumulated.hashCode();
		result = 31 * result + totalCountsOfRating;
		return result;
	}

	public int totalCountsOfRatings() {
		return this.totalCountsOfRating;
	}

	public AverageRating calculateAverageRating(Score ratingToAdd) {
		if (isAverageRatingPresent()) {
			BigDecimal totalScoreAccumulated = calculateTotalRatingsAccumulated(ratingToAdd).totalRating();
			return AverageRating.of(totalScoreAccumulated,totalCountsOfRating, averageRating);
		}

		return AverageRating.of(BigDecimal.valueOf(ratingToAdd.rating()));
	}

	public TotalRatingAccumulated calculateTotalRatingsAccumulated(Score ratingToAdd) {
		return totalRatingAccumulated.addTotalRating(ratingToAdd);
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
		this.totalRatingAccumulated = calculateTotalRatingsAccumulated(score);
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
		
		private final String politicianNumber;

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

        public PoliticiansBuilder setPoliticiansRating(List<PoliticiansRating> politiciansRating) {
			if (politiciansRating == null) {
				this.politiciansRating = new ArrayList<>();
				return this;
			}
			this.politiciansRating = politiciansRating;
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
					.setAverageRating(averageRating)
					.setTotalRating(totalRating)
					.setPoliticiansRating(politiciansRating)
					.setRatingRepository(ratingRepo);
			return builder;
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

			return new Politicians(name, politiciansRating, averageRating, totalRatingAccumulated, new PoliticianNumber(politicianNumber), null);
		}
	}
	
}