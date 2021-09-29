package com.example.demo.adapter.out.jpa;

import com.example.demo.domain.Score;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.entities.PoliticiansRating;

import javax.persistence.*;

@Entity
public class PoliticiansRatingJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, precision = 3, scale = 2)
    private double rating;

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
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

    PoliticiansRatingJpaEntity(Integer id, Double rating, UserRaterJpaEntity rater, PoliticiansJpaEntity politician) {
        this.id = id;
        this.rating = rating;
        this.rater = rater;
        this.politician = politician;
    }

    PoliticiansRatingJpaEntity() {}

    public static PoliticiansRatingJpaEntity from(PoliticiansRating politicianRating) {
        Politicians politicianFromRating = politicianRating.whoWasRated();
        var politician = politicianFromRating == null ? null : fromPoliticians(politicianFromRating);
        var entity = new PoliticiansRatingJpaEntity();
        entity.setId(politicianRating.id());
        entity.setRating(politicianRating.score());
        entity.setPolitician(politician);
        entity.setRater(UserRaterJpaEntity.from(politicianRating.whoRated()));

        return entity;
    }

    public PoliticiansRating toRatingNullPolitician() {
        return rater == null ? null
                : new PoliticiansRating.Builder()
                .setId(String.valueOf(id))
                .setRating(Score.of(rating))
                .setRater(rater.toUserRater())
                .setPolitician(null)
                .build();
    }

    public PoliticiansRating toRating() {
        return new PoliticiansRating.Builder()
                .setId(String.valueOf(id))
                .setRating(Score.of(rating))
                .setRater(rater.toUserRater())
                .setPolitician(politician.toPoliticians())
                .build();
    }

    private static PoliticiansJpaEntity fromPoliticians(Politicians politician) {
        return PoliticiansJpaEntity.fromWithNullRating(politician);
    }
//
//    private static Politicians toPoliticians(PoliticiansJpaEntity jpaEntity) {
//        return new Politicians.PoliticiansBuilder(PoliticianNumber.of(jpaEntity.getId()))
//                .setFirstName(jpaEntity.getFirstName())
//                .setLastName(jpaEntity.getLastName())
//                .setFullName()
//                .setRating(jpaEntity.getRatingJpaEntity().toRating())
//                .setPoliticiansRating(null)
//                .build();
//    }

}
