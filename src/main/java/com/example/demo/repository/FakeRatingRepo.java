package com.example.demo.repository;

import com.example.demo.model.entities.PoliticiansRating;

import java.util.List;

public class FakeRatingRepo extends AbstractFakeRatingRepository {
    @Override
    public List<PoliticiansRating> findByRater_Email(String email) {
        return null;
    }

    @Override
    public long countByPolitician_Id(Integer id) {
        return 0;
    }

    @Override
    public List<PoliticiansRating> findByRater_UserAccountNumber(String accountNumber) {
        return null;
    }

    @Override
    public void deleteByRater_UserAccountNumber(String accountNumber) {

    }

    @Override
    public boolean existsByRater_UserAccountNumber(String accountNumber) {
        return false;
    }

    @Override
    public List<PoliticiansRating> findAll() {
        return null;
    }

    @Override
    public void delete(PoliticiansRating entity) {

    }

    @Override
    public <S extends PoliticiansRating> S save(S entity) {
        return null;
    }

    @Override
    public <S extends PoliticiansRating> List<S> saveAll(Iterable<S> entities) {
        return null;
    }
}
