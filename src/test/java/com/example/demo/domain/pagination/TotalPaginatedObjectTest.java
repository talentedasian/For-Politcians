package com.example.demo.domain.pagination;

import com.example.demo.domain.PagedObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class TotalPaginatedObjectTest {

    @Test
    public void shouldThrowIllegalStateExceptionWhenCreatingPagedObjectWithNullValues() throws Exception{
        assertThrows(IllegalStateException.class, () -> PagedObject.of(null, 10));
    }

    @Test
    public void pagedObjectShouldReturnExpectedContentSize() throws Exception{
        int pagedObjectSize = PagedObject.<String>of(List.of(), 10).pagedObjectSize();

        assertThat(pagedObjectSize)
                .isEqualTo(List.of().size());
    }

    @Test
    public void shouldReturn1AsTotalPageWhenTotalIs0() throws Exception{
        long total = 0;
        long actualTotal = PagedObject.of(List.of(), total).totalPages();

        assertThat(actualTotal)
                .isEqualTo(1);
    }

    @Test
    public void shouldReturnExpectedTotalPagesWhenTotalIsSetLargerThan0() throws Exception{
        long EXPECTED_TOTAL_NUMBER_OF_PAGE = 2;

        long total = 1;
        long totalPages = PagedObject.of(List.of(), total).totalPages();

        assertThat(totalPages)
                .isEqualTo(EXPECTED_TOTAL_NUMBER_OF_PAGE);
    }

    @Test
    public void testTotalPagesWhenTotalIsALargeValue() throws Exception{
        long EXPECTED_TOTAL_NUMBER_OF_PAGE = 3;

        long total = 30;
        long totalPages = PagedObject.of(List.of("random"), total).totalPages();

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

        assertThrows(IllegalStateException.class, () -> PagedObject.of(listSizeLargerThan10, 2));
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenSettingATotalWithANegativeValue() throws Exception{
        long total = -1;

        assertThrows(IllegalStateException.class, () -> PagedObject.of(List.of(), total));
    }

//    @Test
//    public void shouldReturnFalseWhenPagedObjectDoesNotHaveAnyValueInTheOffsetSpecified() throws Exception{
//        int offset = 2;
//        boolean hasValueInOffset = PagedObject.<String>of(List.of("random")).hasValueIn(offset);
//
//        assertThat(hasValueInOffset)
//                .isFalse();
//    }

//    @Test
//    public void shouldReturnTrueWhenPagedObjectContainsValueInOffsetSpecified() throws Exception{
//        int offset = 0;
//        boolean hasValueInOffset = PagedObject.<String>of(List.of("random")).hasValueIn(offset);
//
//        assertThat(hasValueInOffset)
//                .isTrue();
//    }

//    @Test
//    public void shouldReturnFalseWhenPagedObjectDoesNotReallyContainAnyValueAtAll() throws Exception{
//        boolean hasValueAtAll = PagedObject.of(List.of()).hasAnyValue();
//
//        assertThat(hasValueAtAll)
//                .isFalse();
//    }

//    @Test
//    public void shouldReturnFalseWhenPagedObjectDoesContainValue() throws Exception{
//        boolean hasValueAtAll = PagedObject.of(List.of("any value")).hasAnyValue();
//
//        assertThat(hasValueAtAll)
//                .isTrue();
//    }

//    @Test
//    public void shouldReturnExpectedObjectInCurrentOffsetSpecified() throws Exception{
//        int offset = 2;
//        String object = "object";
//        Optional<String> valueInOffset = PagedObject.<String>of(List.of("random", "not random at all", object)).getValueIn(offset);
//
//        assertThat(valueInOffset)
//                .isNotEmpty()
//                .get()
//                .isEqualTo(object);
//    }

//    @Test
//    public void hasNextPageShouldReturnTrueIfPagedResultHasPageForRequestedPage() throws Exception{
//        List<String> contents = List.of("random1", "random2", "random3", "random4",
//                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
//                "random13", "random14", "random15", "random16", "random17", "random18");
//
//        PagedResult<String> pagedResult = PagedResult.of(contents);
//
//        boolean hasNextPage = pagedResult.ofPage(Page.asZero()).hasNextPage();
//
//        assertThat(hasNextPage)
//                .isTrue();
//    }

//    @Test
//    public void hasNextPageShouldReturnFalseIfPagedResultReferenceHasRanOutOfPagesToGive() throws Exception{
//        List<String> contents = List.of("random1", "random2", "random3", "random4",
//                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
//                "random13", "random14", "random15", "random16", "random17", "random18");
//
//        PagedResult<String> pagedResult = PagedResult.of(contents);
//
//        boolean hasNextPage = pagedResult.ofPage(Page.of(1)).hasNextPage();
//
//        assertThat(hasNextPage)
//                .isFalse();
//    }

}
