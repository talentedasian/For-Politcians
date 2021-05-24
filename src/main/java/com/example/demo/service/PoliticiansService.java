package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.exceptions.PoliticianAlreadyExistsException;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Rating;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.repository.RatingRepository;

@EnableTransactionManagement
@Service
public class PoliticiansService {

	private final PoliticiansRepository politiciansRepo;
	private final RatingRepository ratingRepo;

	@Autowired
	public PoliticiansService(PoliticiansRepository politiciansRepo, RatingRepository ratingRepo) {
		this.politiciansRepo = politiciansRepo;
		this.ratingRepo = ratingRepo;
	}
	
	@Transactional(readOnly = true)
	public Politicians findPoliticianById(String id) {
		Politicians politician = politiciansRepo.findById(Integer.valueOf(id))
				.orElseThrow(() -> new PoliticianNotFoundException("No politician found using the given ID"));
		
		return politician;
	}
	
	@Transactional(readOnly = true)
	public Politicians findPoliticianByName(String lastName, String firstName) {
		Politicians politician = politiciansRepo.findByLastNameAndFirstName(lastName, firstName)
				.orElseThrow(() -> new PoliticianNotFoundException("No politician found using the given Name"));
		
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
			var politicianToBeSaved = new Politicians();
			politicianToBeSaved.setRepo(ratingRepo);
			politicianToBeSaved.setFirstName(dto.getFirstName());
			politicianToBeSaved.setLastName(dto.getLastName());
			politicianToBeSaved.calculateFullName();
			politicianToBeSaved.setRating(new Rating(dto.getRating().doubleValue(), 
					0.01D, 
					new LowSatisfactionAverageCalculator(dto.getRating().doubleValue(), 0D)));
			
			Politicians politician = politiciansRepo.saveAndFlush(politicianToBeSaved);
			
			return politician;
			
		} catch (Exception e) {
			throw new PoliticianAlreadyExistsException("Politician Already exists in the database");
		}
	}
	
}
