package com.example.demo.model.enums;

import java.util.Arrays;

public enum PoliticalParty {
	DDS, DILAWAN, GREY_ZONE;
	
	public static PoliticalParty mapToPoliticalParty(String party) {
		return Arrays.stream(PoliticalParty.values())
			.filter(polParty -> party.equalsIgnoreCase(polParty.toString()))
			.findAny().orElse(GREY_ZONE);
	}
}
