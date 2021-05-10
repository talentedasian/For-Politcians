package com.example.demo.dto;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((satisfactionRate == null) ? 0 : satisfactionRate.hashCode());
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
		PoliticianDTO other = (PoliticianDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (satisfactionRate != other.satisfactionRate)
			return false;
		return true;
	}
	
}
