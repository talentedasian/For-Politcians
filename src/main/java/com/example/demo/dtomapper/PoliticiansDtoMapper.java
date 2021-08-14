package com.example.demo.dtomapper;

import com.example.demo.adapter.dto.PoliticianDto;
import com.example.demo.domain.enums.Rating;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.dtomapper.interfaces.PoliticianDTOMapper;

import java.util.List;

public class PoliticiansDtoMapper implements PoliticianDTOMapper{

	@Override
	public PoliticianDto mapToDTO(Politicians entity) {
		switch(entity.getType()) {
			case PRESIDENTIAL -> {return new PresidentialDtoMapper().mapToDTO(entity);}
			case SENATORIAL -> {return new SenatorialDtoMapper().mapToDTO(entity);}
		}

		return mapToPoliticianDTO(entity);
	}

	@Override
	public List<? extends PoliticianDto> mapToDTO(List<Politicians> entity) {
		return entity.stream()
			.map(politicians -> mapToDTO(politicians))
			.toList();
	}
	
	private PoliticianDto mapToPoliticianDTO(Politicians entity) {
		Double rating = entity.getRating().getAverageRating();
		Rating satisfactionRate = Rating.mapToSatisfactionRate(rating);
		
		return new PoliticianDto(
				entity.getFirstName() + " " + entity.getLastName(), 
				entity.getPoliticianNumber(), 
				rating,
				satisfactionRate,
				"Presidential");
	}

}
