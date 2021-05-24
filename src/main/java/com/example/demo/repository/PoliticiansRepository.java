package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entities.Politicians;

@Repository
public interface PoliticiansRepository extends JpaRepository<Politicians, Integer>{

	Optional<Politicians> findByFirstName(String firstName);
	
	Optional<Politicians> findByLastName(String lastName);
	
	Optional<Politicians> findByLastNameAndFirstName(String lastName, String firstName);
	
	Optional<Politicians> findByFullName(String name);
	
	long countByLastName(String lastName);

	long countByLastNameAndFirstName(String lastName, String firstName);
	
}
