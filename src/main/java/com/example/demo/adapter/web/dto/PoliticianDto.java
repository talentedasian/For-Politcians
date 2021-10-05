package com.example.demo.adapter.web.dto;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.enums.Rating;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Objects;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PoliticianDto {

	private final String name, id;
	
	private final String rating;
	
	private final Rating satisfactionRate;

	private final String type;
	
	public Rating getSatisfactionRate() {
		return satisfactionRate;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getRating() {
		return rating;
	}

	public String getType() {
		return type;
	}

	public PoliticianDto(String name, String id, String rating, Rating satisfactionRate, String type) {
		this.name = name;
		this.id = id;
		this.rating = rating;
		this.satisfactionRate = satisfactionRate;
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PoliticianDto that = (PoliticianDto) o;

		if (!Objects.equals(name, that.name)) return false;
		if (!Objects.equals(id, that.id)) return false;
		if (!Objects.equals(rating, that.rating)) return false;
		if (satisfactionRate != that.satisfactionRate) return false;
		return Objects.equals(type, that.type);
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (id != null ? id.hashCode() : 0);
		result = 31 * result + (rating != null ? rating.hashCode() : 0);
		result = 31 * result + (satisfactionRate != null ? satisfactionRate.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		return result;
	}

	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "PoliticianDto [fullName=" + name + ", id=" + id + ", rating=" + rating + ", type" + type + "]";
	}

}
