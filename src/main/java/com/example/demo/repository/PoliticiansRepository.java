package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entities.Politicians;

@Repository
public interface PoliticiansRepository extends JpaRepository<Politicians, Integer>{

	List<Politicians> findByFirstName(String firstName);
	
	List<Politicians> findByLastName(String lastName);
	
	List<Politicians> findByLastNameAndFirstName(String lastName, String firstName);
	
	List<Politicians> findByFullName(String name);
	
	Optional<Politicians> findByPoliticianNumber(String polNumber);
	
	long countByLastName(String lastName);

	long countByLastNameAndFirstName(String lastName, String firstName);
	
	boolean existsByPoliticianNumber(String polNumber);
	
	void deleteByPoliticianNumber(String polNumber);
	
}
