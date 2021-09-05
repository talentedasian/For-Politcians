package com.example.demo.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaginatedObjectTest {

    @Test
    public void shouldThrowIllegalStateExceptionWhenCreatingPagedObjectWithNullValues() throws Exception{
        assertThrows(IllegalStateException.class, () -> PagedObject.of(null));
    }

    @Test
    public void pagedObjectShouldAlwaysReturnSizeOfLessThanOrEqualTo10() throws Exception{
        int pagedObjectSize = PagedObject.<String>of(List.of()).pagedObjectSize();

        assertThat(pagedObjectSize)
                .isEqualTo(0);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenTryingToCreatePagedObjectLargerThan10() throws Exception{
        List<String> listSizeLargerThan10 = List.of("random", "random" , "random", "random" ,
                "random", "random" , "random", "random" , "random", "random" , "random", "random" ,
                "random", "random" , "random", "random");

        assertThrows(IllegalStateException.class, () -> PagedObject.<String>of(listSizeLargerThan10));
    }

    @Test
    public void shouldReturn0AsCurrentPageNumberWhenUsingNewlyCreatedPagedObjectWithoutAnyPageSpecified() throws Exception{
        int currentPageNumber = PagedObject.<String>of(List.of("random")).currentPage();

        assertThat(currentPageNumber)
                .isEqualTo(0);
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenNextPageIsCalledWithNulParameter() throws Exception{
        assertThrows(IllegalStateException.class, () -> PagedObject.of(List.of()).nextPage(null));
    }

    @Test
    public void currentPageShouldBeIncrementedBy1WhenNextPageIsCalled() throws Exception{
        int currentPageNumber = PagedObject.<String>of(List.of("random")).nextPage(List.of("random")).currentPage();

        assertThat(currentPageNumber)
                .isEqualTo(1);
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

}
