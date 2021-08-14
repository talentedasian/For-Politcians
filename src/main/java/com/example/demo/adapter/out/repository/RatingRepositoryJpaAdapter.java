package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.out.jpa.PoliticiansRatingJpaEntity;
import com.example.demo.domain.entities.PoliticiansRating;

import java.util.List;

public class RatingRepositoryJpaAdapter implements RatingRepository {

    private final RatingJpaRepository repo;

    public RatingRepositoryJpaAdapter(RatingJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public PoliticiansRating save(PoliticiansRating rating) {
        PoliticiansRatingJpaEntity entity = repo.save(PoliticiansRatingJpaEntity.from(rating));

        return entity.toRating();
    }

    @Override
    public List<PoliticiansRating> findByRater_Email(String email) {
        return repo.findByRater_Email(email).stream()
                .map(PoliticiansRatingJpaEntity::toRating)
                .toList();
    }

    @Override
    public long countByPolitician_Id(Integer id) {
        return repo.countByPolitician_Id(id);
    }

    @Override
    public List<PoliticiansRating> findByRater_UserAccountNumber(String accountNumber) {
        return repo.findByRater_UserAccountNumber(accountNumber).stream()
                .map(PoliticiansRatingJpaEntity::toRating)
                .toList();
    }

    @Override
    public void deleteByRater_UserAccountNumber(String accountNumber) {
        repo.deleteByRater_UserAccountNumber(accountNumber);
    }

    @Override
    public boolean existsByRater_UserAccountNumber(String accountNumber) {
        return repo.existsByRater_UserAccountNumber(accountNumber);
    }
}
