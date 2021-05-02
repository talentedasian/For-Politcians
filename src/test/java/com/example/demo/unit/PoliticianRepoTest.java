package com.example.demo.unit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Commit;

import com.example.demo.model.Politicians;
import com.example.demo.repository.PoliticiansRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PoliticianRepoTest {

	@Autowired
	private PoliticiansRepository repo;
	
	@Test
	@Commit
	@Order(1)
	public void shouldBeEqualOnSavedEntity() {
		var politicianToBeSaved = new Politicians();
		politicianToBeSaved.setRating(0.00D);
		politicianToBeSaved.setName("Rodrigo Duterte");
		
		Politicians politician = repo.save(politicianToBeSaved);
		
		assertThat(politicianToBeSaved, equalTo(politician));
	}
	
	@Test
	@Order(2)
	public void shouldThrowDataIntegrityException() {
		var politicianToBeSaved = new Politicians();
		politicianToBeSaved.setRating(0.00D);
		politicianToBeSaved.setName("Rodrigo Duterte");
		
		assertThrows(DataIntegrityViolationException.class,
				() -> repo.saveAndFlush(politicianToBeSaved));
	}
	
}
