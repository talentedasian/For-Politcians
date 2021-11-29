package com.example.demo.adapter.web.dto;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.enums.Rating;

import java.util.Objects;

import static com.example.demo.dtomapper.PoliticiansDtoMapper.NO_RATING;

public final class PresidentialPoliticianDto extends PoliticianDto {

	private final String mostSignificantLawSigned;

	public String getMostSignificantLawSigned() {
		return mostSignificantLawSigned;
	}

	private static final String type = "PRESIDENTIAL";

	public PresidentialPoliticianDto(String name, String id, String rating, Rating satisfactionRate, String mostSignificantLawSigned) {
		super(name, id, rating, satisfactionRate, type);
		this.mostSignificantLawSigned = mostSignificantLawSigned;
	}

	public static PresidentialPoliticianDto of(Politicians entity, Rating satisfactionRate, String lawSigned) {
		String rating = entity.hasRating() ? entity.averageRating() : NO_RATING;
		return new PresidentialPoliticianDto(entity.fullName(), entity.retrievePoliticianNumber(), rating, satisfactionRate, lawSigned);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		PresidentialPoliticianDto that = (PresidentialPoliticianDto) o;

		return Objects.equals(mostSignificantLawSigned, that.mostSignificantLawSigned);
	}

	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (mostSignificantLawSigned != null ? mostSignificantLawSigned.hashCode() : 0);
		return result;
	}

	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "PresidentialPoliticianDTO [fullName=" + this.getName() + ", id=" + this.getId() +
				", rating=" + this.getRating() + ", satisfactionRate=" + this.getSatisfactionRate() +   
				", mostSignificantLawSigned=" + this.mostSignificantLawSigned + "]";
	}

}
