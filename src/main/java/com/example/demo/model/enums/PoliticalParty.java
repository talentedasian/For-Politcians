package com.example.demo.model.enums;

public enum PoliticalParty {
	DDS, DILAWAN, GREY_ZONE;
	
	public static PoliticalParty mapToPoliticalParty(String party) {
		PoliticalParty politicalParty = GREY_ZONE;
		for (PoliticalParty politicalParties : PoliticalParty.values()) {
			if (party.equalsIgnoreCase(politicalParties.toString())) {
				politicalParty = politicalParties;
			}
		}
		
		return politicalParty;
	}
}
