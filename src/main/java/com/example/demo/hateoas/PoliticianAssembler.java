package com.example.demo.hateoas;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.demo.adapter.dto.PoliticianDto;

@Component
public class PoliticianAssembler implements SimpleRepresentationModelAssembler<PoliticianDto>{

	@Override
	public void addLinks(EntityModel<PoliticianDto> resource) {
		
	}

	@Override
	public void addLinks(CollectionModel<EntityModel<PoliticianDto>> resources) {
		// TODO Auto-generated method stub
		
	}

}
