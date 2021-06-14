package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.redis.RateLimiter;

public interface RateLimiterRepository extends CrudRepository<RateLimiter, String>{

	Optional<RateLimiter> findByAccountNumber();
}
