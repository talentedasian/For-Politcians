package com.example.demo.service;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.dtomapper.PoliticianDTOUnwrapper;
import com.example.demo.exceptions.PoliticianAlreadyExistsException;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.entities.politicians.Politicians.PoliticiansBuilder;
import com.example.demo.repository.PoliticiansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableTransactionManagement
@Service
public class PoliticiansService {

	private final PoliticiansRepository politiciansRepo;

	@Autowired
	public PoliticiansService(PoliticiansRepository politiciansRepo) {
		this.politiciansRepo = politiciansRepo;
	}

	PoliticiansBuilder politicianBuilder = new PoliticiansBuilder("dummy");

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
	public Politicians savePolitician(AddPoliticianDTORequest dto) {
		try {
			Politicians unwrappedPolitician = new PoliticianDTOUnwrapper().unWrapDTO(dto);

			Politicians politician = politiciansRepo.save(unwrappedPolitician);

			return politician;
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
