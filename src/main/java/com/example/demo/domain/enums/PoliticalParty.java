package com.example.demo.domain.enums;

public enum PoliticalParty {
	DDS("dds, DDS"), DILAWAN("dilawan, DILAWAN"), GREY_ZONE("grey_zone, GREY_ZONE");

	PoliticalParty(String s) {
	}

	public static PoliticalParty value(String party) {
		try {
			return PoliticalParty.valueOf(party);
		} catch (IllegalArgumentException e) {
			return GREY_ZONE;
		}
	}


}
