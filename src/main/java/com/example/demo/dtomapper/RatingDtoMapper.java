package com.example.demo.dtomapper;

import java.util.ArrayList;
import java.util.List;

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
		Rating satisfactionRate = Rating.mapToSatisfactionRate(politician.getRating().getAverageRating());
		
		var politicianDTO = new PoliticianDTO
				(politician.getFirstName() + "\s" + politician.getLastName(), 
				politician.getId().toString(),
				politician.getRating().getAverageRating(),
				satisfactionRate);
		
		var ratingDTO = new RatingDTO
				(entity.getRating(),
				entity.getRater(), 
				politicianDTO);
		
		return ratingDTO;
	}

	@Override
	public List<RatingDTO> mapToDTO(List<PoliticiansRating> entity) {
		List<RatingDTO> ratingDTOList = new ArrayList<>();
		for (PoliticiansRating politiciansRatings : entity) {
			Politicians politician = politiciansRatings.getPolitician();
			Rating satisfactionRate = Rating.mapToSatisfactionRate(politician.getRating().getAverageRating());
			
			var politicianDTO = new PoliticianDTO
					(politician.getFullName(), 
					String.valueOf(politician.getId()), 
					politician.getRating().getAverageRating(), 
					satisfactionRate);
			
			var ratingDTO = new RatingDTO
					(politician.getRating().getAverageRating(), 
					politiciansRatings.getRater(), 
					politicianDTO);
			
			ratingDTOList.add(ratingDTO);
		}
		
		return ratingDTOList;
	}

}
