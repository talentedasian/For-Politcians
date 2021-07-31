package com.example.demo.dtomapper.interfaces;

import java.util.List;

import com.example.demo.dto.PoliticianDTO;
import com.example.demo.model.entities.politicians.Politicians;

public interface PoliticianDTOMapper extends DTOMapper<PoliticianDTO,Politicians>{
 
	 List<? extends PoliticianDTO> mapToDTO(List<Politicians> entity);
}
