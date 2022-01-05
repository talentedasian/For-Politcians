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

    @Query(value = "SELECT AVG(rating) FROM rating r WHERE r.politician_id =  :politicianNumber", nativeQuery = true)
    double calculateRating(String politicianNumber);

    @Query(value = """
            SELECT rating_entity.id, rating_entity.politician_id, rating_entity.email, rating_entity.account_number,
            rating_entity.party, rating_entity.rating, rating_entity.name, politicians.id,
            politicians.dtype, politicians.first_name, politicians.last_name,
            politicians.full_name, politicians.average_rating, politicians.law_signed, politicians.law_made,
            politicians.months_of_service FROM rating_entity
            LEFT JOIN politicians ON politicians.id = 'rating_entity.politician_id'
            WHERE account_number = :accountNumber
            ORDER BY rating_entity.id LIMIT 20 OFFSET :offset
            """,
            nativeQuery = true)
    List<PoliticiansRatingJpaEntity> findByRater_UserAccountNumberByPage(String accountNumber, int offset);
}
