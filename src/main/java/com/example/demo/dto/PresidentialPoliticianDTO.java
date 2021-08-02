package com.example.demo.dto;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.enums.Rating;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public final class PresidentialPoliticianDTO extends PoliticianDTO {

	private final String mostSignificantLawSigned;
	
	public String getMostSignificantLawSigned() {
		return mostSignificantLawSigned;
	}

	public PresidentialPoliticianDTO(Politicians entity, Rating satisfactionRate,String lawSigned) {
		super(entity.getFullName(), entity.getPoliticianNumber(), entity.getRating().getAverageRating(), satisfactionRate);
		this.mostSignificantLawSigned = lawSigned;
	}

	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "PresidentialPoliticianDTO [name=" + this.getName() + ", id=" + this.getId() + 
				", rating=" + this.getRating() + ", satisfactionRate=" + this.getSatisfactionRate() +   
				", mostSignificantLawSigned=" + this.mostSignificantLawSigned + "]";
	}

	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		result = prime * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
		result = prime * result + ((this.getRating() == null) ? 0 : this.getRating().hashCode());
		result = prime * result + ((this.getSatisfactionRate() == null) ? 0 : this.getSatisfactionRate().hashCode());
		result = prime * result + ((mostSignificantLawSigned == null) ? 0 : mostSignificantLawSigned.hashCode()); 
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
		PresidentialPoliticianDTO other = (PresidentialPoliticianDTO) obj;
		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;
		if (this.getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!this.getName().equals(other.getName()))
			return false;
		if (this.getRating() == null) {
			if (other.getRating() != null)
				return false;
		} else if (!this.getRating().equals(other.getRating()))
			return false;
		if (this.getSatisfactionRate() != other.getSatisfactionRate())
			return false;
		if (mostSignificantLawSigned == null) {
			if (other.mostSignificantLawSigned != null)
				return false;
		} else if (!mostSignificantLawSigned.equals(other.mostSignificantLawSigned)) {
			return false;
		}
		return true;
	}

}
