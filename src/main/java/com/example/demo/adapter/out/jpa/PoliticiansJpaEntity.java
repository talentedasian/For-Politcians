package com.example.demo.adapter.out.jpa;

import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.politicians.Politicians;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(indexes = @Index(columnList = "id") )
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PoliticiansJpaEntity {

    @Id
    public String id;

    @Column(nullable = false, name = "politician_first_name")
    private String firstName;

    @Column(nullable = false, name = "politician_last_name")
    private String lastName;

    @Column(nullable = false, name = "politician_full_name")
    private String fullName;

    @Column(nullable = false)
    private RatingJpaEntity ratingJpaEntity;

    @OneToMany(mappedBy = "politician")
    private List<PoliticiansRatingJpaEntity> politiciansRating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String lastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String name() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<PoliticiansRatingJpaEntity> getPoliticiansRating() {
        return politiciansRating;
    }

    public void setPoliticiansRating(List<PoliticiansRatingJpaEntity> politiciansRating) {
        this.politiciansRating = politiciansRating;
    }

    public RatingJpaEntity getRatingJpaEntity() {
        return ratingJpaEntity;
    }

    public void setRatingJpaEntity(RatingJpaEntity ratingJpaEntity) {
        this.ratingJpaEntity = ratingJpaEntity;
    }

    public PoliticiansJpaEntity() {}

    public PoliticiansJpaEntity(String id, String firstName, String lastName, String fullName,
                RatingJpaEntity ratingJpaEntity, List<PoliticiansRatingJpaEntity> politiciansRating) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.ratingJpaEntity = ratingJpaEntity;
        this.politiciansRating = politiciansRating;
    }

    public static PoliticiansJpaEntity from(Politicians politician) {
        return new PoliticiansJpaEntity(politician.retrievePoliticianNumber(), politician.firstName(),
                politician.lastName(), politician.name(),
                RatingJpaEntity.from(politician.getRating()), fromPoliticiansRating(politician.getPoliticiansRating()));
    }

    private static List<PoliticiansRatingJpaEntity> fromPoliticiansRating(List<PoliticiansRating> entities) {
    return entities.stream()
                .map(entity -> new PoliticiansRatingJpaEntity(entity.getId(), entity.getRating(),
                        UserRaterJpaEntity.from(entity.getRater()), null))
                .toList();
    }

    private List<PoliticiansRating> toPoliticiansRating(List<PoliticiansRatingJpaEntity> entities) {
        return entities.stream()
                .map(entity -> new PoliticiansRating.Builder()
                        .setId(id)
                        .setRating(entity.getRating())
                        .setRepo(entity.toRating().getRateLimitRepo())
                        .setRater(entity.getRater().toUserRater())
                        .setPolitician(null)
                        .build())
                .toList();
    }


    public Politicians toPoliticians() {
        return new Politicians.PoliticiansBuilder(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setFullName()
                .setPoliticiansRating(toPoliticiansRating(politiciansRating))
                .setRating(ratingJpaEntity.toRating())
                .build();
    }
}
