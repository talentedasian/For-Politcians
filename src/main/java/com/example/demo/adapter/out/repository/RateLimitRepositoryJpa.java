package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.dto.RateLimitJpaDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateLimitRepositoryJpa extends JpaRepository<RateLimitJpaDto, Long> {

    void deleteByAccountNumberAndPoliticianNumber(String id, String politicianNumber);

    Optional<RateLimitJpaDto> findByAccountNumberAndPoliticianNumber(String id, String polNumber);
}
