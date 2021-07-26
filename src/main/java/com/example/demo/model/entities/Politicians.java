package com.example.demo.model.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.model.PoliticianMethods;
import com.example.demo.repository.RatingRepository;

@Entity
@Table(indexes = @Index(columnList = "politicianNumber") )
public class Politicians implements PoliticianMethods{

	@Transient @Autowired private transient RatingRepository repo;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, name = "politician_first_name")
	private String firstName;
	
	@Column(nullable = false, name = "politician_last_name")
	private String lastName;
	
	@Column(nullable = false, name = "politician_full_name")
	private String fullName;
	
	@OneToMany(mappedBy = "politician")
	private List<PoliticiansRating> politiciansRating;

	@Column(nullable = false, precision = 3, scale = 2)
	private Rating rating;
	
	@Column(nullable = false, unique = true)
	private String politicianNumber;

	public String getPoliticianNumber() {
		return politicianNumber;
	}

	public void setPoliticianNumber(String politicianNumber) {
		this.politicianNumber = politicianNumber;
	}

	public RatingRepository getRepo() {
		return repo;
	}

	public void setRepo(RatingRepository repo) {
		this.repo = repo;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getFullName() {
		return this.fullName;
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

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}
	
	protected Politicians() {
	}

	public Politicians(RatingRepository repo, Integer id, String firstName, String lastName, String fullName,
			List<PoliticiansRating> politiciansRating, Rating rating, String politicianNumber) {
		super();
		this.repo = repo;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.politiciansRating = politiciansRating;
		this.rating = rating;
		this.politicianNumber = politicianNumber;
	}

	@Override
	public String toString() {
		return "Politicians [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", fullName="
				+ fullName +  ", rating=" + rating + "]";
	}

	@ExcludeFromJacocoGeneratedCoverage
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((politicianNumber == null) ? 0 : politicianNumber.hashCode());
		result = prime * result + ((politiciansRating == null) ? 0 : politiciansRating.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (Politicians.class.isInstance(obj.getClass())) {
			return false;
		}
		
		if (politicianNumber == null) {
			this.politicianNumber = "";
		}
		
		Politicians other = (Politicians) obj;
		
		if (other.politicianNumber == null) {
			other.politicianNumber = "";
		}
		
		return this == obj || politicianNumber.equals(other.politicianNumber);
	}

	@Override
	public double calculateAverageRating() {
		double rating = getRating().calculateAverage();
		
		return rating;
	}
	
	private Double convertLongToDouble(long longValue) {
		return Double.valueOf(String.valueOf(longValue));
	}

	@Override
	public double calculateTotalAmountOfRating(Double rating) {
		double totalRating = getRating().calculateTotalAmountOfRating(rating, convertLongToDouble(returnCountsOfRatings()));
		
		return totalRating;
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
		this.fullName = fullName;
		
		return fullName;
	}
	
	public static class PoliticiansBuilder {
		private RatingRepository ratingRepo;
		
		private Integer id;
		
		private String firstName;
		
		private String lastName;
		
		private String fullName;
		
		private List<PoliticiansRating> politiciansRating;

		private Rating rating;
		
		private String politicianNumber;

		public PoliticiansBuilder(String politicianNumber) {
			super();
			this.politicianNumber = politicianNumber;
		}

		public PoliticiansBuilder setId(Integer id) {
			this.id = id;
			return this;
		}

		public PoliticiansBuilder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public PoliticiansBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public PoliticiansBuilder setFullName() {
			if (firstName == null && lastName == null) {
				throw new IllegalArgumentException("First and Last name cannot be null");
			}
			
			if (lastName == null) {
				this.fullName = firstName;
				return this;
			}
			
			this.fullName = firstName + " " + lastName;
			return this;
		}

		public PoliticiansBuilder setPoliticiansRating(List<PoliticiansRating> politiciansRating) {
			this.politiciansRating = politiciansRating;
			return this;
		}

		public PoliticiansBuilder setRating(Rating rating) {
			this.rating = rating;
			return this;
		}

		/*
		 * Politician number should not change in an object so this
		 * method returns a new Builder with the politicianNumber
		 */
		public PoliticiansBuilder setPoliticianNumber(String politicianNumber) {
			var builder = new PoliticiansBuilder(politicianNumber)
				.setId(id)
				.setFirstName(firstName)
				.setLastName(lastName)
				.setRating(rating)
				.setPoliticiansRating(politiciansRating)
				.setRatingRepository(ratingRepo);
			if (firstName.isEmpty() || firstName == null) {
				return builder;
			}
			return builder.setFullName();
		}
		
		public PoliticiansBuilder setRatingRepository(RatingRepository ratingRepo) {
			this.ratingRepo = ratingRepo;
			return this;
		}
		
		public Politicians build() {
			return new Politicians(ratingRepo, id, firstName, lastName, fullName, politiciansRating, rating, politicianNumber);
		}
		
	}
	
}
