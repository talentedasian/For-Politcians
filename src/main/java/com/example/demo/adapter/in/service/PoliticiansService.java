package com.example.demo.adapter.in.service;

import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.exceptions.PoliticianAlreadyExistsException;
import com.example.demo.exceptions.PoliticianNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PoliticiansService {

	private final PoliticiansRepository politiciansRepo;

	public PoliticiansService(PoliticiansRepository politiciansRepo) {
		this.politiciansRepo = politiciansRepo;
	}

	@Transactional(readOnly = true)
	public Politicians findPoliticianByNumber(String polNumber) {
		Politicians politician = politiciansRepo.findByPoliticianNumber(polNumber)
				.orElseThrow(() -> new PoliticianNotFoundException("No politician found using the given number"));
		
		return politician;
	}

	@Transactional(readOnly = true)
	public List<Politicians> findPoliticianByName(String lastName, String firstName) {
		List<Politicians> politician = politiciansRepo.findByLastNameAndFirstName(lastName, firstName);

		if (politician.isEmpty()) {
			throw new PoliticianNotFoundException("No politician found by given full name");
		}
		
		return politician;
	}
	
	@Transactional(readOnly = true)
	public List<Politicians> allPoliticians() {
		List<Politicians> politician = politiciansRepo.findAll();
		
		return politician;
	}
	
	@Transactional
	public Politicians savePolitician(Politicians politician) {
		try {
			Politicians politicianSaved = politiciansRepo.save(politician);

			return politicianSaved;
		} catch (DataIntegrityViolationException e) {
			throw new PoliticianAlreadyExistsException("Politician Already exists in the database");
		}
	}
	
	@Transactional
	public boolean deletePolitician(String polNumber) {
		if (politiciansRepo.existsByPoliticianNumber(polNumber)) {
			politiciansRepo.deleteByPoliticianNumber(polNumber);
			return true;
		}
		
		return false;
	}
	
}
