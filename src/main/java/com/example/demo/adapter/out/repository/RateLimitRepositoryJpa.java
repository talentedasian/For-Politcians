package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.web.dto.RateLimitJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RateLimitRepositoryJpa extends JpaRepository<RateLimitJpaEntity, Long> {

    void deleteByAccountNumberAndPoliticianNumber(String id, String politicianNumber);

    Optional<RateLimitJpaEntity> findByAccountNumberAndPoliticianNumber(String id, String polNumber);

    List<RateLimitJpaEntity> findByAccountNumber(String accountNumber);

    long countByAccountNumberAndPoliticianNumber(String accountNumber, String polNumber);
}
