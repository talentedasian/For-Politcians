package com.example.demo.dto;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.model.enums.Rating;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PoliticianDTO {

	private final String name, id;
	
	private final Double rating;
	
	@JsonProperty(value = "satisfaction_rate")
	private Rating satisfactionRate;
	
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

	public PoliticianDTO(String name, String id, Double rating, Rating satisfactionRate) {
		this.name = name;
		this.id = id;
		this.rating = rating;
		this.satisfactionRate = satisfactionRate;
	}

	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "PoliticianDTO [name=" + name + ", id=" + id + ", rating=" + rating + "]";
	}
	
}
