package com.example.demo.model.entities;

import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.service.RateLimitingService;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
public class PoliticiansRating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, precision = 3, scale = 2)
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
		return "PoliticiansRating [id=" + id + ", rating=" + rating + ", rater=" + rater +
				", politician=" + politician + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (other.id == null) {
			return false;
		} else {
			if (!other.id.equals(id)) {
				return false;
			}
		}
		return true;
	}

	public void calculateRating(double rating) {
		double rate = BigDecimal.valueOf(rating).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
		setRating(rate);
	}
	
	public void calculatePolitician(Politicians politician) {
		double totalAmountOfRating = politician.calculateTotalAmountOfRating(getRating());
		double averageRating = politician.calculateAverageRating();
		politician.getRating().setAverageRating(averageRating);
		politician.getRating().setTotalRating(totalAmountOfRating);
	}

	public void calculateRater(String subject, String id, String politicalParty,
			String accountNumber, RateLimitingService service) {
		PoliticalParty party = PoliticalParty.mapToPoliticalParty(politicalParty);
		var userRater = new UserRater(id, party, subject, accountNumber, service);
		setRater(userRater);
	}
	
}
