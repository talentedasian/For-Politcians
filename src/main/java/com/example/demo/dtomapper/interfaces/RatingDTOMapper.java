package com.example.demo.dtomapper.interfaces;

import java.util.List;

import com.example.demo.adapter.web.dto.RatingDTO;
import com.example.demo.domain.entities.PoliticiansRating;

public interface RatingDTOMapper extends DTOMapper<RatingDTO, PoliticiansRating>{

	List<RatingDTO> mapToDTO(List<PoliticiansRating> entity);
	
}
