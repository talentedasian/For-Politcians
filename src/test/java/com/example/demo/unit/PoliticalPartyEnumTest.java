package com.example.demo.unit;

import static com.example.demo.model.enums.PoliticalParty.DDS;
import static com.example.demo.model.enums.PoliticalParty.GREY_ZONE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.demo.model.enums.PoliticalParty;

public class PoliticalPartyEnumTest {

	@Test
	public void shouldMapToPoliticalPartyWhenPartyIsSpecifiedEnums() {
		PoliticalParty politicalParty = PoliticalParty.mapToPoliticalParty("dds");
		
		assertThat(politicalParty,
				equalTo(DDS));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "dsaqd", "random", "no sense" })
	public void shouldMapToGreyZonePoliticalPartyWhenPartyIsNotOnSpecifiedEnums(String party) {
		PoliticalParty politicalParty = PoliticalParty.mapToPoliticalParty(party);
		
		assertThat(politicalParty,
				equalTo(GREY_ZONE));
	}
	
}
