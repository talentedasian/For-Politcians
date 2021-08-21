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
    public void shouldThrowIllegalStateExceptionIfDoesNotContain2Hyphens() {
        String POLITICIAN_NUMBER_WITH_INSUFFICIENT_HYPHEN = "RDPP-DRPP0000";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> new PoliticianNumber(POLITICIAN_NUMBER_WITH_INSUFFICIENT_HYPHEN));

        assertThat(exception.getMessage())
                .containsIgnoringCase("insufficient amount of separators");
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfSeparatorsAreWronglyPlaced() {
        String POLITICIAN_NUMBER_WITH_WRONGLY_PLACED_SEPARATOR = "RDPP-DRPP000-0";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> new PoliticianNumber(POLITICIAN_NUMBER_WITH_WRONGLY_PLACED_SEPARATOR));

        assertThat(exception.getMessage())
                .containsIgnoringCase("wrongly placed");
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfLastSectionContainsNonDigits() {
        String POLITICIAN_NUMBER_LAST_SECTION_CONTAINS_NON_DIGITS = "RDPP-DRPP-0000dasd";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> new PoliticianNumber(POLITICIAN_NUMBER_LAST_SECTION_CONTAINS_NON_DIGITS));

        assertThat(exception.getMessage())
                .containsIgnoringCase("contains invalid characters");
    }

    @Test
    public void shouldCreateInstanceOfPoliticianNumberWhenConstraintsAreMet() {
        String EXPECTED_POLITICIAN_NUMBER = with(new Name("firstname", "lastname"), PRESIDENTIAL).calculateEntityNumber().getPoliticianNumber();

        assertThat(new PoliticianNumber(EXPECTED_POLITICIAN_NUMBER).politicianNumber())
                .isEqualTo(EXPECTED_POLITICIAN_NUMBER);
    }

}
