package com.example.demo.domain.entities;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public record AccountNumber(String accountNumber) {

    /*OP stands for the Oauth2 Provider that is used for logging into the application.
     * If facebook is used as the login mechanism or OP, OP will be replaced to FLM which
     * stands for Facebook Login Mechanism. FL stands for the first two initials of your facebook
     * fullName. For example your facebook fullName is "test fullName politics", FL will be replaced to 'T' and 'N'.
     * If your facebook fullName is only made up of one word, FL will then be replaced with the first letter
     * of your fullName appended with "G".
     */
    public static final String pattern = "FLOP-00000000000000";

    public AccountNumber(String accountNumber) {
        Assert.state(accountNumber != null | StringUtils.hasText(accountNumber), "account number cannot be null or empty");
        doesAccountNumberContainHyphen(accountNumber);
        doesAccountNumberHaveSufficientLengthOnFirstSection(accountNumber);

        this.accountNumber = accountNumber;
    }

    private void doesAccountNumberHaveSufficientLengthOnFirstSection(String accountNumber) {
        Assert.state(!(accountNumber.split("-")[0].length() < 5), "account number does not have sufficient length for the first section");
    }

    private void doesAccountNumberContainHyphen(String accountNumber) {
        Assert.state(accountNumber.contains("-"), "account number does not contain separator");
    }

    public static boolean isValid(String accountNumber) {
        return new AccountNumber(accountNumber).isValid(accountNumber);
    }

}
