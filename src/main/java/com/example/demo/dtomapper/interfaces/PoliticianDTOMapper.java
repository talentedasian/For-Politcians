package com.example.demo.dtomapper.interfaces;

import com.example.demo.adapter.web.dto.PoliticianDto;
import com.example.demo.domain.entities.Politicians;

import java.util.List;

public interface PoliticianDTOMapper extends DTOMapper<PoliticianDto,Politicians>{
 
	 List<? extends PoliticianDto> mapToDTO(List<Politicians> entity);

}
