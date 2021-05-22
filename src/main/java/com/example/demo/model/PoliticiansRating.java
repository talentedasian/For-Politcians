package com.example.demo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.demo.model.enums.PoliticalParty;

@Entity
public class PoliticiansRating implements PoliticiansRatingMethods{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((politician == null) ? 0 : politician.hashCode());
		result = prime * result + ((rater == null) ? 0 : rater.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
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
		PoliticiansRating other = (PoliticiansRating) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (politician == null) {
			if (other.politician != null)
				return false;
		} else if (!politician.equals(other.politician))
			return false;
		if (rater == null) {
			if (other.rater != null)
				return false;
		} else if (!rater.equals(other.rater))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		return true;
	}

	@Override
	public double calculateRating(double rating) {
		double rate = BigDecimal.valueOf(rating).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
		setRating(rate);
		
		return rate;
	}

	@Override
	public Politicians calculatePolitician(Politicians politician) {
		politician.calculateFullName();
		politician.calculateTotalAmountOfRating(getRating());
		politician.calculateAverageRating();
		setPolitician(politician);
		
		return politician;
	}

	@Override
	public UserRater calculateRater(String subject, String id, PoliticalParty politicalParty) {
		var userRater = new UserRater(id, politicalParty, subject);
		setRater(userRater);
		
		return userRater;
	}
	
}
