package com.example.demo.unit.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.model.Politicians;
import com.example.demo.model.PoliticiansRating;
import com.example.demo.model.UserRater;
import com.example.demo.model.enums.PoliticalParty;
import com.example.demo.repository.PoliticiansRepository;
import com.example.demo.service.PoliticiansService;

@ExtendWith(SpringExtension.class)
public class PoliticianServiceTest {

	@Mock
	public PoliticiansRepository repo;
	
	public PoliticiansService service;
	
	public PoliticiansRating rating;
	public Politicians politician;
	
	@BeforeEach
	public void setup() {
		service = new PoliticiansService(repo);
		
		rating = new PoliticiansRating
				(1, 8.023D, 
				new UserRater("test", PoliticalParty.DDS),
				politician);
		politician =  new Politicians
				(null, 0.00D,
				"Mirriam Defensor", 
				List.of(rating),
				0.00D);
	}
	
	@Test
	public void verifyRepoCalledSaveMethod() {
		when(repo.save(politician)).thenReturn(politician); 
		
		service.savePolitician(new AddPoliticianDTORequest("Mirriam Defensor", BigDecimal.valueOf(0.00D)));
		
		verify(repo, times(1)).save(Mockito.any());
		
	}
}