package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entities.PoliticiansRating;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<PoliticiansRating, Integer>{
	
	List<PoliticiansRating> findByRater_Email(String email);

	long countByPolitician_Id(Integer id);
	
	List<PoliticiansRating> findByRater_UserAccountNumber(String accountNumber);
	
	void deleteByRater_UserAccountNumber(String accountNumber);
	
	boolean existsByRater_UserAccountNumber(String accountNumber);

}
