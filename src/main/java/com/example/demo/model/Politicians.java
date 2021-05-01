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
	public Integer id;
	
//	public Rating rating;
	
	@Column(nullable = false, precision = 3, scale = 2)
	public Double rating;
	
	@Column(nullable = false, name = "politician_name")
	public String name;
	
	@OneToMany(mappedBy = "politician")
	List<PoliticiansRating> politiciansRating;
	
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

	public Politicians(Double rating, String name) {
		super();
		this.rating = rating;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Politicians [id=" + id + ", rating=" + rating + ", name=" + name + "]";
	}
	
}
