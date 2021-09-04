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
        if (politicianNumber == null || !StringUtils.hasText(politicianNumber)) throw new IllegalStateException("politician number cannot be null or empty");
        this.politicianNumber = politicianNumber;
        isValid();
    }

    public static void tryParse(String politicianNumber) {
        new PoliticianNumber(politicianNumber);
    }

    public static PoliticianNumber of(String politicianNumber) {
        return new PoliticianNumber(politicianNumber);
    }

    private void isValid() {
        isNumberOfSeparatorsCorrect();
        isPlaceMentOfSeparatorsCorrect();
        lastSectionOnlyContainsDigits();
    }

    private void isNumberOfSeparatorsCorrect() {
        long separatorCount = politicianNumber.chars().filter(it -> it == '-').count();
        Assert.state(separatorCount == 2, "politician number has insufficient amount of separators. only "
                + separatorCount + " separators found");
    }

    private void isPlaceMentOfSeparatorsCorrect() {
        Assert.state(politicianNumber.contains("-"), "politician number does not contain a separator");
        try {
            Assert.state(politicianNumber.charAt(4) == '-' && politicianNumber.charAt(9) == '-', "separators are wrongly placed");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalStateException("politician number does not meet minimum length");
        }
    }

    private void lastSectionOnlyContainsDigits() {
        Assert.state(politicianNumber.split("-")[2].matches("[0-9]+"),
                "last section of politician number contains invalid characters. should only contain digits");
    }

}
