package com.example.demo.domain.entities;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;

public class PoliticianTypes {

	public static class PresidentialPolitician extends Politicians{

		private String mostSignificantLawSigned;

		protected PresidentialPolitician(Politicians politician, String lawSigned) {
			super(	politician.recordName(), politician.getPoliticiansRating(),
					politician.getRating(), politician.getPoliticianNumber(),
					Type.PRESIDENTIAL);
			this.mostSignificantLawSigned = lawSigned;
		}

		public String getMostSignificantLawSigned() {
			return mostSignificantLawSigned;
		}

		@Override
		@ExcludeFromJacocoGeneratedCoverage
		public String toString() {
			return "Politicians [name=" + fullName() + ", rating=" + averageRating() + ", politicianNumber=" + retrievePoliticianNumber()
					+ ", mostSignificantLawMSigned=" + mostSignificantLawSigned + ", type=" + Type.PRESIDENTIAL.toString() +  "]";
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
				return new PresidentialPolitician(politician, mostSignificantLawSigned);
			}
			
		}
	}

	public static class SenatorialPolitician extends Politicians{

		private int totalMonthsOfServiceAsSenator;

		private String mostSignificantLawMade;

		public int getTotalMonthsOfServiceAsSenator() {
			return totalMonthsOfServiceAsSenator;
		}

		public String getMostSignificantLawMade() {
			return mostSignificantLawMade;
		}

		protected SenatorialPolitician(Politicians politician, int monthsOfService, String lawMade) {
			super(	politician.recordName(), politician.getPoliticiansRating(),
					politician.getRating(), new PoliticianNumber(politician.retrievePoliticianNumber()),
					Type.SENATORIAL);
			this.totalMonthsOfServiceAsSenator = monthsOfService;
			this.mostSignificantLawMade = lawMade;			 
		}

		@Override
		@ExcludeFromJacocoGeneratedCoverage
		public String toString() {
			return "Politicians [name=" + fullName() + ", rating=" + averageRating() + ", politicianNumber=" + retrievePoliticianNumber()
					+ ", totalMonthsOfServiceAsSenator=" + totalMonthsOfServiceAsSenator + ", mostSignificantLawMade=" + mostSignificantLawMade
					+ ", type=" + Type.SENATORIAL.toString() +  "]";
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

				return new SenatorialPolitician(politician, totalMonthsOfServiceAsSenator, mostSignificantLawMade);
			}

			private boolean isPositive(int yearsOfService) {
				return !String.valueOf(yearsOfService).contains("-");
			}
			
		}

	}
	
}