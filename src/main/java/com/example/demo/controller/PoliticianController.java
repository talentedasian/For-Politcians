package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.repository.PoliticiansRepository;

@RestController
@RequestMapping("/api/politicians")
public class PoliticianController {
	
	private final PoliticiansRepository politiciansRepo;
	
	public PoliticianController(PoliticiansRepository politiciansRepo) {
		super();
		this.politiciansRepo = politiciansRepo;
	}

	@PostMapping("add-politician")
	public PoliticianDTO savePolitician() {
		politiciansRepo.save(entity)
	}
	

}
