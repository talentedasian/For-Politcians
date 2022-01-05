package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.out.jpa.PoliticiansJpaEntity;
import com.example.demo.adapter.out.jpa.PoliticiansRatingJpaEntity;
import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.entities.PoliticiansRating;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RatingRepositoryJpaAdapter implements RatingRepository {

    private final RatingJpaRepository repo;

    public RatingRepositoryJpaAdapter(RatingJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<PoliticiansRating> findById(String id) {
        Optional<PoliticiansRatingJpaEntity> entity = repo.findById(Integer.valueOf(id));

        return entity.isEmpty() ? Optional.empty() : Optional.of(entity.get().toRating());
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

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public List<Politicians> findPoliticiansByAccNumber(AccountNumber accNumber, int page) {
        int offset = ((page + 1) * 20);

        List<PoliticiansRatingJpaEntity> polQueried = repo.findByRater_UserAccountNumberByPage(accNumber.accountNumber(), offset);

        return polQueried
                .stream()
                .map(PoliticiansRatingJpaEntity::getPolitician)
                .map(PoliticiansJpaEntity::toPoliticians)
                .toList();
    }
}
