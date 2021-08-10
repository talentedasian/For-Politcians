package com.example.demo.domain;

import com.example.demo.domain.entities.RateLimit;

import java.util.Optional;

public interface RateLimitRepository {

	RateLimit save(RateLimit rateLimit);

	Optional<RateLimit> findUsingIdAndPoliticianNumber(String id, String politicianNumber);

	void deleteUsingIdAndPoliticianNumber(String id, String politicianNumber);

	long countUsingIdAndPoliticianNumber(String id, String polNumber);
}
