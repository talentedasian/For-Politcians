package com.example.demo.domain.domainModel;

import com.example.demo.domain.Score;
import com.example.demo.domain.ScoreHasExceededMaximumValueException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Tag("Domain")
public class ScoreTest {

    @Test
    public void returnScoreSet() throws Exception{
        String VALID_SCORE = "9";
        String score = com.example.demo.domain.Score.of(VALID_SCORE).rating();

        assertThat(score)
                .isEqualTo(VALID_SCORE);
    }

    @Test
    public void throwIllegalStateExceptionIf0() throws Exception{
        assertThatThrownBy(() -> Score.of("0")).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void throwIllegalStateExceptionIfScoreIsLessThan0() throws Exception{
        assertThatThrownBy(() -> Score.of("-321")).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void throwScoreHasExceededMaximumValueExceptionIfScoreIsGreaterThan10() throws Exception{
        assertThatThrownBy(() -> Score.of("11")).isInstanceOf(ScoreHasExceededMaximumValueException.class);
    }

    @Test
    public void anythingLargerThan0AndIsWithinMaximumIsValid() throws Exception{
        assertThatCode(() -> Score.of("0.1")).doesNotThrowAnyException();
    }

    @Test
    public void throwScoreHasExceededMaximumValueExceptionIfScoreIsGreaterThan10RegardlessOfHowLargeItIs() throws Exception{
        assertThatThrownBy(() -> Score.of("3217321")).isInstanceOf(ScoreHasExceededMaximumValueException.class);
    }

}
