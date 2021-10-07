package com.example.demo.domain.pagination;

import com.example.demo.domain.NotLastPageException;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertThrows;

@Tag("Domain")
public class PaginatedObjectTest {

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
    public void shouldReturn0AsTotalPageWhenTotalIs0() throws Exception{
        long actualTotal = PagedObject.of(List.of(), 0, 1).totalPages();

        assertThat(actualTotal)
                .isEqualTo(0);
    }

    @Test
    public void shouldReturnExpectedTotalPagesWhenTotalIsSetLargerThan0() throws Exception{
        long EXPECTED_TOTAL_NUMBER_OF_PAGE = 1;

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
    public void testTotalPagesWhenItemsToFetchIsALargeValue() throws Exception{
        long EXPECTED_TOTAL_NUMBER_OF_PAGE = 9;

        long total = 9000;
        long totalPages = PagedObject.of(List.of("random"), total, 1090).totalPages();

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
                .isEqualTo(PagedObject.of(List.of("random21", "random22", "random23", "random24", "random25", "random26",
                        "random27", "random28", "random29", "random30"), 30, 10, Page.of(2)));
    }

    @Test
    public void testLastPagePageNumberWithItemsToFetchHaveARemainderWhenDividedWithTotal() throws Exception{
        List<String> pagedList = createList(20);

        PagedObject<String> pagedObject = PagedObject.of(pagedList, 30, 7);
        PagedObject<String> lastPagedObject = pagedObject.lastPage(() -> createList(30).stream().skip(28).toList());

        assertThat(lastPagedObject.valuesAsList())
                .isEqualTo(List.of("random29", "random30"));

        assertThat(lastPagedObject.currentPageNumber())
                .isEqualTo(4);
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
                .isEqualTo(2);
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
    public void throwsNotLastPageExceptionWhenQueryResultSizeIsLessThanTheSizeForLastPage() throws Exception{
        PagedObject<String> pagedObject = PagedObject.of(List.of("random"), 30, 9);

        assertThrows(NotLastPageException.class,
                () -> pagedObject.lastPage(() -> List.of("random")));
    }

    @Test
    public void shouldReturnFalseWhenPagedObjectDoesNotReallyContainAnyValueAtAll() throws Exception{
        boolean hasValueAtAll = PagedObject.of(List.of(), 1, 1).doesCurrentPageExist();

        assertThat(hasValueAtAll)
                .isFalse();
    }

    @Test
    public void shouldReturnTrueWhenContentsOfPagedObjectIsEmpty() throws Exception{
        boolean hasValueAtAll = PagedObject.of(List.of("any value"), 1, 1).doesCurrentPageExist();

        assertThat(hasValueAtAll)
                .isTrue();
    }

    @Test
    public void shouldReturnFalseWhenContentsOfPagedObjectIsEmpty() throws Exception{
        boolean hasValueAtAll = PagedObject.of(Collections.emptyList(), 1, 1).doesCurrentPageExist();

        assertThat(hasValueAtAll)
                .isFalse();
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
    public void testHasPageForWithLargePageThatReturnsFalse() throws Exception{
        List<String> contents = createList(0);

        boolean hasNextPage = PagedObject.of(contents, 500, 40).hasPageFor(Page.of(20));

        assertThat(hasNextPage)
                .isFalse();
    }

    @Test
    public void testHasPageForWithLargePageThatReturnsTrue() throws Exception{
        List<String> contents = createList(1);

        boolean hasNextPage = PagedObject.of(contents, 500, 20).hasPageFor(Page.of(20));

        assertThat(hasNextPage)
                .isTrue();
    }

    @Test
    public void testPageToThatReturnsPageRequestedAndResultInAPagedObject() throws Exception{
        int numberOfTimesToCreate = 20;
        List<String> contents = createList(numberOfTimesToCreate);
        int itemsToFetch = 4;
        List<String> firstPageContent = contents.stream().limit(itemsToFetch).toList();
        List<String> nextPageContent = contents.stream().skip(itemsToFetch).limit(itemsToFetch).toList();

        PagedObject<String> pagedObject = PagedObject.of(firstPageContent, numberOfTimesToCreate, itemsToFetch);

        Page secondPage = Page.of(1);
        assertThat(pagedObject.toPage(() -> nextPageContent, secondPage))
                .isEqualTo(PagedObject.of(nextPageContent, numberOfTimesToCreate, itemsToFetch, secondPage));
    }

    @Test
    public void testHasPageForInNextPage() throws Exception{
        int total = 100;
        List<String> contents = createList(total);

        PagedObject<String> pagedObject = PagedObject.of(contents, total, 20, Page.of(3));
        Page lastPage = Page.of(4);

        assertThat(pagedObject.hasPageFor(lastPage))
                .isTrue();
    }

    @Test
    public void shouldNotThrowLastPageExceptionWhenTotalPagesModuloItemsToFetchIsZero() throws Exception{
        int total = 100;
        List<String> contents = createList(total);
        List<String> lastPageContents = contents.stream().skip(80).toList();

        PagedObject<String> pagedObject = PagedObject.of(contents, total, 20, Page.of(3));
        Page lastPage = Page.of(4);

        assertThat(pagedObject.lastPage(() -> lastPageContents))
                .isEqualTo(PagedObject.of(lastPageContents, total, 20, lastPage));
    }

    @Test
    public void shouldNotThrowIllegalStateExceptionWithLastPage() throws Exception{
        int total = 5;
        List<String> contents = createList(total);
        List<String> lastPageContents = contents.stream().skip(4).toList();

        int itemsToFetch = 1;
        PagedObject<String> pagedObject = PagedObject.of(contents, total, itemsToFetch);
        Page lastPage = Page.of(4);

        assertThat(pagedObject.lastPage(() -> lastPageContents))
                .isEqualTo(PagedObject.of(lastPageContents, total, itemsToFetch, lastPage));
    }

    @Test
    public void shouldThrowIllegalStateExceptionInToPageWhenContentSizeAndItemsToFetchIsNotEqual() throws Exception{
        int total = 100;
        int itemsToFetch = 23;
        List<String> contents = createList(total);
        List<String> nextPageContentsWithSizeNotEqualToItemsToFetch = createList(itemsToFetch + 1);


        PagedObject<String> pagedObject = PagedObject.of(contents, total, itemsToFetch);
        Page anyPageThatIsNotLastPage = Page.of(2);

        assertThatThrownBy(() -> pagedObject.toPage(() -> nextPageContentsWithSizeNotEqualToItemsToFetch, anyPageThatIsNotLastPage))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Content size must be equal to itemsToFetch");
    }

    @Test
    public void shouldThrowNotLastPageExceptionWhenPagedObjectHasOnly1PageThenRequestsForLastPage() throws Exception{
        PagedObject pagedObject = PagedObject.of(List.of(), 1, 1);

        assertThatThrownBy(() -> pagedObject.lastPage(() -> List.of("random")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Currently on the last page");
    }

    @Test
    public void testHasPageForWithLastPagePlus1() throws Exception{
        int total = 233;
        List<String> contents = createList(total);

        PagedObject<String> pagedObject = PagedObject.of(contents, total, 3, Page.of(3));
        Page firstNonExistentPage = Page.of(79);

        assertThat(pagedObject.hasPageFor(firstNonExistentPage))
                .isFalse();
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenNextPageIsNonExistent() throws Exception{
        List<String> contents = createList(500);

        PagedObject<String> pagedObject = PagedObject.of(contents, 1000, 200, Page.of(4));

        assertThrows(IllegalStateException.class, () -> pagedObject.toPage(() -> List.of(), Page.of(501)));
    }

    private List<String> createList(int numberOfTimesToCreate) {
        List<String> result = new ArrayList<>();
        final String content = "random";
        IntStream.range(0, numberOfTimesToCreate).forEach(it -> result.add(content.concat(String.valueOf(it + 1))));

        return result;
    }

}
