package com.example.demo.domain.domainModel;

import com.example.demo.domain.Score;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ScoreTest {

    @Test
    public void returnScoreSet() throws Exception{
        double VALID_SCORE = 9d;
        double score = com.example.demo.domain.Score.of(VALID_SCORE).rating();

        assertThat(score)
                .isEqualTo(VALID_SCORE);
    }

    @Test
    public void throwIllegalStateExceptionIf0() throws Exception{
        assertThatThrownBy(() -> Score.of(0)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void throwIllegalStateExceptionIfScoreIsLessThan0() throws Exception{
        assertThatThrownBy(() -> Score.of(-321)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void throwIllegalStateExceptionIfScoreIsGreaterThan10() throws Exception{
        assertThatThrownBy(() -> Score.of(11)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void throwIllegalStateExceptionIfScoreIsGreaterThan10RegardlessOfHowLargeItIs() throws Exception{
        assertThatThrownBy(() -> Score.of(3217321)).isInstanceOf(IllegalStateException.class);
    }

}
