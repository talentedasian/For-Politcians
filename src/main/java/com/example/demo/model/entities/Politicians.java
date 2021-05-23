package com.example.demo.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.PoliticianMethods;
import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.averageCalculator.DecentSatisfactionAverageCalculator;
import com.example.demo.model.averageCalculator.HighSatisfactionAverageCalculator;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.repository.RatingRepository;

@Entity
public class Politicians implements PoliticianMethods{

	@Autowired transient RatingRepository repo;
	private transient  AverageCalculator averageCalculator;
	
	public AverageCalculator getAverageCalculator() {
		return averageCalculator;
	}
	
	public void setAverageCalculator(AverageCalculator averageCalculator) {
		this.averageCalculator = averageCalculator;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, precision = 3, scale = 2)
	private Double rating;
	
	@Column(nullable = false, name = "politician_first_name")
	private String firstName;
	
	@Column(nullable = false, name = "politician_last_name")
	private String lastName;
	
	@Column(nullable = false, unique = true, name = "full_name")
	private String fullName;
	
	@OneToMany(mappedBy = "politician")
	private List<PoliticiansRating> politiciansRating;
	
	@Column(nullable = false, precision = 3, scale = 2)
	private Double totalRating;

	public void setRepo(RatingRepository repo) {
		this.repo = repo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

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
		this.totalRating = BigDecimal.valueOf(totalRating)
				.setScale(2, RoundingMode.HALF_DOWN).doubleValue();
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Politicians() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Politicians(Integer id, Double rating, String firstName, String lastName,
			List<PoliticiansRating> politiciansRating, Double totalRating) {
		super();
		this.id = id;
		this.rating = rating;
		this.firstName = firstName;
		this.lastName = lastName;
		this.politiciansRating = politiciansRating;
		this.totalRating = totalRating;
		
	}
	
	public Politicians(Integer id, Double rating, String firstName, String lastName,
			List<PoliticiansRating> politiciansRating, Double totalRating, RatingRepository repo) {
		super();
		this.id = id;
		this.rating = rating;
		this.firstName = firstName;
		this.lastName = lastName;
		this.politiciansRating = politiciansRating;
		this.totalRating = totalRating;
		this.repo = repo;
	}

	@Override
	public String toString() {
		return "Politicians [id=" + id + ", rating=" + rating + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", politiciansRating=" + politiciansRating + ", totalRating=" + totalRating + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
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

	@Override
	public double calculateAverageRating() {
		double rating = returnAverageCalculator().calculateAverage();
		setRating(rating);
		
		return rating;
	}
	
	private Double convertLongToDouble(long longValue) {
		return Double.valueOf(String.valueOf(longValue));
	}

	@Override
	public double calculateTotalAmountOfRating(Double rating) {
		if (getTotalRating() == null) {
			setTotalRating(BigDecimal.valueOf(rating)
			.setScale(2, RoundingMode.HALF_DOWN).doubleValue());
		}
		
		double totalAmount = BigDecimal.valueOf(getTotalRating() + rating)
			.setScale(2, RoundingMode.HALF_DOWN).doubleValue();
		setTotalRating(totalAmount);
		
		return totalAmount;
	}

	@Override
	public long returnCountsOfRatings() {
		if (isEmptyCountOfRatings()) {
			return 0;
		}
		
		return returnCountsOfRatings(this.id);
	}

	@Override
	public boolean isEmptyCountOfRatings() {
		return returnCountsOfRatings(this.id) == 0L;
	}

	@Override
	public List<PoliticiansRating> calculateListOfRaters(PoliticiansRating rater) {
		List<PoliticiansRating> listOfPoliticiansRating = getPoliticiansRating();
		listOfPoliticiansRating.add(rater);
		setPoliticiansRating(listOfPoliticiansRating);
		
		return listOfPoliticiansRating;
	}
	
	private long returnCountsOfRatings(Integer id) {
		return repo.countByPolitician_Id(id);
	}

	@Override
	public String calculateFullName() {
		String fullName = this.firstName + "\s" + this.lastName;
		setFullName(fullName);
		
		return fullName;
	}
	
	private AverageCalculator returnAverageCalculator() {
		if (this.rating < 5D) {
			return new LowSatisfactionAverageCalculator(getTotalRating(), convertLongToDouble(returnCountsOfRatings()));
		} else if (this.rating < 8.89D) {
			return new DecentSatisfactionAverageCalculator(getTotalRating(), convertLongToDouble(returnCountsOfRatings()));
		} else if (this.rating >= 8.89D) {
			return new HighSatisfactionAverageCalculator(getTotalRating(), convertLongToDouble(returnCountsOfRatings()));
		}
		
		return null;
	}
	
}
