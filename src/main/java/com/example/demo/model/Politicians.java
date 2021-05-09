package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Politicians {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
//	private Rating rating;
	
	@Column(nullable = false, precision = 3, scale = 2)
	private Double rating;
	
	@Column(nullable = false, unique = true, name = "politician_name")
	private String name;
	
	@OneToMany(mappedBy = "politician")
	List<PoliticiansRating> politiciansRating;
	
	@Column(nullable = false, precision = 3, scale = 2)
	private Double totalRating;
	
	public List<PoliticiansRating> getPoliticiansRating() {
		return politiciansRating;
	}

	public void setPoliticiansRating(List<PoliticiansRating> politiciansRating) {
		this.politiciansRating = politiciansRating;
	}

	public Double getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(Double totalRating) {
		this.totalRating = totalRating;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Politicians() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Politicians(Integer id, Double rating, String name, List<PoliticiansRating> politiciansRating,
			Double totalRating) {
		super();
		this.id = id;
		this.rating = rating;
		this.name = name;
		this.politiciansRating = politiciansRating;
		this.totalRating = totalRating;
	}

	@Override
	public String toString() {
		return "Politicians [id=" + id + ", rating=" + rating + ", name=" + name + ", totalRating=" + totalRating + "]";
	}

}
