package com.example.demo.dtomapper;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtomapper.interfaces.PoliticianDTOMapper;
import com.example.demo.model.Politicians;
import com.example.demo.model.enums.Rating;

public class PoliticiansDtoMapper implements PoliticianDTOMapper{

	@Override
	public PoliticianDTO mapToDTO(Politicians entity) {
		Rating satisfactionRate = null;
		if (entity.getRating() < 5D) {
			satisfactionRate = Rating.LOW;
		} else if (entity.getRating() < 8.89D) {
			satisfactionRate = Rating.DECENT;
		} else if (entity.getRating() >= 8.89D) {
			satisfactionRate = Rating.HIGH;
		}
		var politicianDTO =  new PoliticianDTO(
				entity.getName(), 
				String.valueOf(entity.getId()), 
				entity.getRating().doubleValue(),
				satisfactionRate);
		
		return politicianDTO;
	}

}
