package com.example.demo.model.entities.politicians;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.model.entities.Politicians;

public class PoliticianTypes {
	
	@Entity
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
		
		public String getMostSignificantLawSigned() {
			return mostSignificantLawSigned;
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
				if (!other.getPoliticianNumber().equals(this.getPoliticianNumber())) {
					return false;
				}
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
			
			public PresidentialPolitician build() {
				return new PresidentialPolitician(politician, mostSignificantLawSigned);
			}
			
		}
	}
	
	@Entity
	public static class SenatorialPolitician extends Politicians{
		
		@Column(nullable = false, name = "years_of_service")
		private int totalYearsOfServiceAsSenator;
		
		@Column(nullable = true, name = "law_made")
		private String mostSignificantLawMade;

		public int getTotalYearsOfServiceAsSenator() {
			return totalYearsOfServiceAsSenator;
		}

		public String getMostSignificantLawMade() {
			return mostSignificantLawMade;
		}

		protected SenatorialPolitician(Politicians politician, int yearsOfService, String lawMade) {
			super(politician.getRepo(), politician.getId(),
					politician.getFirstName(), politician.getLastName(), 
					politician.getFullName(), politician.getPoliticiansRating(),
					politician.getRating(), politician.getPoliticianNumber(),
					Type.SENATORIAL);
			this.totalYearsOfServiceAsSenator = yearsOfService;
			this.mostSignificantLawMade = lawMade;			 
		}
		
		@Override
		@ExcludeFromJacocoGeneratedCoverage
		public String toString() {
			return "SenatorialPolitician [totalYearsOfServiceAsSenator=" + totalYearsOfServiceAsSenator
					+ ", mostSignificantLawMade=" + mostSignificantLawMade + "]";
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
			
			private int totalYearsOfServiceAsSenator;
			
			private String mostSignificantLawMade;
			
			public SenatorialBuilder(Politicians politician) {
				this.politician = politician;
			}
			
			public SenatorialBuilder(PoliticiansBuilder politicianBuilder) {
				this.politician = politicianBuilder.build();
			}
			
			public SenatorialBuilder setTotalYearsOfService(int yearsOfService) {
				this.totalYearsOfServiceAsSenator = yearsOfService;
				return this;
			}
			
			public SenatorialBuilder setTotalYearsOfService(String lawMade) {
				this.mostSignificantLawMade = lawMade;
				return this;
			}
			
			public SenatorialPolitician build() {
				return new SenatorialPolitician(politician, totalYearsOfServiceAsSenator, mostSignificantLawMade);
			}
			
		}

	}
	
}