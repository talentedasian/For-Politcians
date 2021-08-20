package com.example.demo.unit.entities;

import com.example.demo.domain.politicians.Name;
import com.example.demo.domain.politicians.PoliticianNumber;
import org.junit.jupiter.api.Test;

import static com.example.demo.domain.politicianNumber.PoliticianNumberImplementor.with;
import static com.example.demo.domain.politicians.Politicians.Type.PRESIDENTIAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PoliticianNumberTest {

    @Test
    public void shouldThrowIllegalStateExceptionIfDoesNotContainThreeHyphens() {
        String POLITICIAN_NUMBER_WITH_INSUFFICIENT_HYPHEN = "RDPP-DRPP0000";

        assertThrows(IllegalStateException.class, () -> new PoliticianNumber(POLITICIAN_NUMBER_WITH_INSUFFICIENT_HYPHEN));
    }

    @Test
    public void shouldCreateInstanceOfPoliticianNumberWhenConstraintsAreMet() {
        String EXPECTED_POLITICIAN_NUMBER = with(new Name("firstname", "lastname"), PRESIDENTIAL).calculateEntityNumber().getPoliticianNumber();

        assertThat(new PoliticianNumber(EXPECTED_POLITICIAN_NUMBER).politicianNumber())
                .isEqualTo(EXPECTED_POLITICIAN_NUMBER);
    }

}
