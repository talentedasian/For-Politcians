package com.example.demo.domain.entities;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;

public class PoliticianTypes {

	public static class PresidentialPolitician extends Politicians{

		private final String mostSignificantLawSigned;

		protected PresidentialPolitician(Politicians politician, String lawSigned) {
			super(	politician.recordName(), politician.getPoliticiansRating(), politician.average(),
					politician.politicianNumber(),
					Type.PRESIDENTIAL);
			this.mostSignificantLawSigned = lawSigned;
		}

		public String getMostSignificantLawSigned() {
			return mostSignificantLawSigned;
		}

		@Override
		@ExcludeFromJacocoGeneratedCoverage
		public String toString() {
			return "Politicians [name=" + fullName() + ", rating=" + average() + ", politicianNumber=" + retrievePoliticianNumber()
					+ ", mostSignificantLawMSigned=" + mostSignificantLawSigned + ", type=" + Type.PRESIDENTIAL +  "]";
		}

		public static class PresidentialBuilder{
			
			private final Politicians politician;
			
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
				return new PresidentialPolitician(politician, mostSignificantLawSigned);
			}
			
		}
	}

	public static class SenatorialPolitician extends Politicians{

		private final int totalMonthsOfServiceAsSenator;

		private final String mostSignificantLawMade;

		public int getTotalMonthsOfServiceAsSenator() {
			return totalMonthsOfServiceAsSenator;
		}

		public String getMostSignificantLawMade() {
			return mostSignificantLawMade;
		}

		protected SenatorialPolitician(Politicians politician, int monthsOfService, String lawMade) {
			super(	politician.recordName(), politician.getPoliticiansRating(), politician.average(),
					politician.politicianNumber(),
					Type.SENATORIAL);
			this.totalMonthsOfServiceAsSenator = monthsOfService;
			this.mostSignificantLawMade = lawMade;			 
		}

		@Override
		@ExcludeFromJacocoGeneratedCoverage
		public String toString() {
			return "Politicians [name=" + fullName() + ", rating=" + (average() == null ? 0 : averageRating())
					+ ", politicianNumber=" + retrievePoliticianNumber()
					+ ", totalMonthsOfServiceAsSenator=" + totalMonthsOfServiceAsSenator +
					", mostSignificantLawMade=" + mostSignificantLawMade
					+ ", type=" + Type.SENATORIAL +  "]";
		}

		public static class SenatorialBuilder {
			
			private final Politicians politician;
			
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

				return new SenatorialPolitician(politician, totalMonthsOfServiceAsSenator, mostSignificantLawMade);
			}

			private boolean isPositive(int yearsOfService) {
				return !String.valueOf(yearsOfService).contains("-");
			}
			
		}

	}
	
}