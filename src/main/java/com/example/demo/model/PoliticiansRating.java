package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.model.enums.Rating;

@Entity
public class PoliticiansRating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

//	private Rating rating;
	
	@Column(nullable = false, precision = 3, scale = 2)
	private Float rating; 
	
	@Column(nullable = false, name = "user")
	private String username;
	
	@Enumerated(EnumType.STRING)
	private PoliticalParty politicalParties;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public PoliticalParty getPoliticalParties() {
		return politicalParties;
	}

	public void setPoliticalParties(PoliticalParty politicalParties) {
		this.politicalParties = politicalParties;
	}

	public PoliticiansRating() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PoliticiansRating(Integer id, Float rating, String username, PoliticalParty politicalParties) {
		super();
		this.id = id;
		this.rating = rating;
		this.username = username;
		this.politicalParties = politicalParties;
	}

	@Override
	public String toString() {
		return "PoliticiansRating [id=" + id + ", rating=" + rating + ", username=" + username + ", politicalParties="
				+ politicalParties + "]";
	}
	
}
