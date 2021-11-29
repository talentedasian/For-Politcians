package com.example.demo.domain.domainModel;

import com.example.demo.domain.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.domain.entities.PoliticianNumber;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("Domain")
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

    @Test
    public void test() throws Exception{
        LowSatisfactionAverageCalculator calc = new LowSatisfactionAverageCalculator("6.6663641", 3);

//                .isEqualTo(wilfred(List.of("1.324","3.2312321","2.111132")));
    }

    public String wilfred(List<String> summands) {
        BigDecimal mean = BigDecimal.ZERO;
        for (int i = 0; i < summands.size(); i++) {
            BigDecimal difference = new BigDecimal(summands.get(i)).subtract(mean);
            BigDecimal delta = difference.divide(new BigDecimal(i + 1), 3, RoundingMode.CEILING);
            mean = delta.add(mean);
        }

        return mean.toString();
    }

}
