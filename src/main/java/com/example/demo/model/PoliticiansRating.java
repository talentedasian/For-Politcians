package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.model.enums.PoliticalParty;

@Entity
public class PoliticiansRating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

//	private Rating rating;
	
	@Column(nullable = false, precision = 3, scale = 2)
	private Double rating; 
	
	@Column(nullable = false, name = "user_name")
	private String username;
	
	@Enumerated(EnumType.STRING)
	private PoliticalParty politicalParties;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "politician_id")
	private Politicians politician;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
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

	public PoliticiansRating(Integer id, Double rating, String username, PoliticalParty politicalParties) {
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
