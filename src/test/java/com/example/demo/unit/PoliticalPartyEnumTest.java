package com.example.demo.unit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.example.demo.model.enums.PoliticalParty;

public class PoliticalPartyEnumTest {

	@Test
	public void shouldMapToPoliticalParty() {
		PoliticalParty politicalParty = PoliticalParty.mapToPoliticalParty("dds");
		
		assertThat(politicalParty,
				equalTo(PoliticalParty.DDS));
	}
}
