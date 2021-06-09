package com.example.demo.dtomapper.interfaces;

import java.util.List;

import com.example.demo.dto.RatingDTO;
import com.example.demo.model.entities.PoliticiansRating;

public interface RatingDTOMapper extends DTOMapper<RatingDTO, PoliticiansRating>{

	List<RatingDTO> mapToDTO(List<PoliticiansRating> entity);
	
}
