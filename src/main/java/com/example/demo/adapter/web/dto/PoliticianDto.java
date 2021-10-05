package com.example.demo.adapter.web.dto;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.enums.Rating;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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
	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "PoliticianDto [fullName=" + name + ", id=" + id + ", rating=" + rating + ", type" + type + "]";
	}

}
