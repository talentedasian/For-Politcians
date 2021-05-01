package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.model.enums.Rating;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PoliticianDTO {

	private String name, id;
	
	private final Double rating;
	
	@JsonProperty(value = "satisfaction_rate")
	private Rating satisfactionRate;
	
	public Rating getSatisfactionRate() {
		return satisfactionRate;
	}

	public void setSatisfactionRate(Rating satisfactionRate) {
		this.satisfactionRate = satisfactionRate;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Double getRating() {
		return rating;
	}

	public PoliticianDTO(String name, String id, Double rating, Rating satisfactionRate) {
		super();
		this.name = name;
		this.id = id;
		this.rating = rating;
		this.satisfactionRate = satisfactionRate;
	}

	@Override
	public String toString() {
		return "PoliticianDTO [name=" + name + ", id=" + id + ", rating=" + rating + "]";
	}
	
}
