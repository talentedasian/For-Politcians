package com.example.demo.adapter.out.repository;

import com.example.demo.domain.politicians.Politicians;

import java.util.List;
import java.util.Optional;

public interface PoliticiansRepository{

	Politicians save(Politicians politician);

	Politicians update(Politicians politician);


	List<Politicians> findByLastNameAndFirstName(String lastName, String firstName);
	
	Optional<Politicians> findByPoliticianNumber(String polNumber);

	boolean existsByPoliticianNumber(String polNumber);
	
	void deleteByPoliticianNumber(String polNumber);

    List<Politicians> findAll();
}
