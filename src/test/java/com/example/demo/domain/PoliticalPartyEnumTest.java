package com.example.demo.domain;

import com.example.demo.domain.enums.PoliticalParty;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.example.demo.domain.enums.PoliticalParty.DDS;
import static com.example.demo.domain.enums.PoliticalParty.GREY_ZONE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Tag("Domain")
public class PoliticalPartyEnumTest {

	@Test
	public void shouldMapToPoliticalPartyWhenPartyIsSpecifiedEnums() {
		PoliticalParty politicalParty = PoliticalParty.value("DDS");
		
		assertThat(politicalParty,
				equalTo(DDS));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "dsaqd", "random", "no sense" })
	public void shouldMapToGreyZonePoliticalPartyWhenPartyIsNotOnSpecifiedEnums(String party) {
		PoliticalParty politicalParty = PoliticalParty.value(party);
		
		assertThat(politicalParty,
				equalTo(GREY_ZONE));
	}
	
}
