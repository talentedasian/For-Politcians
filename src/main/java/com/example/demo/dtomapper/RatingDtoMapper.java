package com.example.demo.dtomapper;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dto.RatingDTO;
import com.example.demo.dtomapper.interfaces.RatingDTOMapper;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;
import com.example.demo.model.enums.Rating;

public class RatingDtoMapper implements RatingDTOMapper{

	@Override
	public RatingDTO mapToDTO(PoliticiansRating entity) {
		Politicians politician = entity.getPolitician();
		Rating satisfactionRate = null;
		if (politician.getRating() < 5D) {
			satisfactionRate = Rating.LOW;
		} else if (politician.getRating() < 8.89D) {
			satisfactionRate = Rating.DECENT;
		} else if (politician.getRating() >= 8.89D) {
			satisfactionRate = Rating.HIGH;
		}
		
		var politicianDTO = new PoliticianDTO
				(politician.getFirstName() + "\s" + politician.getLastName(), 
				politician.getId().toString(),
				politician.getRating(),
				satisfactionRate);
		
		var ratingDTO = new RatingDTO
				(entity.getRating(),
				entity.getRater(), 
				politicianDTO);
		
		return ratingDTO;
	}

}
