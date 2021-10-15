 package com.example.demo.adapter.in.web;

 import com.example.demo.adapter.in.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.adapter.web.dto.PoliticianDto;
import com.example.demo.domain.Page;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import com.example.demo.hateoas.PoliticianAssembler;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/politicians")
public class PoliticianController {
	
	private final PoliticianServiceAdapter politiciansService;
	private final PoliticianAssembler assembler;
	
	@Autowired
	public PoliticianController(PoliticianServiceAdapter politiciansService, PoliticianAssembler assembler) {
		this.politiciansService = politiciansService;
		this.assembler = assembler;
	}

	@PostMapping("/politician")
	@Hidden
	public ResponseEntity<EntityModel<PoliticianDto>> savePolitician(@Valid @RequestBody AddPoliticianDTORequest request) throws PoliticianNotPersistableException {
		PoliticianDto politicianSaved = politiciansService.savePolitician(request);

		EntityModel<PoliticianDto> response = assembler.toModel(politicianSaved);

		return new ResponseEntity<EntityModel<PoliticianDto>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/politician/{id}")
	public ResponseEntity<EntityModel<PoliticianDto>> politicianById(@PathVariable String id) {
		PoliticianDto politicianQueried = politiciansService.findPoliticianUsingNumber(id);
		
		EntityModel<PoliticianDto> response = assembler.toModel(politicianQueried);
		
		return new ResponseEntity<EntityModel<PoliticianDto>>(response, HttpStatus.OK);
	}
	
	
	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<PoliticianDto>>> allPoliticians() {
		System.out.println("haha tangina gumagana");
		List<PoliticianDto> allPoliticians = politiciansService.allPoliticians();
		
		CollectionModel<EntityModel<PoliticianDto>> response = assembler.toCollectionModel(allPoliticians);
		response.add(Link.of("/api/politicians/politicians").withSelfRel());

		return new ResponseEntity<CollectionModel<EntityModel<PoliticianDto>>>(response, HttpStatus.OK);
	}

	@GetMapping("/politicians")
	public ResponseEntity<CollectionModel<EntityModel<PoliticianDto>>> allPoliticiansWithPage(@RequestParam(name = "page") int pageNumber,
																							  @RequestParam(required = false, defaultValue = "10", name = "items") int itemsToFetch,
																							  HttpServletRequest req) {
		List<PoliticianDto> allPoliticians = politiciansService.allPoliticiansWithPage(Page.of(pageNumber), itemsToFetch, req);

		CollectionModel<EntityModel<PoliticianDto>> response = assembler.toCollectionModel(allPoliticians);
		response.add(Link.of("/api/politicians/politicians?page=" + pageNumber + "&items=" + itemsToFetch).withSelfRel().expand());

		return new ResponseEntity<CollectionModel<EntityModel<PoliticianDto>>>(response, HttpStatus.OK);
	}

}
