package com.example.demo.model.entities.politicians;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.model.politicianNumber.PoliticianNumberImplementor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

public class PoliticianTypes {
	
	@Entity
	@DiscriminatorValue("Presidential")
	public static class PresidentialPolitician extends Politicians{
		
		@Column(nullable = true, name = "law_signed")
		private String mostSignificantLawSigned;

		protected PresidentialPolitician(Politicians politician, String lawSigned) {
			super(politician.getRepo(), politician.getId(), 
					politician.getFirstName(), politician.getLastName(),
					politician.getFullName(), politician.getPoliticiansRating(), 
					politician.getRating(), politician.getPoliticianNumber(),
					Type.PRESIDENTIAL);
			this.mostSignificantLawSigned = lawSigned;
		}

		protected PresidentialPolitician() {}

		public String getMostSignificantLawSigned() {
			return mostSignificantLawSigned;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((getFirstName() == null) ? 0 : getFirstName().hashCode());
			result = prime * result + ((getLastName() == null) ? 0 : getLastName().hashCode());
			result = prime * result + Type.PRESIDENTIAL.hashCode();
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) 
				return true;
			if (getClass() != obj.getClass())
				return false;
			PresidentialPolitician other = (PresidentialPolitician) obj;
			if (other.getType() == null) {
				return false;
			} else {
				if (!other.getType().equals(Type.PRESIDENTIAL)) {	
					return false;
				}
			}
			if (other.getPoliticianNumber() == null) {
				return false;
			} else {
				if (!other.getPoliticianNumber().equals(this.getPoliticianNumber()))
					return false;
			}
			return true;
		}

		public static class PresidentialBuilder{
			
			private Politicians politician;
			
			private String mostSignificantLawSigned;

			public PresidentialBuilder(Politicians politician) {
				this.politician = politician;
			}
			
			public PresidentialBuilder(PoliticiansBuilder politicianBuilder) {
				this.politician = politicianBuilder.build();
			}
			
			public PresidentialBuilder setMostSignificantLawPassed(String nameOfLaw) {
				this.mostSignificantLawSigned = nameOfLaw;
				return this;
			}
			
			public PresidentialBuilder setBuilder(PoliticiansBuilder builder) {
				return new PresidentialBuilder(builder)
						.setMostSignificantLawPassed(mostSignificantLawSigned);
			}

			public PresidentialPolitician build() {
				var entity = new PresidentialPolitician(politician, null);
				String politicianNumber = PoliticianNumberImplementor.with(entity).calculateEntityNumber().getPoliticianNumber();
				politician.setPoliticianNumber(politicianNumber);

				return new PresidentialPolitician(politician, mostSignificantLawSigned);
			}
			
		}
	}
	
	@Entity
	@DiscriminatorValue("Senatorial")
	public static class SenatorialPolitician extends Politicians{
		
		/*
		 * This column must not be null when constructing Senatorial politicians.
		 * This is only nullable in the database for JPA inheritance.
		 */
		@Column(nullable = true, name = "months_of_service")
		private int totalMonthsOfServiceAsSenator;
		
		@Column(nullable = true, name = "law_made")
		private String mostSignificantLawMade;

		public int getTotalMonthsOfServiceAsSenator() {
			return totalMonthsOfServiceAsSenator;
		}

		public String getMostSignificantLawMade() {
			return mostSignificantLawMade;
		}

		protected SenatorialPolitician(Politicians politician, int monthsOfService, String lawMade) {
			super(politician.getRepo(), politician.getId(),
					politician.getFirstName(), politician.getLastName(), 
					politician.getFullName(), politician.getPoliticiansRating(),
					politician.getRating(), politician.getPoliticianNumber(),
					Type.SENATORIAL);
			this.totalMonthsOfServiceAsSenator = monthsOfService;
			this.mostSignificantLawMade = lawMade;			 
		}

		protected SenatorialPolitician() {}

		@Override
		@ExcludeFromJacocoGeneratedCoverage
		public String toString() {
			return "SenatorialPolitician [totalMonthsOfServiceAsSenator=" + totalMonthsOfServiceAsSenator
					+ ", mostSignificantLawMade=" + mostSignificantLawMade + ", politicianNumber=" + getPoliticianNumber() + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((getFirstName() == null) ? 0 : getFirstName().hashCode());
			result = prime * result + ((getLastName() == null) ? 0 : getLastName().hashCode());
			result = prime * result + Type.SENATORIAL.hashCode();
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
			SenatorialPolitician other = (SenatorialPolitician) obj;
			if (other.getType() == null) {
				return false;
			} else {
				if (!other.getType().equals(Type.SENATORIAL)) {
					return false;
				}
			}
			if (other.getPoliticianNumber() == null) {
				return false;
			} else {
				if (!other.getPoliticianNumber().equals(this.getPoliticianNumber())) {
					return false;
				}
			}
			return true;
		}



		public static class SenatorialBuilder {
			
			private Politicians politician;
			
			private Integer totalMonthsOfServiceAsSenator;
			
			private String mostSignificantLawMade;
			
			private PoliticiansBuilder builder;
			
			public SenatorialBuilder(Politicians politician) {
				this.politician = politician;
			}
			
			public SenatorialBuilder(PoliticiansBuilder politicianBuilder) {
				this.politician = politicianBuilder.build();
			}
			
			public SenatorialBuilder setTotalMonthsOfService(int yearsOfService) {
				this.totalMonthsOfServiceAsSenator = yearsOfService;
				return this;
			}
			
			public SenatorialBuilder setMostSignificantLawMade(String lawMade) {
				this.mostSignificantLawMade = lawMade;
				return this;
			}
			
			public SenatorialBuilder setBuilder(PoliticiansBuilder builder) {
				return new SenatorialBuilder(builder)
						.setTotalMonthsOfService(totalMonthsOfServiceAsSenator)
						.setMostSignificantLawMade(mostSignificantLawMade);
			}

			public SenatorialPolitician build() {
				org.springframework.util.Assert.notNull(totalMonthsOfServiceAsSenator,
						"""
								years of service must not be null. if politician has no experience, the appropriate number
								of experience is 0
								 """);
				org.springframework.util.Assert.state(isPositive(totalMonthsOfServiceAsSenator),
						"months of experience must not be negative");

				var entity = new SenatorialPolitician(politician, 0, null);
				String politicianNumber = PoliticianNumberImplementor.with(entity).calculateEntityNumber().getPoliticianNumber();
				politician.setPoliticianNumber(politicianNumber);

				return new SenatorialPolitician(politician, totalMonthsOfServiceAsSenator, mostSignificantLawMade);
			}

			private boolean isPositive(int yearsOfService) {
				return !String.valueOf(yearsOfService).contains("-");
			}
			
		}

	}
	
}