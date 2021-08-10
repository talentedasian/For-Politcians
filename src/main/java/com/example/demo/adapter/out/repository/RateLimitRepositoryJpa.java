package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.dto.RateLimitDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateLimitRepositoryJpa extends JpaRepository<RateLimitDTO, String> {

    void deleteByIdAndPoliticianNumber(String id, String politicianNumber);

}
