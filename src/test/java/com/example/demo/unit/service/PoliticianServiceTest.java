package com.example.demo.unit.service;

import com.example.demo.baseClasses.AbstractEntitiesServiceTest;
import com.example.demo.model.entities.politicians.Politicians;
import com.example.demo.model.politicianNumber.PoliticianNumberImplementor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class PoliticianServiceTest extends AbstractEntitiesServiceTest{

	@Test
	public void verifyRepoSaveMethodActuallySavesPolitician() {
		politicianRepo.save(politician);

		Politicians politicianSaved = politicianService.savePolitician(politicianDtoRequest);

		Optional<Politicians> politicianQueried = politicianRepo.findByPoliticianNumber(politicianSaved.getPoliticianNumber());

		assertThat(politicianQueried)
				.isNotEmpty()
				.get()
				.isEqualTo(politician);
	}

	/*	verifies if the politician number has been changed and/or is correct when saving this politician
	 *
	 *
	*/  
	@Test
	public void verifyPoliticianNumberIsCorrectWhenSavingPolitcian() {
		String polNumber = PoliticianNumberImplementor.with(politician).calculateEntityNumber().getPoliticianNumber();

		Politicians politicianSaved = politicianService.savePolitician(politicianDtoRequest);

		assertEquals(polNumber, politicianSaved.getPoliticianNumber());
	}
	
}
