package com.example.demo.adapter.in.service;

import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.exceptions.PoliticianAlreadyExistsException;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
	All transaction annotations shall be removed in branch 1.2.X.restful
	or at least in any latest branch.
 */
public class PoliticiansService {

	private final PoliticiansRepository politiciansRepo;

	public PoliticiansService(PoliticiansRepository politiciansRepo) {
		this.politiciansRepo = politiciansRepo;
	}

	public Optional<Politicians> findPoliticianByNumber(String polNumber) {
		return politiciansRepo.findByPoliticianNumber(polNumber);
	}

	@Transactional(readOnly = true)
	public List<Politicians> findPoliticianByName(String lastName, String firstName) {
		return politiciansRepo.findByLastNameAndFirstName(lastName, firstName);
	}
	
	@Transactional(readOnly = true)
	public List<Politicians> allPoliticians() {
		return politiciansRepo.findAll();
	}
	
	@Transactional
	public Politicians savePolitician(Politicians politician) throws PoliticianNotPersistableException {
		try {
			Politicians politicianSaved = politiciansRepo.save(politician);

			return politicianSaved;
		} catch (DataIntegrityViolationException e) {
			throw new PoliticianAlreadyExistsException("Politician Already exists in the database");
		}
	}

	/*
		Adapters that use the service must not use this method yet.
		Updating politicians is only done on lower levels of code and not on the user side.
	 */
	public Politicians updatePolitician(Politicians politician) throws PoliticianNotPersistableException {
		return politiciansRepo.update(politician);
	}

	@Transactional(readOnly = true)
	public boolean doesExistWithPoliticianNumber(String polNumber) {
		return politiciansRepo.existsByPoliticianNumber(polNumber);
	}

	@Transactional
	public void deletePolitician(String polNumber) {
		politiciansRepo.deleteByPoliticianNumber(polNumber);
	}

	@Transactional
	public PagedObject<Politicians> findAllWithPage(Page page, int numberOfItemsToFetch, final Long total) {
		return politiciansRepo.findAllByPage(page, numberOfItemsToFetch, total == null ? politiciansRepo.count() : total);
	}

}
