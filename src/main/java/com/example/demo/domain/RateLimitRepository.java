package com.example.demo.domain;

import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.RateLimit;

import java.util.List;
import java.util.Optional;

public interface RateLimitRepository {

	RateLimit save(RateLimit rateLimit);

	Optional<RateLimit> findUsingIdAndPoliticianNumber(String id, PoliticianNumber politicianNumber);

	void deleteUsingIdAndPoliticianNumber(String id, PoliticianNumber politicianNumber);

	long countUsingIdAndPoliticianNumber(String id, PoliticianNumber polNumber);

	List<RateLimit> findUsingId(String id);

    void saveAll(List<RateLimit> rateLimits);
}
