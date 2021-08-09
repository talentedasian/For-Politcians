package com.example.demo.adapter.out.repository;

import com.example.demo.domain.entities.RateLimit;

import java.util.Optional;

public interface RateLimitRepository {

	Optional<RateLimit> findByIdAndPoliticianNumber(String id, String politicianNumber);
	
	void deleteByIdAndPoliticianNumber(String id, String politicianNumber);

	long countByIdAndPoliticianNumber(String id, String polNumber);	
}
