package com.example.demo.domain.pagination;

import com.example.demo.domain.NotLastPageException;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class TotalPaginatedObjectTest {

    @Test
    public void shouldThrowIllegalStateExceptionWhenCreatingPagedObjectWithNullValues() throws Exception{
        assertThrows(IllegalStateException.class, () -> PagedObject.of(null, 10, 10));
    }

    @Test
    public void pagedObjectShouldReturnExpectedContentSize() throws Exception{
        int pagedObjectSize = PagedObject.<String>of(List.of(), 10, 10).pagedObjectSize();

        assertThat(pagedObjectSize)
                .isEqualTo(List.of().size());
    }

    @Test
    public void shouldReturn1AsTotalPageWhenTotalIs0() throws Exception{
        long total = 0;
        long actualTotal = PagedObject.of(List.of(), total, 1).totalPages();

        assertThat(actualTotal)
                .isEqualTo(1);
    }

    @Test
    public void shouldReturnExpectedTotalPagesWhenTotalIsSetLargerThan0() throws Exception{
        long EXPECTED_TOTAL_NUMBER_OF_PAGE = 2;

        long total = 1;
        long totalPages = PagedObject.of(List.of(), total, 1).totalPages();

        assertThat(totalPages)
                .isEqualTo(EXPECTED_TOTAL_NUMBER_OF_PAGE);
    }

    @Test
    public void testTotalPagesWhenTotalIsALargeValue() throws Exception{
        long EXPECTED_TOTAL_NUMBER_OF_PAGE = 900;

        long total = 900;
        long totalPages = PagedObject.of(List.of("random"), total, 1).totalPages();

        assertThat(totalPages)
                .isEqualTo(EXPECTED_TOTAL_NUMBER_OF_PAGE);
    }

    @Test
    public void shouldReturn5AsTotalPages() throws Exception{
        long total = 25;
        long totalPages = PagedObject.of(List.of("random"), total, 5).totalPages();

        assertThat(totalPages)
                .isEqualTo(5);
    }

    @Test
    public void testTotalPagesOfPagedObjectWithItemsToFetchHaveARemainderWhenDividedWithTotal() throws Exception{
        long EXPECTED_TOTAL_NUMBER_OF_PAGE = 9;

        long total = 25;
        long totalPages = PagedObject.of(List.of("random"), total, 3).totalPages();

        assertThat(totalPages)
                .isEqualTo(EXPECTED_TOTAL_NUMBER_OF_PAGE);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenTryingToCreatePagedObjectLargerThanSpecifiedTotal() throws Exception{
        List<String> listSizeLargerThan10 = List.of("random", "random" , "random", "random" ,
                "random", "random" , "random", "random" , "random", "random" , "random", "random" ,
                "random", "random" , "random", "random");

        assertThrows(IllegalStateException.class, () -> PagedObject.of(listSizeLargerThan10, 2, 1));
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenSettingATotalWithANegativeValue() throws Exception{
        long total = -1;

        assertThrows(IllegalStateException.class, () -> PagedObject.of(List.of(), total, 1));
    }

    @Test
    public void testLastPage() throws Exception{
        List<String> pagedList = createList(30);

        PagedObject<String> pagedObject = PagedObject.of(pagedList, 30, 10);
        PagedObject<String> lastPagedObject = pagedObject.lastPage(() -> pagedList.stream().skip(20).toList());

        assertThat(lastPagedObject)
                .isEqualTo(PagedObject.<String>of(List.of("random21", "random22", "random23", "random24", "random25", "random26",
                        "random27", "random28", "random29", "random30"), 30, 10, Page.of(3)));
    }

    @Test
    public void testLastPageWithItemsToFetchHaveARemainderWhenDividedWithTotal() throws Exception{
        List<String> pagedList = createList(20);

        PagedObject<String> pagedObject = PagedObject.of(pagedList, 30, 7);
        PagedObject<String> lastPagedObject = pagedObject.lastPage(() -> createList(30).stream().skip(28).toList());

        assertThat(lastPagedObject.valuesAsList())
                .isEqualTo(List.of("random29", "random30"));

        assertThat(lastPagedObject.currentPageNumber())
                .isEqualTo(5);
    }

    @Test
    public void lastPageShouldReturnPagedObjectWithContentAndCorrectPage() throws Exception{
        List<String> pagedList = createList(20);

        PagedObject<String> pagedObject = PagedObject.of(pagedList, 30, 10);

        PagedObject<String> lastPage = pagedObject.lastPage(() -> createList(30).stream().skip(20).toList());

        assertThat(lastPage.valuesAsList())
                .hasSameElementsAs(List.of("random21", "random22", "random23", "random24", "random25", "random26",
                        "random27", "random28", "random29", "random30"));

        assertThat(lastPage.currentPageNumber())
                .isEqualTo(3);
    }

    @Test
    public void throwsNotLastPageExceptionWhenQueryResultIsEmpty() throws Exception{
        PagedObject<String> pagedObject = PagedObject.of(List.of("random"), 30, 1);

        assertThrows(NotLastPageException.class,
                () -> pagedObject.lastPage(() -> List.of()));
    }

    @Test
    public void throwsNotLastPageExceptionWhenQueryResultIsNull() throws Exception{
        PagedObject<String> pagedObject = PagedObject.of(List.of("random"), 30, 1);

        assertThrows(NotLastPageException.class,
                () -> pagedObject.lastPage(() -> null));
    }

    @Test
    public void throwsNotLastPageExceptionWhenQueryResultSizeIsLessThanTotalModuloOfItemsToFetch() throws Exception{
        PagedObject<String> pagedObject = PagedObject.of(List.of("random"), 30, 9);

        assertThrows(NotLastPageException.class,
                () -> pagedObject.lastPage(() -> List.of("random")));
    }

    @Test
    public void shouldReturnFalseWhenPagedObjectDoesNotReallyContainAnyValueAtAll() throws Exception{
        boolean hasValueAtAll = PagedObject.of(List.of(), 1, 1).hasAnyValue();

        assertThat(hasValueAtAll)
                .isFalse();
    }

    @Test
    public void shouldReturnFalseWhenPagedObjectDoesContainValue() throws Exception{
        boolean hasValueAtAll = PagedObject.of(List.of("any value"), 1, 1).hasAnyValue();

        assertThat(hasValueAtAll)
                .isTrue();
    }


    @Test
    public void hasPageReturnsTrueWhenTotalIsLargerThanItemsToSkip() throws Exception{
        List<String> contents = createList(18);

        boolean hasNextPage = PagedObject.of(contents, 20, 4).hasPageFor(Page.of(4));

        assertThat(hasNextPage)
                .isTrue();
    }

    @Test
    public void hasPageReturnsFalseWhenTotalIsLessThanItemsToSkip() throws Exception{
        List<String> contents = createList(18);

        boolean hasNextPage = PagedObject.of(contents, 20, 6).hasPageFor(Page.of(4));

        assertThat(hasNextPage)
                .isFalse();
    }

    @Test
    public void testHasPageForWithLargePage() throws Exception{
        boolean EXPECTED_HAS_PAGE = false;

        List<String> contents = createList(0);

        boolean hasNextPage = PagedObject.of(contents, 500, 40).hasPageFor(Page.of(20));

        assertThat(hasNextPage)
                .isEqualTo(EXPECTED_HAS_PAGE);
    }

    private List<String> createList(int numberOfTimesToCreate) {
        List<String> result = new ArrayList<>();
        final String content = "random";
        IntStream.range(0, numberOfTimesToCreate).forEach(it -> result.add(content.concat(String.valueOf(it + 1))));

        return result;
    }

}
