package com.example.demo.domain.domainModel;

import com.example.demo.domain.Score;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ScoreTest {

    @Test
    public void returnRatingSet() throws Exception{
        double RATING_LESS_THAN_10 = 9d;
        double rating = Score.of(RATING_LESS_THAN_10).rating();

        assertThat(rating)
                .isEqualTo(RATING_LESS_THAN_10);
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
