package com.example.demo.domain.domainModel;

import com.example.demo.domain.entities.PoliticianNumber;
import org.junit.jupiter.api.Test;

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
    public void shouldThrowIllegalStateExceptionIfNullIsPassed() {
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> new PoliticianNumber(null));

        assertThat(exception.getMessage())
                .containsIgnoringCase("cannot be null");
    }

    @Test
    public void shouldThrowIllegalStateExceptionIfOnlyWhitespaceIsPassed() {
        String WHITESPACE_ONLY_POLITICIAN_NUMBER = "    ";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> new PoliticianNumber(WHITESPACE_ONLY_POLITICIAN_NUMBER));

        assertThat(exception.getMessage())
                .containsIgnoringCase("cannot be null or empty");
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
        String EXPECTED_POLITICIAN_NUMBER = "RDPP-DRPP-00000000";

        assertThat(new PoliticianNumber(EXPECTED_POLITICIAN_NUMBER).politicianNumber())
                .isEqualTo(EXPECTED_POLITICIAN_NUMBER);
    }

}
