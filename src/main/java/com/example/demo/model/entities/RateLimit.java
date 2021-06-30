package com.example.demo.model.entities;

import static java.lang.Integer.valueOf;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RateLimit {
	
	@Id
	private String id;
	
	@Column(nullable = false, name = "politician_number")
	private String politicianNumber;
	
	@Column(nullable = true, name = "date_created")
	private LocalDate dateCreated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPoliticianNumber() {
		return politicianNumber;
	}

	public void setPoliticianNumber(String politicianNumber) {
		this.politicianNumber = politicianNumber;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public RateLimit(String id, String politicianNumber, LocalDate created) {
		super();
		this.id = id;
		this.politicianNumber = politicianNumber;
		dateCreated = created;
	}

	public RateLimit() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "RateLimit [id=" + id + ", politicianNumber=" + politicianNumber + ", dateCreated=" + dateCreated + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((politicianNumber == null) ? 0 : politicianNumber.hashCode());
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
		RateLimit other = (RateLimit) obj;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (politicianNumber == null) {
			if (other.politicianNumber != null)
				return false;
		} else if (!politicianNumber.equals(other.politicianNumber))
			return false;
		return true;
	}
	
	public boolean isNotRateLimited() {
		return this.dateCreated != null && (LocalDate.now().minusDays(7L).isAfter(dateCreated) | 
				LocalDate.now().minusDays(7L).isEqual(dateCreated));
	}
	
	// return null if user is not rate limited
	public Integer daysLeftOfBeingRateLimited() {
		if (isNotRateLimited()) {
			return null;
		}
		
		return valueOf(this.dateCreated.getDayOfMonth() - LocalDate.now().minusDays(7L).getDayOfMonth());
	}

}
