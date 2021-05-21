package com.example.demo.integration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.demo.model.Politicians;
import com.example.demo.repository.PoliticiansRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PoliticianRepoTest {

	@Autowired
	private PoliticiansRepository repo;
	
	@Test
	public void shouldBeEqualOnSavedEntity() {
		var politicianToBeSaved = new Politicians();
		politicianToBeSaved.setRating(0.00D);
		politicianToBeSaved.setFirstName("Rodrigo");
		politicianToBeSaved.setLastName("Duterte");
		
		Politicians politician = repo.save(politicianToBeSaved);
		
		assertThat(politicianToBeSaved.getFirstName(), 
				equalTo(politician.getFirstName()));
		assertThat(politicianToBeSaved.getLastName(), 
				equalTo(politician.getLastName()));
		assertThat(politicianToBeSaved.getRating(), 
				equalTo(politician.getRating()));
	}
	
	@Test
	public void shouldThrowDataIntegrityException() {
		var politicianToBeSaved = new Politicians();
		politicianToBeSaved.setRating(0.00D);
		politicianToBeSaved.setFirstName("Rodrigo");
		politicianToBeSaved.setLastName("Duterte");
		
		assertThrows(DataIntegrityViolationException.class,
				() -> repo.saveAndFlush(politicianToBeSaved));
	}
	
}
