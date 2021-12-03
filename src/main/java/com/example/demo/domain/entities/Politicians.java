package com.example.demo.domain.entities;

import com.example.demo.adapter.out.repository.RatingJpaRepository;
import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.AverageRating;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Politicians {

	private final Name name;

	private List<PoliticiansRating> politiciansRating = new ArrayList<>();

	private AverageRating averageRating;

	private final PoliticianNumber politicianNumber;

	private final Politicians.Type type;

	public String retrievePoliticianNumber() {
		return politicianNumber == null ? null : politicianNumber.politicianNumber();
	}

	public List<PoliticiansRating> getPoliticiansRating() {
		return List.copyOf(politiciansRating);
	}

	public void setPoliticiansRating(List<PoliticiansRating> politiciansRating) {
		this.politiciansRating = politiciansRating;
	}

	public String averageRating() {
		return averageRating.averageRating();
	}

	public boolean hasRating() {
		return AverageRating.hasRating(averageRating);
	}

	public AverageRating average() {
		return this.averageRating;
	}

	public Politicians.Type getType() {
		return type;
	}

	Politicians(Name name, List<PoliticiansRating> politiciansRating, AverageRating averageRating,
				PoliticianNumber politicianNumber, Type polType) {
		this.name = name;
		this.politiciansRating.addAll(politiciansRating == null ? List.of() : politiciansRating);
		this.averageRating = averageRating;
		this.politicianNumber = politicianNumber;
		this.type = polType;
	}

	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "Politicians{ " +
				"name= " + name +
				", rating= " + averageRating +
				", politicianNumber= " + politicianNumber +
				", type=" + type + " }";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Politicians that = (Politicians) o;

		if (!politicianNumber.equals(that.politicianNumber)) return false;
		return Objects.equals(averageRating, that.averageRating);
	}

	@Override
	public int hashCode() {
		int result = averageRating != null ? averageRating.hashCode() : 0;
		result = 31 * result + politicianNumber.hashCode();
		return result;
	}

	void rate(PoliticiansRating rating, RatingJpaRepository repo) {
		BigDecimal calculatedAverageRating = repo.calculateRating(politicianNumber.politicianNumber());
		changeAverageRating(AverageRating.of(calculatedAverageRating));

		politiciansRating.add(rating);
	}

	private void changeAverageRating(AverageRating averageRating) {
		this.averageRating = averageRating;
	}

	// INFO : DOES NOT CHANGE OVERALL BEHAVIOUR OF POLITICIAN. DELETING A RATING DOES NOT CHANGE THE TOTAL RATING AND THE AVERAGE RATING
	public void deleteRate(PoliticiansRating secondRating) {
		politiciansRating.remove(secondRating);
	}

	public Name recordName() {
		return this.name;
	}

	public String fullName() {
		return name == null ? null : name.fullName();
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

	public enum Type {
		PRESIDENTIAL("presidential, PRESIDENTIAL"), SENATORIAL("senatorial, SENATORIAL"),
		MAYOR("mayorial, MAYORIAL");

		Type(String s) {
		}
	}
	
	public static class PoliticiansBuilder {
		
		private Integer id;
		
		private String firstName;
		
		private String lastName;
		
		private List<PoliticiansRating> politiciansRating;
		
		private final String politicianNumber;

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
					.setPoliticiansRating(politiciansRating);
			return builder;
		}

		public PoliticiansBuilder setAverageRating(AverageRating averageRating) {
			this.averageRating = averageRating;
			return this;
		}

		public Politicians build() {
			var name = new Name(firstName, lastName);
			if (politiciansRating == null) politiciansRating = List.of();

			return new Politicians(name, politiciansRating, averageRating, new PoliticianNumber(politicianNumber), null);
		}
	}
	
}