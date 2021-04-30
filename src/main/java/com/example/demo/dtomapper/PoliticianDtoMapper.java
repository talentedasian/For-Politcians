package com.example.demo.dtomapper;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtomapper.interfaces.PoliticiansDTOMapper;
import com.example.demo.model.Politicians;

public class PoliticianDtoMapper implements PoliticiansDTOMapper{

	@Override
	public PoliticianDTO mapToDTO(Politicians entity) {
		var politicianDTO =  new PoliticianDTO(
				entity.getName(), 
				String.valueOf(entity.getId()), 
				entity.getRating());
		
		return politicianDTO;
	}

}
