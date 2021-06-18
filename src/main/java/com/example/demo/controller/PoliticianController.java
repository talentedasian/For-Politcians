package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.dtomapper.interfaces.PoliticianDTOMapper;
import com.example.demo.model.entities.Politicians;
import com.example.demo.service.PoliticiansService;

@RestController
@RequestMapping("/api/politicians")
public class PoliticianController {
	
	private final PoliticiansService politiciansService;
	private PoliticianDTOMapper mapper;
	
	@Autowired
	public PoliticianController(PoliticiansService politiciansService, Optional<PoliticianDTOMapper> mapper) {
		this.politiciansService = politiciansService;
		mapper.ifPresent(dtoMapper -> {this.mapper = dtoMapper;});
	}

	@PostMapping("/politician")
	public ResponseEntity<PoliticianDTO> savePolitician(@Valid @RequestBody AddPoliticianDTORequest request,
			@RequestHeader(name = "Politician-Access") String politicianHeader) {
		Politicians politicianSaved = politiciansService.savePolitician(request);
		
		mapper = new PoliticiansDtoMapper();
		
		PoliticianDTO politician = mapper.mapToDTO(politicianSaved);
		
		return new ResponseEntity<PoliticianDTO>(politician, HttpStatus.CREATED);
	}
	
	@GetMapping("/politician")
	public ResponseEntity<List<PoliticianDTO>> politicianByName(String lastName, String firstName) {
		List<Politicians> politicianByName = politiciansService.findPoliticianByName(lastName, firstName);
		
		mapper = new PoliticiansDtoMapper();
		
		List<PoliticianDTO> politician = mapper.mapToDTO(politicianByName);
		
		return new ResponseEntity<List<PoliticianDTO>>(politician, HttpStatus.OK);
	}
	
	@GetMapping("/politician/{id}")
	public ResponseEntity<PoliticianDTO> politicianById(@PathVariable String id) {
		Politicians politicianQueried = politiciansService.findPoliticianByNumber(id);
		
		mapper = new PoliticiansDtoMapper();
		
		PoliticianDTO politician = mapper.mapToDTO(politicianQueried);
		
		return new ResponseEntity<PoliticianDTO>(politician, HttpStatus.OK);
	}
	
	
	@GetMapping("/politicians")
	public ResponseEntity<List<PoliticianDTO>> allPoliticians() {
		List<Politicians> allPoliticians = politiciansService.allPoliticians();
		
		mapper = new PoliticiansDtoMapper();
		List<PoliticianDTO> politician = mapper.mapToDTO(allPoliticians);
		
		return new ResponseEntity<List<PoliticianDTO>>(politician, HttpStatus.OK);
	}

}
