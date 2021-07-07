package com.example.demo.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import com.example.demo.baseClasses.AbstractPoliticianControllerTest;
import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtomapper.PoliticiansDtoMapper;

public class PoliticianControllerTest extends AbstractPoliticianControllerTest {
	
	@Test
	public void shouldCallSaveFromServiceWhenSaved() {
		when(service.savePolitician(politicianDTORequest)).thenReturn(politician);
		controller.savePolitician(politicianDTORequest);
		
		verify(service, times(1)).savePolitician(politicianDTORequest);
	}
	
	@Test
	public void shouldEqualDTOOutputsWhenSaved() {
		PoliticianDTO polDTO = new PoliticiansDtoMapper().mapToDTO(politician);
		
		when(service.savePolitician(politicianDTORequest)).thenReturn(politician);
		when(assembler.toModel(polDTO)).thenReturn(EntityModel.of(polDTO));
		
		PoliticianDTO politicianResponse = controller.savePolitician(politicianDTORequest).getBody().getContent();
		
		assertEquals(polDTO, politicianResponse);
	}		
	
	@Test
	public void shouldEqualDTOOutputsByPoliticianNumber() {
		PoliticianDTO polDTO = new PoliticiansDtoMapper().mapToDTO(politician);
		
		when(service.findPoliticianByNumber("1")).thenReturn(politician);
		when(assembler.toModel(polDTO)).thenReturn(EntityModel.of(polDTO));
		
		PoliticianDTO politicianResponse = controller.politicianById("1").getBody().getContent();
		PoliticianDTO politicianDTO = new PoliticiansDtoMapper().mapToDTO(politician);
		
		assertEquals(politicianDTO, politicianResponse);
	}			
	
}
