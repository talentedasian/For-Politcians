package com.example.demo.repository;

import com.example.demo.model.entities.politicians.Politicians;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PoliticiansRepository extends JpaRepository<Politicians, Integer>{
	
	List<Politicians> findByLastNameAndFirstName(String lastName, String firstName);
	
	Optional<Politicians> findByPoliticianNumber(String polNumber);

	boolean existsByPoliticianNumber(String polNumber);
	
	void deleteByPoliticianNumber(String polNumber);
	
}
