package com.example.demo.unit.entities;

import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.userRaterNumber.AbstractUserRaterNumber;
import com.example.demo.domain.userRaterNumber.facebook.FacebookAccountNumberCalculator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountNumberTest {

    final String NAME = "Jitter Ted";

    final String FACEBOOK_ID = "21312";

    AbstractUserRaterNumber accNumberCalc = FacebookAccountNumberCalculator.with(NAME, FACEBOOK_ID);

    final String ACCOUNT_NUMBER = new AccountNumber(accNumberCalc.calculateEntityNumber().getAccountNumber()).accountNumber();

    @Test
    public void shouldThrowIllegalStateExceptionIfNoHyphen() {
        String ACCOUNT_NUMBER_WITHOUT_HYPHEN = "DFFLM".concat(FACEBOOK_ID);

        assertThrows(IllegalStateException.class, () -> new AccountNumber(ACCOUNT_NUMBER_WITHOUT_HYPHEN));
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfOnlyContainsId() {
        String ACCOUNT_NUMBER_WITHOUT_LEFT_SIDE = FACEBOOK_ID;

        assertThrows(IllegalStateException.class, () -> new AccountNumber(ACCOUNT_NUMBER_WITHOUT_LEFT_SIDE));
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfLeftSideLessThanFiveInLength() {
        String ACCOUNT_NUMBER_WITH_NO_MORE_FIVE_LENGTH_IN_LEFT_SIDE = "DFLM".concat(FACEBOOK_ID);

        assertThrows(IllegalStateException.class, () -> new AccountNumber(ACCOUNT_NUMBER_WITH_NO_MORE_FIVE_LENGTH_IN_LEFT_SIDE));
    }

    @Test
    public void shouldCreateInstanceOfAccountNumberWrappingTheActualAccountNumber() {
        String EXPECTED_ACCOUNT_NUMBER = accNumberCalc.calculateEntityNumber().getAccountNumber();

        assertThat(new AccountNumber(ACCOUNT_NUMBER).accountNumber())
                .isEqualTo(EXPECTED_ACCOUNT_NUMBER);
    }

}
