package com.example.demo.adapter.out.repository;

import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.exceptions.PoliticianNotPersistableException;

import java.util.List;
import java.util.Optional;

public interface PoliticiansRepository{

	Politicians save(Politicians politician) throws PoliticianNotPersistableException;

	Politicians update(Politicians politician);

	List<Politicians> findByLastNameAndFirstName(String lastName, String firstName);
	
	Optional<Politicians> findByPoliticianNumber(String polNumber);

	boolean existsByPoliticianNumber(String polNumber);
	
	void deleteByPoliticianNumber(String polNumber);

    List<Politicians> findAll();

    PagedObject<Politicians> findAllByPage(Page page, int itemsToFetch, long total);

    long count();
}
