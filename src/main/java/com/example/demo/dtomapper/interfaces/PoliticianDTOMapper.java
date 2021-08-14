package com.example.demo.dtomapper.interfaces;

import com.example.demo.adapter.dto.PoliticianDto;
import com.example.demo.domain.politicians.Politicians;

import java.util.List;

public interface PoliticianDTOMapper extends DTOMapper<PoliticianDto,Politicians>{
 
	 List<? extends PoliticianDto> mapToDTO(List<PoliticianDto> entity);

}
