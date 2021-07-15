package com.example.demo.unit.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.baseClasses.AbstractEntitiesServiceTest;
import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.model.entities.Politicians;

@ExtendWith(SpringExtension.class)
public class PoliticianServiceTest extends AbstractEntitiesServiceTest{
	
	@Test
	public void verifyRepoCalledSaveMethod() {
		when(politicianRepo.save(politician)).thenReturn(politician); 
		when(politicianRepo.countByLastNameAndFirstName("Mirriam", "Defensor")).thenReturn(1L);
		politicianService.savePolitician(new AddPoliticianDTORequest("Mirriam", "Defensor", BigDecimal.valueOf(0.01D)));
		
		verify(politicianRepo, times(1)).save(Mockito.any());	
	}
	
	@Test
	public void shouldAddTotalRatingAndCorrectAverageRating() {
		when(calculator.calculateAverage()).thenReturn(0.01D);
		politician.calculateAverageRating();
		
		when(politicianRepo.findByPoliticianNumber(polNumber)).thenReturn(Optional.of(politician));
		
		Politicians politicianQueried = politicianService.findPoliticianByNumber(polNumber);
		
		assertThat(0.01D,
				equalTo(politicianQueried.getRating().getAverageRating()));
	}
	
	@Test
	public void shouldEqualDTOOutputs() {
		when(politicianRepo.findByPoliticianNumber(polNumber)).thenReturn(Optional.of(politician));
		
		Politicians politicianQueried = politicianService.findPoliticianByNumber(polNumber);
		
		assertDtoOutputsUtil(politician, politicianQueried);
	}
	
	@Test
	public void shouldReturnTrueWhenDeletedByPoliticianNumber() {
		when(politicianRepo.existsByPoliticianNumber(polNumber)).thenReturn(true);
		
		assertTrue(politicianService.deletePolitician(polNumber));
	}
	
	@Test
	public void shouldEqualDTOOutputsWhenSaved() {
		when(politicianRepo.save(any(Politicians.class))).thenReturn(politician); 
		when(politicianRepo.countByLastNameAndFirstName("Mirriam", "Defensor")).thenReturn(1L);
		
		Politicians politicianQueried = politicianService.savePolitician(politicianDtoRequest);
		
		assertDtoOutputsUtil(politician, politicianQueried);
	}
	
	public void assertDtoOutputsUtil(Politicians politicianToAssert, Politicians politicianToCompare) {
		assertThat(politicianToAssert.getFirstName(),
				equalTo(politicianToCompare.getFirstName()));
		assertThat(politicianToAssert.getLastName(),
				equalTo(politicianToCompare.getLastName()));
		assertThat(politicianToAssert.getRating().getAverageRating(),
				equalTo(politicianToCompare.getRating().getAverageRating()));
		assertThat(politicianToAssert.getRating().getTotalRating(),
				equalTo(politicianToCompare.getRating().getTotalRating()));
	}
	
}