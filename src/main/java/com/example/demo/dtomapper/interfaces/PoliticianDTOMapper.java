package com.example.demo.dtomapper.interfaces;

import com.example.demo.adapter.dto.PoliticianDTO;
import com.example.demo.domain.politicians.Politicians;

import java.util.List;

public interface PoliticianDTOMapper extends DTOMapper<PoliticianDTO,Politicians>{
 
	 List<? extends PoliticianDTO> mapToDTO(List<Politicians> entity);

}
