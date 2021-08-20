package com.example.demo.domain.politicians;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public record PoliticianNumber(String politicianNumber) {

    /*
		F stands for Firstname
		L stands for Lastname
		T stands for what type the politician is e.g. Presidential
		The leading zeroes are the last 4 numbers of the hashcode of the politician
	 */
    public static final String pattern = "FLTT-LFTT-0000";

    public PoliticianNumber(String politicianNumber) {
        Assert.state(politicianNumber == null || StringUtils.hasText(politicianNumber), "politician number cannot be null or empty");
        this.politicianNumber = politicianNumber;
        Assert.state(isValid(), "politician number invalid");
    }

    private boolean isValid() {
        return isPlaceMentOfHyphenCorrect() && lastSectionOnlyContainsDigits();
    }

    private boolean isPlaceMentOfHyphenCorrect() {
        if (!politicianNumber.contains("-")) {
            return false;
        }
        try {
            return politicianNumber.charAt(4) == '-' && politicianNumber.charAt(9) == '-';
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean lastSectionOnlyContainsDigits() {
        return politicianNumber.split("-")[2].matches("[0-9]+");
    }

}
