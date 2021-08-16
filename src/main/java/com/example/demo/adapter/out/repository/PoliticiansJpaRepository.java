package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.out.jpa.PoliticiansJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoliticiansJpaRepository extends JpaRepository<PoliticiansJpaEntity, String> {

    List<PoliticiansJpaEntity> findByLastNameAndFirstName(String lastName, String firstName);

}
