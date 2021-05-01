package com.example.demo.service;

import org.springframework.stereotype.Component;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.model.Politicians;
import com.example.demo.repository.PoliticiansRepository;

@Component
public class PoliticiansService {

	private final PoliticiansRepository politiciansRepo;

	public PoliticiansService(PoliticiansRepository politiciansRepo) {
		this.politiciansRepo = politiciansRepo;
	}
	
	public Politicians findPoliticianById(String id) {
		Politicians politician = politiciansRepo.findById(Integer.valueOf(id))
				.orElseThrow(() -> new PoliticianNotFoundException("No politician found using the given ID"));
		
		return politician;
	}
	
	public Politicians findPoliticianByName(String name) {
		Politicians politician = politiciansRepo.findByName(name)
				.orElseThrow(() -> new PoliticianNotFoundException("No politician found using the given Name"));
		
		return politician;
	}
	
	public Politicians savePolitician(AddPoliticianDTORequest dto) {
		var politicianToBeSaved = new Politicians(
				dto.getRating().doubleValue(), 
				dto.getName());
		
		Politicians politician = politiciansRepo.save(politicianToBeSaved);
		
		return politician;
	}
	
}
