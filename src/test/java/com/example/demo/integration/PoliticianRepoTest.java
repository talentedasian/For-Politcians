package com.example.demo.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Commit;

import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.model.averageCalculator.AverageCalculator;
import com.example.demo.model.entities.Politicians;
import com.example.demo.model.entities.Politicians.PoliticiansBuilder;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.model.entities.politicians.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.repository.PoliticiansRepository;

import testAnnotations.DatabaseTest;

@DatabaseTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PoliticianRepoTest extends BaseClassTestsThatUsesDatabase{

	@Autowired
	private PoliticiansRepository repo;
	
	@Mock
	public AverageCalculator calculator;
	
	PoliticiansBuilder politicianBuilder = new Politicians.PoliticiansBuilder("1111")
			.setFirstName("Rodrigo")
			.setLastName("Duterte")
			.setFullName()
			.setRating(new Rating(0.01D, 0.01D, calculator));

	PresidentialBuilder presidentialBuilder = new PoliticianTypes.PresidentialPolitician
			.PresidentialBuilder(politicianBuilder)
			.setMostSignificantLawPassed("Rice Law");
	SenatorialBuilder senatorialBuilder = new PoliticianTypes.SenatorialPolitician
			.SenatorialBuilder(politicianBuilder.setPoliticianNumber("2222"))
			.setTotalMonthsOfService(12)
			.setMostSignificantLawMade("Taxification Law");
	
	@Test
	@Order(1)
	@Commit
	public void shouldBeEqualOnSavedEntityForPresidential() {
		Politicians politicianToBeSaved = presidentialBuilder.setMostSignificantLawPassed("Rice Law")
				.build();
		
		Politicians politician = repo.save(politicianToBeSaved);
		
		assertEquals(politicianToBeSaved, politician);
	}
	
	@Test
	public void shouldBeEqualOnSavedEntityForSenatorial() {
		Politicians politicianToBeSaved = senatorialBuilder.build();
		
		Politicians politician = repo.save(politicianToBeSaved);
		
		assertEquals(politicianToBeSaved, politician);
	}
	
	@Test
	@Order(2)
	public void shouldThrowDataIntegrityExceptionWhenPoliticianNumberIsTheSame() {
		Politicians politicianToBeSaved = politicianBuilder.build();
		
		assertThrows(DataIntegrityViolationException.class,
				() -> repo.saveAndFlush(politicianToBeSaved));
	}
	
	@Test
	public void assertExistsByPoliticianNumberQueryByPresidential() {
		Politicians politicianToBeSaved = presidentialBuilder.setBuilder(politicianBuilder.setPoliticianNumber("9898"))
				.buildWithDifferentBuilder();
		
		repo.save(politicianToBeSaved);
		
		assertTrue(repo.existsByPoliticianNumber(politicianToBeSaved.getPoliticianNumber()));
	}
	
	@Test
	public void assertExistsByPoliticianNumberQueryBySenatorial() {
		Politicians politicianToBeSaved = senatorialBuilder.setBuilder(politicianBuilder.setPoliticianNumber("9898"))
				.buildWithDifferentBuilder();
		
		repo.save(politicianToBeSaved);
		
		assertTrue(repo.existsByPoliticianNumber(politicianToBeSaved.getPoliticianNumber()));
	}
	
	@Test
	public void assertDeleteByPoliticianNumberQueryBySenatorial() {
		Politicians politicianToBeSaved = senatorialBuilder.build();
		repo.save(politicianToBeSaved);
		
		String id = politicianToBeSaved.getPoliticianNumber();
		
		assertTrue(repo.existsByPoliticianNumber(id));
		repo.deleteByPoliticianNumber(id);
		assertFalse(repo.existsByPoliticianNumber(id));
	}
	
	@Test
	public void assertDeleteByPoliticianNumberQueryByPresidential() {
		Politicians politicianToBeSaved = presidentialBuilder.setBuilder(politicianBuilder.setPoliticianNumber("random number"))
				.buildWithDifferentBuilder();
		repo.save(politicianToBeSaved);
		
		String id = politicianToBeSaved.getPoliticianNumber();
		
		assertTrue(repo.existsByPoliticianNumber(id));
		repo.deleteByPoliticianNumber(id);
		assertFalse(repo.existsByPoliticianNumber(id));
	}
	
}
