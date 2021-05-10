package com.example.demo.unit.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.controller.PoliticianController;
import com.example.demo.dto.PoliticianDTO;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.model.Politicians;
import com.example.demo.service.PoliticiansService;

@ExtendWith(SpringExtension.class)
public class PoliticianControllerTest {

	@Mock
	public PoliticiansService service;
	public PoliticianController controller;
	public Politicians politician;
	public AddPoliticianDTORequest politicianDTORequest;
	
	@BeforeEach
	public void setUp() {
		controller = new PoliticianController(service);
		
		politician =  new Politicians();
		politician.setRating(9.67D);
		politician.setName("Mirriam Defensor");
		
		politicianDTORequest = new AddPoliticianDTORequest
		("Mirriam Defensor", 
		BigDecimal.valueOf(9.67D));
	}
	
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
		
		assertThat(politician.getRating(),
				equalTo(politicianResponse.getBody().getRating()));
		assertThat(politician.getId(),
				nullValue());
	}		
	
	@Test
	public void shouldEqualDTOOutputs() {
		when(service.findPoliticianByName("Mirriam Defensor")).thenReturn(politician);
		
		ResponseEntity<PoliticianDTO> politicianResponse = controller.politicianByName("Mirriam Defensor");
		PoliticianDTO politicianDTO = new PoliticiansDtoMapper().mapToDTO(politician);
		
		assertThat(politicianDTO,
				equalTo(politicianResponse.getBody()));
	}			
	
}
