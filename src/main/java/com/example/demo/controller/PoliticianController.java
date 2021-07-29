 package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.dtomapper.interfaces.PoliticianDTOMapper;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.hateoas.PoliticianAssembler;
import com.example.demo.model.entities.Politicians;
import com.example.demo.service.PoliticiansService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@RequestMapping("/api/politicians")
public class PoliticianController {
	
	private final PoliticiansService politiciansService;
	private final PoliticianAssembler assembler;
	private PoliticianDTOMapper mapper = new PoliticiansDtoMapper();
	
	@Autowired
	public PoliticianController(PoliticiansService politiciansService, PoliticianAssembler assembler) {
		this.politiciansService = politiciansService;
		this.assembler = assembler;
	}

	@PostMapping("/politician")
	@Hidden
	public ResponseEntity<EntityModel<PoliticianDTO>> savePolitician(@Valid @RequestBody AddPoliticianDTORequest request) {
		Politicians politicianSaved = politiciansService.savePolitician(request);
		
		PoliticianDTO politician = mapper.mapToDTO(politicianSaved);
		
		EntityModel<PoliticianDTO> response = assembler.toModel(politician);
		
		return new ResponseEntity<EntityModel<PoliticianDTO>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/politician")
	public ResponseEntity<CollectionModel<EntityModel<PoliticianDTO>>> politicianByName(@RequestParam String lastName,
			@RequestParam String firstName) {
		List<Politicians> politicianByName = politiciansService.findPoliticianByName(lastName, firstName);
		
		List<? extends PoliticianDTO> politician = mapper.mapToDTO(politicianByName);
		
		CollectionModel<EntityModel<PoliticianDTO>> response = assembler.toCollectionModel(politician);
		
		return new ResponseEntity<CollectionModel<EntityModel<PoliticianDTO>>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/politician/{id}")
	public ResponseEntity<EntityModel<PoliticianDTO>> politicianById(@PathVariable String id) {
		Politicians politicianQueried = politiciansService.findPoliticianByNumber(id);
		
		PoliticianDTO politician = mapper.mapToDTO(politicianQueried);
		
		EntityModel<PoliticianDTO> response = assembler.toModel(politician);
		
		return new ResponseEntity<EntityModel<PoliticianDTO>>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/politicians")
	public ResponseEntity<CollectionModel<EntityModel<PoliticianDTO>>> allPoliticians() {
		List<Politicians> allPoliticians = politiciansService.allPoliticians();
		
		List<? extends PoliticianDTO> politician = mapper.mapToDTO(allPoliticians);
		
		CollectionModel<EntityModel<PoliticianDTO>> response = assembler.toCollectionModel(politician);
		
		return new ResponseEntity<CollectionModel<EntityModel<PoliticianDTO>>>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/politician/{polNumber}")
	public ResponseEntity<?> deletePolitician(@PathVariable String polNumber) {
		if (!politiciansService.deletePolitician(polNumber)) {
			throw new PoliticianNotFoundException("Politician not found by " + polNumber);
		}
		
		return ResponseEntity.noContent().build();
	}

}
