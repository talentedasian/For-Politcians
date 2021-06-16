package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.exceptions.PoliticianAlreadyExistsException;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.politicianNumber.PoliticianNumberImplementor;
import com.example.demo.repository.PoliticiansRepository;

@EnableTransactionManagement
@Service
public class PoliticiansService {

	private final PoliticiansRepository politiciansRepo;
	
	private static Integer polNumber = 1;

	@Autowired
	public PoliticiansService(PoliticiansRepository politiciansRepo) {
		this.politiciansRepo = politiciansRepo;
	}
	
	@Transactional(readOnly = true)
	public Politicians findPoliticianByNumber(String polNumber) throws PoliticianNotFoundException {
		Politicians politician = politiciansRepo.findByPoliticianNumber(polNumber)
				.orElseThrow(() -> new PoliticianNotFoundException("No politician found using the given number"));
		
		return politician;
	}
	
	@Transactional(readOnly = true)
	public List<Politicians> findPoliticianByName(String lastName, String firstName) throws PoliticianNotFoundException {
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
			var politicianNumImplementor = PoliticianNumberImplementor.with
					(dto.getFirstName(), 
					dto.getLastName(), 
					String.valueOf(polNumber));
			
			var politicianToBeSaved = new Politicians.PoliticiansBuilder()
					.setFirstName(dto.getFirstName())
					.setLastName(dto.getLastName())
					.setFullName()
					.setRating(new Rating(dto.getRating().doubleValue(), 
					0.01D, 
					new LowSatisfactionAverageCalculator(dto.getRating().doubleValue(), 0D)))
					.setPoliticianNumber(politicianNumImplementor.calculatePoliticianNumber().getPoliticianNumber())
					.build();
			
			Politicians politician = politiciansRepo.save(politicianToBeSaved);
			
			//increment the politician number used for politician number patterns
			polNumber++;
			return politician;
			
		} catch (DataIntegrityViolationException e) {
			throw new PoliticianAlreadyExistsException("Politician Already exists in the database");
		}
	}
	
}
