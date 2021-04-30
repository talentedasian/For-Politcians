package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.demo.model.enums.Rating;

@Entity
public class Politicians {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;
	
//	public Rating rating;
	
	@Column(nullable = false, precision = 3, scale = 2)
	public Float rating;
	
	@Column(nullable = false, name = "politician_name")
	public String name;
	
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

	public Politicians(Integer id, Float rating, String name) {
		super();
		this.id = id;
		this.rating = rating;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Politicians [id=" + id + ", rating=" + rating + ", name=" + name + "]";
	}
	
}
