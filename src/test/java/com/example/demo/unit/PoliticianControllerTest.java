package com.example.demo.unit;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.controller.PoliticianController;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.model.Politicians;
import com.example.demo.service.PoliticiansService;

@ExtendWith(SpringExtension.class)
public class PoliticianControllerTest {

	@Mock
	public PoliticiansService service;
	public PoliticianController controller;
	public Politicians politician;
	
	@BeforeEach
	public void setUp() {
		controller = new PoliticianController(service);
		politician =  new Politicians(9.67D, "Mirriam Defensor");
	}
	
	@Test
	public void shouldCallSaveFromServiceWhenSaved() {
		var politicianDTORequest = new AddPoliticianDTORequest
				("Mirriam Defensor", 
				BigDecimal.valueOf(9.67D));
		when(service.savePolitician(politicianDTORequest)).thenReturn(politician);
		controller.savePolitician(politicianDTORequest);
		
		verify(service, times(1)).savePolitician(politicianDTORequest);
	}
	
}
