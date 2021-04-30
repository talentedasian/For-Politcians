package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Politicians;

public interface PoliticiansRepository extends JpaRepository<Politicians, Integer>{

	Optional<Politicians> findByName(String name);
}
