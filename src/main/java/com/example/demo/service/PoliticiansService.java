package com.example.demo.service;

import java.math.RoundingMode;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.exceptions.PoliticianAlreadyExistsException;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.model.Politicians;
import com.example.demo.repository.PoliticiansRepository;

@Service
public class PoliticiansService {

	private final PoliticiansRepository politiciansRepo;

	public PoliticiansService(PoliticiansRepository politiciansRepo) {
		this.politiciansRepo = politiciansRepo;
	}
	
	@Transactional(readOnly = true)
	public Politicians findPoliticianById(String id) {
		Politicians politician = politiciansRepo.findById(Integer.valueOf(id))
				.orElseThrow(() -> new PoliticianNotFoundException("No politician found using the given ID"));
		
		return politician;
	}
	
	@Transactional(readOnly = true)
	public Politicians findPoliticianByName(String name) {
		Politicians politician = politiciansRepo.findByName(name)
				.orElseThrow(() -> new PoliticianNotFoundException("No politician found using the given Name"));
		
		return politician;
	}
	
	@Transactional(readOnly = true)
	public List<Politicians> allPoliticians() {
		List<Politicians> politician = politiciansRepo.findAll();
		
		return politician;
	}
	
	public Politicians savePolitician(AddPoliticianDTORequest dto) {
		try {
			var politicianToBeSaved = new Politicians();
			politicianToBeSaved.setName(dto.getName());
			politicianToBeSaved.setRating(dto.getRating().doubleValue());
			politicianToBeSaved.setTotalRating(dto.getRating().setScale(2,RoundingMode.HALF_DOWN).doubleValue());
			
			Politicians politician = politiciansRepo.save(politicianToBeSaved);
			
			return politician;
		} catch (DataIntegrityViolationException e) {
			throw new PoliticianAlreadyExistsException("Politician Already exists in the database");
		}
	}
	
}
