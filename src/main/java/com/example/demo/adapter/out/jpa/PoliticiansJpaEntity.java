package com.example.demo.adapter.out.jpa;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.AverageRating;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.PoliticianTypes.PresidentialPolitician;
import com.example.demo.domain.entities.PoliticianTypes.SenatorialPolitician;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.entities.PoliticiansRating;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(indexes = @Index(columnList = "id"), name = "politicians")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PoliticiansJpaEntity {

    @Id
    @Column(unique = true)
    private String id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = true, name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Column(nullable = false, name = "rating")
    private BigDecimal averageRating;

    @Column(nullable = false, name = "total_count_of_rating")
    private int totalCountRating;

    @OneToMany(mappedBy = "politician", fetch = FetchType.LAZY)
    private List<PoliticiansRatingJpaEntity> politiciansRating;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String name() {
        return fullName;
    }

    public List<PoliticiansRatingJpaEntity> getPoliticiansRating() {
        return politiciansRating;
    }

    public int getTotalCountRating() {
        return totalCountRating;
    }

    PoliticiansJpaEntity() {}

    PoliticiansJpaEntity(String id, String firstName, String lastName, String fullName,
                            String rating, int totalCountRating, List<PoliticiansRatingJpaEntity> politiciansRating) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.averageRating = new BigDecimal(rating);
        this.totalCountRating = totalCountRating;
        this.politiciansRating = politiciansRating;
    }

    public static PoliticiansJpaEntity from(Politicians politician) {
        AverageRating averageRating = politician.average();
        var jpaEntity = new PoliticiansJpaEntity(politician.retrievePoliticianNumber(), politician.firstName(),
                politician.lastName(), politician.fullName(), politician.averageRating(),
                politician.totalCountsOfRatings(), fromPoliticiansRating(politician.ratings()));

        switch (politician.getType()) {
            case PRESIDENTIAL -> {
                var presidential = (PresidentialPolitician) politician;
                return new PresidentialJpaEntity(jpaEntity, presidential.getMostSignificantLawSigned());
            }
            case SENATORIAL -> {
                var senatorial = (SenatorialPolitician) politician;
                return new SenatorialJpaEntity(jpaEntity, senatorial.getMostSignificantLawMade(), senatorial.getTotalMonthsOfServiceAsSenator());
            }
            default -> {return jpaEntity;}
//            default -> throw new IllegalStateException("Unexpected value: " + politician.getType());
        }
    }

    public static PoliticiansJpaEntity fromWithNullRating(Politicians politician) {
        AverageRating averageRating = politician.average();
        var jpaEntity = new PoliticiansJpaEntity(politician.retrievePoliticianNumber(), politician.firstName(),
                politician.lastName(), politician.fullName(), politician.averageRating(),
                politician.totalCountsOfRatings(), null);
        if (politician.getType() == null) {
            return jpaEntity;
        }

        switch (politician.getType()) {
            case PRESIDENTIAL -> {
                var presidential = (PresidentialPolitician) politician;
                return new PresidentialJpaEntity(jpaEntity, presidential.getMostSignificantLawSigned());
            }
            case SENATORIAL -> {
                var senatorial = (SenatorialPolitician) politician;
                return new SenatorialJpaEntity(jpaEntity, senatorial.getMostSignificantLawMade(), senatorial.getTotalMonthsOfServiceAsSenator());
            }
            default -> throw new IllegalStateException("Unexpected value: " + politician.getType());
        }
    }

    @Override
    @ExcludeFromJacocoGeneratedCoverage
    public String toString() {
        return "PoliticiansJpaEntity{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", rating='" + averageRating.toString() +
                ", politiciansRating=" + politiciansRating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoliticiansJpaEntity that = (PoliticiansJpaEntity) o;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(fullName, that.fullName)) return false;
        if (averageRating.compareTo(that.averageRating) != 0) return false;
        return politiciansRating.equals(that.politiciansRating);
    }


    private static List<PoliticiansRatingJpaEntity> fromPoliticiansRating(List<PoliticiansRating> entities) {
    return entities.stream()
                .map(PoliticiansRatingJpaEntity::from)
                .toList();
    }

    private List<PoliticiansRating> toPoliticiansRating(List<PoliticiansRatingJpaEntity> entities) {
//        if(entities == null) return List.of();
//        List<PoliticiansRating> result = entities.stream()
//                .map(entity -> entity.toRatingNullPolitician())
//                .toList();
//        return result.contains(null) ? List.of() : result;
        return List.of();
    }

    public Politicians toPoliticians() {
        return new Politicians.PoliticiansBuilder(new PoliticianNumber(id))
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPoliticiansRating(toPoliticiansRating(politiciansRating))
                .setAverageRating(new AverageRating(averageRating))
                .setTotalCount(totalCountRating)
                .build();
    }

    protected String getRating() {
        return averageRating.toString();
    }
}
