package com.example.demo.dtomapper;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtomapper.interfaces.PoliticianDTOMapper;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.enums.Rating;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class PoliticiansDtoMapper implements PoliticianDTOMapper{

	@Override
	public PoliticianDTO mapToDTO(Politicians entity) {
		switch(entity.getType()) {
			case PRESIDENTIAL -> {return new PresidentialDtoMapper().mapToDTO(entity);}
			case SENATORIAL -> {return new SenatorialDtoMapper().mapToDTO(entity);}
		}

		return mapToPoliticianDTO(entity);
	}
	
	@Override
	public List<? extends PoliticianDTO> mapToDTO(List<Politicians> entity) {
		return entity.stream()
			.map(politicians -> mapToDTO(politicians))
			.collect(toList());
	}
	
	private PoliticianDTO mapToPoliticianDTO(Politicians entity) {
		Double rating = entity.getRating().getAverageRating();
		Rating satisfactionRate = Rating.mapToSatisfactionRate(rating);
		
		return new PoliticianDTO(
				entity.getFirstName() + " " + entity.getLastName(), 
				entity.getPoliticianNumber(), 
				rating,
				satisfactionRate);
	}

}
