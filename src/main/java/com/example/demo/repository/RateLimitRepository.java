package com.example.demo.repository;

import com.example.demo.model.entities.RateLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateLimitRepository extends JpaRepository<RateLimit, String>{

	Optional<RateLimit> findByIdAndPoliticianNumber(String id, String politicianNumber);
	
	void deleteByIdAndPoliticianNumber(String id, String politicianNumber);
	
	/*
	 *	Used for tests only! 
	 */
	long countByIdAndPoliticianNumber(String id, String polNumber);	
}
