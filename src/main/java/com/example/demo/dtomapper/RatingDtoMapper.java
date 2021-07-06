package com.example.demo.dtomapper;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dto.RatingDTO;
import com.example.demo.dtomapper.interfaces.RatingDTOMapper;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.PoliticiansRating;

public class RatingDtoMapper implements RatingDTOMapper{

	@Override
	public RatingDTO mapToDTO(PoliticiansRating entity) {
		Politicians politician = entity.getPolitician();
		
		var politicianDTO = new PoliticiansDtoMapper().mapToDTO(politician);
		
		var ratingDTO = mapToRatingDTO(entity, politicianDTO);
		
		return ratingDTO;
	}

	@Override
	public List<RatingDTO> mapToDTO(List<PoliticiansRating> entity) {
		List<RatingDTO> ratingDTOList = new ArrayList<>();
		
		entity.stream().forEach(politiciansRatings -> {
			Politicians politician = politiciansRatings.getPolitician();
			var politicianDTO = new PoliticiansDtoMapper().mapToDTO(politician);
			
			var ratingDTO = mapToRatingDTO(politiciansRatings, politicianDTO);
			
			ratingDTOList.add(ratingDTO);
		});
		
		return ratingDTOList;
	}
	
	private RatingDTO mapToRatingDTO(PoliticiansRating entity, PoliticianDTO politicianDTO) {
		return new RatingDTO
				(entity.getRating(),
				entity.getRater(), 
				politicianDTO,
				String.valueOf(entity.getId()));
	}

}
