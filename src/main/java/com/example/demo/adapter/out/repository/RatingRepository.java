package com.example.demo.adapter.out.repository;

import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.entities.PoliticiansRating;

import java.util.List;
import java.util.Optional;

public interface RatingRepository{

	Optional<PoliticiansRating> findById(String id);

	PoliticiansRating save(PoliticiansRating rating);
	
	List<PoliticiansRating> findByRater_Email(String email);

	long countByPolitician_Id(Integer id);
	
	List<PoliticiansRating> findByRater_UserAccountNumber(String accountNumber);
	
	void deleteByRater_UserAccountNumber(String accountNumber);
	
	boolean existsByRater_UserAccountNumber(String accountNumber);

	void deleteById(Integer id);
	
    List<Politicians> findPoliticiansByAccNumber(AccountNumber accNumber, int page);
}
