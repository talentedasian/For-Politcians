package com.example.demo.hateoas;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.demo.dto.PoliticianDTO;

@Component
public class PoliticianAssembler implements SimpleRepresentationModelAssembler<PoliticianDTO>{

	@Override
	public void addLinks(EntityModel<PoliticianDTO> resource) {
		
	}

	@Override
	public void addLinks(CollectionModel<EntityModel<PoliticianDTO>> resources) {
		// TODO Auto-generated method stub
		
	}

}
