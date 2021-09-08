package com.example.demo.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class PaginatedObjectTest {

    @Test
    public void shouldThrowIllegalStateExceptionWhenCreatingPagedObjectWithNullValues() throws Exception{
        assertThrows(IllegalStateException.class, () -> PagedObject.of(null));
    }

    @Test
    public void pagedObjectShouldReturnExpectedContentSize() throws Exception{
        int pagedObjectSize = PagedObject.<String>of(List.of()).pagedObjectSize();

        assertThat(pagedObjectSize)
                .isEqualTo(List.of().size());
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenTryingToCreatePagedObjectLargerThan10() throws Exception{
        List<String> listSizeLargerThan10 = List.of("random", "random" , "random", "random" ,
                "random", "random" , "random", "random" , "random", "random" , "random", "random" ,
                "random", "random" , "random", "random");

        assertThrows(IllegalStateException.class, () -> PagedObject.<String>of(listSizeLargerThan10));
    }

    @Test
    public void shouldReturnFalseWhenPagedObjectDoesNotHaveAnyValueInTheOffsetSpecified() throws Exception{
        int offset = 2;
        boolean hasValueInOffset = PagedObject.<String>of(List.of("random")).hasValueIn(offset);

        assertThat(hasValueInOffset)
                .isFalse();
    }

    @Test
    public void shouldReturnTrueWhenPagedObjectContainsValueInOffsetSpecified() throws Exception{
        int offset = 0;
        boolean hasValueInOffset = PagedObject.<String>of(List.of("random")).hasValueIn(offset);

        assertThat(hasValueInOffset)
                .isTrue();
    }

    @Test
    public void shouldReturnFalseWhenPagedObjectDoesNotReallyContainAnyValueAtAll() throws Exception{
        boolean hasValueAtAll = PagedObject.of(List.of()).hasAnyValue();

        assertThat(hasValueAtAll)
                .isFalse();
    }

    @Test
    public void shouldReturnFalseWhenPagedObjectDoesContainValue() throws Exception{
        boolean hasValueAtAll = PagedObject.of(List.of("any value")).hasAnyValue();

        assertThat(hasValueAtAll)
                .isTrue();
    }

    @Test
    public void shouldReturnExpectedObjectInCurrentOffsetSpecified() throws Exception{
        int offset = 2;
        String object = "object";
        Optional<String> valueInOffset = PagedObject.<String>of(List.of("random", "not random at all", object)).getValueIn(offset);

        assertThat(valueInOffset)
                .isNotEmpty()
                .get()
                .isEqualTo(object);
    }

    @Test
    public void hasNextPageShouldReturnTrueIfPagedResultHasPageForRequestedPage() throws Exception{
        List<String> contents = List.of("random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18");

        PagedResult<String> pagedResult = PagedResult.of(contents);

        boolean hasNextPage = pagedResult.ofPage(Page.asZero()).hasNextPage();

        assertThat(hasNextPage)
                .isTrue();
    }

    @Test
    public void hasNextPageShouldReturnFalseIfPagedResultReferenceHasRanOutOfPagesToGive() throws Exception{
        List<String> contents = List.of("random1", "random2", "random3", "random4",
                "random5", "random6", "random7", "random8", "random9", "random10", "random11", "random12",
                "random13", "random14", "random15", "random16", "random17", "random18");

        PagedResult<String> pagedResult = PagedResult.of(contents);

        boolean hasNextPage = pagedResult.ofPage(Page.of(1)).hasNextPage();

        assertThat(hasNextPage)
                .isFalse();
    }

}
