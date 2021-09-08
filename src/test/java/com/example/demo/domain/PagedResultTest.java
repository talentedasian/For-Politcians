package com.example.demo.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PagedResultTest {

    @Test
    public void obtainingSpecifiedPagedObjectOfPageShouldReturnCorrectPagedObject() throws Exception{
        List<String> contents = List.of("random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18");

        List<String> EXPECTED_CONTENTS_AFTER_OBTAINING_FROM_PAGE = contents.stream().skip(10).toList();

        PagedResult<String> pagedResult = PagedResult.of(contents);

        PagedObject<String> pagedObject = pagedResult.ofPage(Page.of(1));

        assertThat(pagedObject)
                .isEqualTo(PagedObject.of(EXPECTED_CONTENTS_AFTER_OBTAINING_FROM_PAGE, Page.of(1), pagedResult));
    }

    @Test
    public void hasPageForShouldReturnTrueWhenContentSizeIsGreaterThanItemsSkippedInASinglePage() throws Exception{
        Page pageOf1 = Page.of(1);

        List<String> contents = List.of("random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18");

        boolean hasPageForPageOf1 = PagedResult.of(contents).hasPageFor(pageOf1);

        assertThat(hasPageForPageOf1)
                .isTrue();
    }

    @Test
    public void hasPageForShouldReturnTrueForMultiplePages() throws Exception{
        Page pageOf3 = Page.of(3);

        List<String> contents = List.of
                ("random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18",
                "random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18",
                "random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18");

        boolean hasPageForPageOf3 = PagedResult.of(contents).hasPageFor(pageOf3);

        assertThat(hasPageForPageOf3)
                .isTrue();
    }

    @Test
    public void firstPageReturnsPagedObjectContainingContentsFromFirst10() throws Exception{
        List<String> contents = List.of("random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18");

        PagedObject<String> pagedObject = PagedResult.of(contents).firstPage();

        List<String> EXPECTED_FIRST_PAGE_PAGEDOBJECT = contents.stream().limit(10).toList();

        assertThat(pagedObject)
                .isEqualTo(PagedObject.of(EXPECTED_FIRST_PAGE_PAGEDOBJECT, Page.asZero(), PagedResult.of(contents)));
    }

    @Test
    public void lastPageShouldReturnPageOf1() throws Exception{
        List<String> contents = List.of("random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18");

        PagedObject<String> pagedObject = PagedResult.of(contents).lastPage();

        assertThat(pagedObject.currentPageNumber())
                .isEqualTo(1);
    }

    @Test
    public void firstPageShouldAlwaysReturn0RegardlessOfContentSize() throws Exception{
        List<String> contents = List.of("random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18", "random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10");

        int firstPageNumber = PagedResult.of(contents).firstPage().currentPageNumber();

        assertThat(firstPageNumber)
                .isEqualTo(0);
    }

    @Test
    public void lastPageShouldReturnPageOf2IfContentSizeIsGreaterThan30AndLessThan40() throws Exception{
        List<String> contents = List.of("random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18", "random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18");

        PagedObject<String> pagedObject = PagedResult.of(contents).lastPage();

        assertThat(pagedObject.currentPageNumber())
                .isEqualTo(3);
    }

    @Test
    public void totalPagesShouldReturn4AndIsNotZeroBasedEvenThoughTheFirstPageIs0() throws Exception{
        List<String> contents = List.of("random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18", "random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18");

        int totalPage = PagedResult.of(contents).totalPages();

        assertThat(totalPage)
                .isEqualTo(4);
    }

    @Test
    public void anEmptyPagedResultContentShouldReturn0AsTotalPages() throws Exception{
        int totalPage = PagedResult.of(List.of()).totalPages();

        assertThat(totalPage)
                .isEqualTo(0);
    }

}
