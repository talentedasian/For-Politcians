package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.dto.RateLimitJpaDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RateLimitRepositoryJpa extends JpaRepository<RateLimitJpaDto, UUID> {

    void deleteByIdAndPoliticianNumber(String id, String politicianNumber);

}
