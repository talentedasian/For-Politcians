package com.example.demo.domain.pagination;

import com.example.demo.domain.Page;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("Domain")
public class PageTest {

    @Test
    public void shouldReturnPagedObjectWithPageNumberDefaultTo0() throws Exception{
        int pageNumber = Page.asZero().pageNumber();

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
        int pageNumber = Page.asZero().nextPage().pageNumber();

        assertThat(pageNumber)
                .isEqualTo(1);
    }

    @Test
    public void shouldReturnExpectedPageWhenNextPageIsCalledWithNumberOfPagesDesired() throws Exception{
        int EXPECTED_PAGE_NUMBER = 7;

        Page firstPage = Page.asZero().nextPage();

        assertThat(firstPage.nextPage(6).pageNumber())
                .isEqualTo(EXPECTED_PAGE_NUMBER);
    }

    @Test
    public void shouldReturnExpectedPageNumberWhenNextPageIsCalledMultipleTimes() throws Exception{
        int EXPECTED_PAGE_NUMBER = 4;

        int ACTUAL_PAGE_NUMBER = Page.asZero().nextPage().nextPage().nextPage().nextPage().pageNumber();

        assertThat(ACTUAL_PAGE_NUMBER)
                .isEqualTo(EXPECTED_PAGE_NUMBER);
    }

    @Test
    public void itemsToSkipShouldReturnPageNumberMultipliedToHowManyItemsToSkipIsSpecified() throws Exception{
        int EXPECTED_ITEMS_TO_SKIP = 20;

        Page page = Page.of(2);

        assertThat(page.itemsToSkip(10))
                .isEqualTo(EXPECTED_ITEMS_TO_SKIP);
    }

    @Test
    public void itemsToSkipShouldReturn9WhenMultipleIs3AndPageNumberIs2() throws Exception{
        Page page = Page.of(2);

        assertThat(page.itemsToSkip(3))
                .isEqualTo(6);
    }

}
