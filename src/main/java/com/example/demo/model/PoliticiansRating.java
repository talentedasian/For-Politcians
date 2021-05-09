package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PoliticiansRating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

//	private Rating rating;
	
	@Column(nullable = false, precision = 1, scale = 2)
	private Double rating; 
	
	@Column(nullable = false)
	private UserRater rater;
	
	
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

	public UserRater getRater() {
		return rater;
	}

	public void setRater(UserRater rater) {
		this.rater = rater;
	}


	public Politicians getPolitician() {
		return politician;
	}

	public void setPolitician(Politicians politician) {
		this.politician = politician;
	}

	public PoliticiansRating() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PoliticiansRating(Integer id, Double rating, UserRater rater, Politicians politician) {
		super();
		this.id = id;
		this.rating = rating;
		this.rater = rater;
		this.politician = politician;
	}


	@Override
	public String toString() {
		return "PoliticiansRating [id=" + id + ", rating=" + rating + ", rater=" + rater + ", politician=" + politician
				+ "]";
	}
	
}
