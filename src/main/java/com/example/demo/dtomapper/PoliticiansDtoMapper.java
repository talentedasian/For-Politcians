package com.example.demo.dtomapper;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtomapper.interfaces.PoliticianDTOMapper;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.enums.Rating;

public class PoliticiansDtoMapper implements PoliticianDTOMapper{

	@Override
	public PoliticianDTO mapToDTO(Politicians entity) {
		Double rating = entity.getRating().getAverageRating();
		Rating satisfactionRate = null;
		if (rating < 5D) {
			satisfactionRate = Rating.LOW;
		} else if (rating < 8.89D) {
			satisfactionRate = Rating.DECENT;
		} else if (rating >= 8.89D) {
			satisfactionRate = Rating.HIGH;
		}
		var politicianDTO =  new PoliticianDTO(
				entity.getFirstName() + " " + entity.getLastName(), 
				String.valueOf(entity.getId()), 
				entity.getRating().getAverageRating(),
				satisfactionRate);
		
		return politicianDTO;
	}
	
	@Override
	public List<PoliticianDTO> mapToDTO(List<Politicians> entity) {
		List<PoliticianDTO> politicianDTOList = new ArrayList<>();
		for (Politicians politicians : entity) {
			Double rating = politicians.getRating().getAverageRating();			
			Rating satisfactionRate = Rating.mapToSatisfactionRate(rating);
			var politicianDTO =  new PoliticianDTO(
					politicians.getFirstName() + " " + politicians.getLastName(), 
					String.valueOf(politicians.getId()), 
					politicians.getRating().getAverageRating(),
					satisfactionRate);
			
			politicianDTOList.add(politicianDTO);
		}
		
		return politicianDTOList;
	}

}
