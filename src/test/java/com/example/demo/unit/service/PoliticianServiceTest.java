package com.example.demo.unit.service;

import com.example.demo.adapter.in.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.adapter.in.dtoRequest.AddSenatorialPoliticianDTORequest;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicianNumber.PoliticianNumberImplementor;
import com.example.demo.adapter.out.repository.FakePoliticianRepository;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.in.service.PoliticiansService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class PoliticianServiceTest {

	public PoliticiansRepository politicianRepo;

	public PoliticiansService politicianService;
	public Politicians politician;
	public AddPoliticianDTORequest politicianDtoRequest;

	public final String FIRST_NAME = "Miriam Palma";
	public final String LAST_NAME = "Defensor-Santiago";

	@BeforeEach
	public void setup() {
		politicianRepo = new FakePoliticianRepository();

		politicianService = new PoliticiansService(politicianRepo);

		politician = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(new Politicians.PoliticiansBuilder("dummy")
					.setId(1)
					.setFirstName(FIRST_NAME)
					.setLastName(LAST_NAME))
				.setTotalMonthsOfService(12)
				.build();

		politicianDtoRequest = new AddSenatorialPoliticianDTORequest
					(FIRST_NAME,
					LAST_NAME,
					BigDecimal.valueOf(0.01D),
					12,
					"Anti Corrupt Law");
	}

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
