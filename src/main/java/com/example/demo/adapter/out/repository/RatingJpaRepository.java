package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.out.jpa.PoliticiansRatingJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingJpaRepository extends JpaRepository<PoliticiansRatingJpaEntity, Integer> {

    List<PoliticiansRatingJpaEntity> findByRater_Email(String email);

    long countByPolitician_Id(Integer id);

    List<PoliticiansRatingJpaEntity> findByRater_UserAccountNumber(String accountNumber);

    void deleteByRater_UserAccountNumber(String accountNumber);

    boolean existsByRater_UserAccountNumber(String accountNumber);

    @Query(value = "SELECT AVG(rating) FROM rating_entity r WHERE r.politician_id =  :politicianNumber", nativeQuery = true)
    double calculateRating(String politicianNumber);

}
