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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((politiciansRating == null) ? 0 : politiciansRating.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((totalRating == null) ? 0 : totalRating.hashCode());
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
		Politicians other = (Politicians) obj;
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
		if (politiciansRating == null) {
			if (other.politiciansRating != null)
				return false;
		} else if (!politiciansRating.equals(other.politiciansRating))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (totalRating == null) {
			if (other.totalRating != null)
				return false;
		} else if (!totalRating.equals(other.totalRating))
			return false;
		return true;
	}

}
