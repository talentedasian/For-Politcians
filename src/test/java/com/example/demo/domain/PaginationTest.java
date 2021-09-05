package com.example.demo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaginationTest {

    @Test
    public void shouldReturnPagedObjectWithPageNumberDefaultTo0() throws Exception{
        int pageNumber = Page.asDefault().pageNumber();

        assertThat(pageNumber)
                .isEqualTo(0);
    }

    @Test
    public void shouldReturnPagedObjectWithPageNumberSetTo0() throws Exception{
        int pageNumber = Page.of(0).pageNumber();

        assertThat(pageNumber)
                .isEqualTo(0);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWithMeaningfulMessageWhenPassingBelowZeroPageNumber() throws Exception{
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> Page.of(-1));

        assertThat(exception.getMessage())
                .containsIgnoringCase("should not be less than 0");
    }

    @Test
    public void shouldReturnPagedObjectWithPageNumberSetToDesiredPageNumberGivenOnParameter() throws Exception{
        assertThat(Page.of(2).pageNumber())
                .isEqualTo(2);
    }

    @Test
    public void shouldReturn1WhenCurrentPageIsZeroAndNextPageIsCalled() throws Exception{
        int pageNumber = Page.asDefault().nextPage().pageNumber();

        assertThat(pageNumber)
                .isEqualTo(1);
    }

    @Test
    public void shouldReturnExpectedPageWhenNextPageIsCalledWithNumberOfPagesDesired() throws Exception{
        int EXPECTED_PAGE_NUMBER = 7;

        Page firstPage = Page.asDefault().nextPage();

        assertThat(firstPage.nextPage(6).pageNumber())
                .isEqualTo(EXPECTED_PAGE_NUMBER);
    }

    @Test
    public void shouldReturnExpectedPageNumberWhenNextPageIsCalledMultipleTimes() throws Exception{
        int EXPECTED_PAGE_NUMBER = 4;

        int ACTUAL_PAGE_NUMBER = Page.asDefault().nextPage().nextPage().nextPage().nextPage().pageNumber();

        assertThat(ACTUAL_PAGE_NUMBER)
                .isEqualTo(EXPECTED_PAGE_NUMBER);
    }

}
