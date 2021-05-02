package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<PoliticianDTO> savePolitician(@Valid @RequestBody AddPoliticianDTORequest request) {
		var politicianSaved = politiciansService.savePolitician(request);
		
		DTOMapper<PoliticianDTO, Politicians> mapper = new PoliticiansDtoMapper();
		
		PoliticianDTO politician = mapper.mapToDTO(politicianSaved);
		
		return new ResponseEntity<PoliticianDTO>(politician, HttpStatus.CREATED);
	}
	
	@GetMapping("/politicianByName")
	public ResponseEntity<PoliticianDTO> politicianByName(String name) {
		var politicianByName = politiciansService.findPoliticianByName(name);
		
		DTOMapper<PoliticianDTO, Politicians> mapper = new PoliticiansDtoMapper();
		
		PoliticianDTO politician = mapper.mapToDTO(politicianByName);
		
		return new ResponseEntity<PoliticianDTO>(politician, HttpStatus.OK);
	}

}
