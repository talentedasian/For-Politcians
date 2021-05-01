package com.example.demo.unit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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
		var politicianToBeSaved = new Politicians(0.00, "Rodrigo Duterte");
		Politicians politician = repo.save(politicianToBeSaved);
		
		assertThat(politicianToBeSaved, equalTo(politician));
	}
	
	@Test
	@Order(2)
	public void shouldThrowDataIntegrityException() {
		var politicianToBeSaved = new Politicians(0.00, "Rodrigo Duterte");
		
		assertThrows(DataIntegrityViolationException.class,
				() -> repo.saveAndFlush(politicianToBeSaved));
	}
	
}
