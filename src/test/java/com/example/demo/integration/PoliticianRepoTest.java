package com.example.demo.integration;

import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.domain.averageCalculator.AverageCalculator;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicians.Politicians.PoliticiansBuilder;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import testAnnotations.DatabaseTest;

import static org.junit.jupiter.api.Assertions.*;

@DatabaseTest
public class PoliticianRepoTest extends BaseClassTestsThatUsesDatabase{

	@Autowired
	private PoliticiansRepository repo;
	
	@Mock
	public AverageCalculator calculator;

	final String POLITICIAN_NUMBER = "1111";
	
	PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
			.setFirstName("Rodrigo")
			.setLastName("Duterte")
			.setFullName()
			.setRating(new Rating(0.01D, 0.01D));

	PresidentialBuilder presidentialBuilder = new PoliticianTypes.PresidentialPolitician
			.PresidentialBuilder(politicianBuilder)
			.setMostSignificantLawPassed("Rice Law");
	SenatorialBuilder senatorialBuilder = new PoliticianTypes.SenatorialPolitician
			.SenatorialBuilder(politicianBuilder.setPoliticianNumber("2222"))
			.setTotalMonthsOfService(12)
			.setMostSignificantLawMade("Taxification Law");
	
	@Test
	public void shouldBeEqualOnSavedEntityForPresidential() throws PoliticianNotPersistableException {
		Politicians politicianToBeSaved = presidentialBuilder.setMostSignificantLawPassed("Rice Law")
				.build();
		
		Politicians politician = repo.save(presidentialBuilder.setBuilder(politicianBuilder.setPoliticianNumber(POLITICIAN_NUMBER)).build());
		
		assertEquals(politicianToBeSaved, politician);
	}
	
	@Test
	public void shouldBeEqualOnSavedEntityForSenatorial() throws PoliticianNotPersistableException {
		Politicians politicianToBeSaved = senatorialBuilder.build();

		Politicians politician = repo.save(politicianToBeSaved);

		assertEquals(politicianToBeSaved, politician);
	}
	
	@Test
	public void assertExistsByPoliticianNumberQueryByPresidential() throws PoliticianNotPersistableException {
		Politicians politicianToBeSaved = presidentialBuilder.setBuilder(politicianBuilder.setPoliticianNumber("9898"))
				.build();
		
		repo.save(politicianToBeSaved);
		
		assertTrue(repo.existsByPoliticianNumber(politicianToBeSaved.retrievePoliticianNumber()));
	}
	
	@Test
	public void assertExistsByPoliticianNumberQueryBySenatorial() throws PoliticianNotPersistableException {
		Politicians politicianToBeSaved = senatorialBuilder.setBuilder(politicianBuilder.setPoliticianNumber("9898"))
				.build();
		
		repo.save(politicianToBeSaved);
		
		assertTrue(repo.existsByPoliticianNumber(politicianToBeSaved.retrievePoliticianNumber()));
	}
	
	@Test
	public void assertDeleteByPoliticianNumberQueryBySenatorial() throws PoliticianNotPersistableException {
		Politicians politicianToBeSaved = senatorialBuilder.build();
		repo.save(politicianToBeSaved);
		
		String id = politicianToBeSaved.retrievePoliticianNumber();
		
		assertTrue(repo.existsByPoliticianNumber(id));
		repo.deleteByPoliticianNumber(id);
		assertFalse(repo.existsByPoliticianNumber(id));
	}
	
	@Test
	public void assertDeleteByPoliticianNumberQueryByPresidential() throws PoliticianNotPersistableException {
		Politicians politicianToBeSaved = presidentialBuilder.setBuilder(politicianBuilder.setPoliticianNumber("random number"))
				.build();
		repo.save(politicianToBeSaved);
		
		String id = politicianToBeSaved.retrievePoliticianNumber();
		
		assertTrue(repo.existsByPoliticianNumber(id));
		repo.deleteByPoliticianNumber(id);
		assertFalse(repo.existsByPoliticianNumber(id));
	}
	
}
