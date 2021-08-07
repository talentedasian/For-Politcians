package com.example.demo.model.entities.politicians;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.model.PoliticianMethods;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.entities.Rating;
import com.example.demo.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(indexes = @Index(columnList = "politicianNumber") )
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Politicians.Type type;

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
	
	public Politicians.Type getType() {
		return type;
	}

	public void setType(Politicians.Type type) {
		this.type = type;
	}

	protected Politicians() {
	}

	protected Politicians(RatingRepository repo, Integer id, String firstName, String lastName, String fullName,
			List<PoliticiansRating> politiciansRating, Rating rating, String politicianNumber, Type polType) {
		super();
		this.repo = repo;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.politiciansRating = politiciansRating;
		this.rating = rating;
		this.politicianNumber = politicianNumber;
		this.type = polType;
	}

	@Override
	@ExcludeFromJacocoGeneratedCoverage
	public String toString() {
		return "Politicians [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", fullName="
				+ fullName +  ", rating=" + rating + ", politicianNumber=" + politicianNumber + "]";
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
	
	public static enum Type {
		PRESIDENTIAL, SENATORIAL, MAYOR;

		static Map<String, Type> cache = new HashMap<>();

		static {
			cache.put("presidential", PRESIDENTIAL);
			cache.put("senatorial", SENATORIAL);
			cache.put("mayorial", MAYOR);
		}


        public static Type mapToPoliticianType(String type) {
			if (!cache.containsKey(type)) {
				throw new IllegalArgumentException("type is non existing");
			}

			return cache.get(type);
        }
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
			this.politicianNumber = politicianNumber;
		}

		public PoliticiansBuilder() {}

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
			return new Politicians(ratingRepo, id, firstName, lastName, fullName, politiciansRating, rating, politicianNumber, null);
		}
		
	}
	
}