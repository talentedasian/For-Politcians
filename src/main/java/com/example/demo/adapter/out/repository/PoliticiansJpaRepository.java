package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.out.jpa.PoliticiansJpaEntity;
import com.example.demo.domain.politicians.Politicians;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PoliticiansJpaRepository extends JpaRepository<PoliticiansJpaEntity, String> {

    List<Politicians> findByLastNameAndFirstName(String lastName, String firstName);

    Optional<Politicians> findByPoliticianNumber(String polNumber);

    boolean existsByPoliticianNumber(String polNumber);

    void deleteByPoliticianNumber(String polNumber);

}
