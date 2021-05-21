package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.dtomapper.interfaces.DTOMapper;
import com.example.demo.model.Politicians;
import com.example.demo.service.PoliticiansService;

@RestController
@RequestMapping("/api/politicians")
public class PoliticianController {
	
	private final PoliticiansService politiciansService;
	
	public PoliticianController(PoliticiansService politiciansService) {
		super();
		this.politiciansService = politiciansService;
	}

	@PostMapping("add-politician")
	public ResponseEntity<PoliticianDTO> savePolitician(@Valid @RequestBody AddPoliticianDTORequest request,
			@RequestHeader(name = "Politician-Access") String politicianHeader) {
		var politicianSaved = politiciansService.savePolitician(request);
		
		DTOMapper<PoliticianDTO, Politicians> mapper = new PoliticiansDtoMapper();
		
		PoliticianDTO politician = mapper.mapToDTO(politicianSaved);
		
		return new ResponseEntity<PoliticianDTO>(politician, HttpStatus.CREATED);
	}
	
	@GetMapping("/politicianByName")
	public ResponseEntity<PoliticianDTO> politicianByName(String lastName, String firstName) {
		var politicianByName = politiciansService.findPoliticianByName(lastName, firstName);
		
		DTOMapper<PoliticianDTO, Politicians> mapper = new PoliticiansDtoMapper();
		
		PoliticianDTO politician = mapper.mapToDTO(politicianByName);
		
		return new ResponseEntity<PoliticianDTO>(politician, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<PoliticianDTO>> allPoliticians() {
		List<Politicians> allPoliticians = politiciansService.allPoliticians();
		List<PoliticianDTO> politician = new ArrayList<>();
		
		DTOMapper<PoliticianDTO, Politicians> mapper = new PoliticiansDtoMapper();
		for (Politicians politicians : allPoliticians) {
			politician.add(mapper.mapToDTO(politicians));
		}
		
		return new ResponseEntity<List<PoliticianDTO>>(politician, HttpStatus.OK);
	}

}
