package com.example.demo.unit.controller;

import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

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
		when(service.savePolitician(politicianDTORequest)).thenReturn(politician);
		
		ResponseEntity<PoliticianDTO> politicianResponse = controller.savePolitician(politicianDTORequest);
		
		assertThat(politician.getRating().getAverageRating(),
				equalTo(politicianResponse.getBody().getRating()));
		assertThat(politicianResponse.getBody().getId(),
				containsStringIgnoringCase("polnumber"));
	}		
	
	@Test
	public void shouldEqualDTOOutputs() {
		when(service.findPoliticianByNumber("1")).thenReturn(politician);
		
		ResponseEntity<PoliticianDTO> politicianResponse = controller.politicianById("1");
		PoliticianDTO politicianDTO = new PoliticiansDtoMapper().mapToDTO(politician);
		
		assertThat(politicianDTO,
				equalTo(politicianResponse.getBody()));
	}			
	
}
