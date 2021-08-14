package com.example.demo.adapter.out.jpa;

import com.example.demo.domain.entities.PoliticiansRating;
import com.example.demo.domain.politicians.Politicians;

import javax.persistence.*;

@Entity
public class PoliticiansRatingJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, precision = 3, scale = 2)
    private Double rating;

    @Column(nullable = false)
    private UserRaterJpaEntity rater;

    @ManyToOne
    @JoinColumn(nullable = false, name = "politician_id")
    private PoliticiansJpaEntity politician;

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

    public UserRaterJpaEntity getRater() {
        return rater;
    }

    public void setRater(UserRaterJpaEntity rater) {
        this.rater = rater;
    }

    public PoliticiansJpaEntity getPolitician() {
        return politician;
    }

    public void setPolitician(PoliticiansJpaEntity politician) {
        this.politician = politician;
    }

    public PoliticiansRatingJpaEntity(Integer id, Double rating, UserRaterJpaEntity rater, PoliticiansJpaEntity politician) {
        this.id = id;
        this.rating = rating;
        this.rater = rater;
        this.politician = politician;
    }

    public PoliticiansRatingJpaEntity() {}

    public static PoliticiansRatingJpaEntity from(PoliticiansRating politicianRating) {
        var entity = new PoliticiansRatingJpaEntity();
        entity.setId(politicianRating.getId());
        entity.setRating(politicianRating.getRating());
        entity.setPolitician(fromPoliticians(politicianRating.getPolitician()));
        entity.setRater(UserRaterJpaEntity.from(politicianRating.getRater()));

        return entity;
    }

    public PoliticiansRating toRating() {
        return new PoliticiansRating(id, rating, rater.toUserRater(), toPoliticians(politician));
    }


    private static PoliticiansJpaEntity fromPoliticians(Politicians politician) {
        return new PoliticiansJpaEntity
                (politician.getPoliticianNumber(), politician.getFirstName(),
                politician.getLastName(), politician.getFullName(),
                RatingJpaEntity.from(politician.getRating()), null);
    }

    private static Politicians toPoliticians(PoliticiansJpaEntity jpaEntity) {
        return new Politicians.PoliticiansBuilder(jpaEntity.getId())
                .setFirstName(jpaEntity.getFirstName())
                .setLastName(jpaEntity.getLastName())
                .setFullName()
                .setRating(jpaEntity.getRatingJpaEntity().toRating())
                .setPoliticiansRating(null)
                .build();
    }

}
