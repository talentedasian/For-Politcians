package com.example.demo.unit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Politicians;
import com.example.demo.repository.PoliticiansRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PoliticianRepoTest {

	@Autowired
	private PoliticiansRepository repo;
	
	@Test
	public void shouldBeEqualOnSavedEntity() {
		var politicianToBeSaved = new Politicians(0.00, "Rodrigo Duterte");
		Politicians politician = repo.save(politicianToBeSaved);
		
		assertThat(politicianToBeSaved, equalTo(politician));
	}
}
