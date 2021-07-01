package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.entities.RateLimit;

public interface RateLimitRepository extends JpaRepository<RateLimit, String>{

	Optional<RateLimit> findByIdAndPoliticianNumber(String id, String politicianNumber);
	
	void deleteByIdAndPoliticianNumber(String id, String politicianNumber);
}
