package com.example.demo.adapter.dto;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.enums.Rating;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PoliticianDto {

	private final String name, id;
	
	private final Double rating;
	
	@JsonProperty(value = "satisfaction_rate")
	private Rating satisfactionRate;

	private String type;
	
	public Rating getSatisfactionRate() {
		return satisfactionRate;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public Double getRating() {
		return rating;
	}

	public PoliticianDto(String name, String id, Double rating, Rating satisfactionRate, String type) {
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
