package com.example.demo.domain;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class PagedObject<T> {

    private final List<T> content;
    private final List<T> previousContent;
    private final Page page;

    private PagedObject(List<T> content) {
        this.content = content;
        this.previousContent = null;
        this.page = Page.asDefault();
    }

    private PagedObject(List<T> content, Page page) {
        this.content = content;
        this.previousContent = null;
        this.page = page;
    }

    private PagedObject(List<T> content, List<T> previousContent, Page page) {
        this.content = content;
        this.previousContent = previousContent;
        this.page = page;
    }

    public static <Z> PagedObject<Z> of(List<Z> values) {
        isValuesNotNull(values);
        isValuesLessThan10(values.size());
        return new PagedObject<Z>(values);
    }

    public static <Z> PagedObject<Z> ofPageSize(List<Z> values, Page page) {
        isValuesNotNull(values);
        isValuesLessThan10(values.size());
        return new PagedObject<Z>(values, page);
    }

    public static <Z> PagedObject<Z> ofPageSizeWithPreviousContent(List<Z> values, List<Z> previousValues,Page page) {
        isValuesNotNull(values);
        isValuesNotNull(previousValues);
        isValuesLessThan10(values.size());
        isValuesLessThan10(previousValues.size());
        return new PagedObject<Z>(values, previousValues, page);
    }

    private static void isValuesLessThan10(int size) {
        Assert.state(size <= 10, "Value size must be within 10 only. Values' size == " + size);
    }

    private static void isValuesNotNull(Object value) {
        Assert.state(value != null, "Values cannot be null be can be empty");
    }

    public Stream<T> values() {
        return Collections.unmodifiableList(content).stream();
    }

    public int pagedObjectSize() {
        return content.size();
    }

    public int currentPage() {
        return page.pageNumber();
    }

    public PagedObject<T> nextPage(List<T> values) {
        Assert.state((values != null), "Values cannot be null but it can be empty");
        return new PagedObject<T>(this.content, values, page.nextPage());
    }

    public boolean hasValueIn(int offset) {
        isValuesLessThan10(offset);
        return content.size() < offset ? false : true;
    }

    public Optional<T> getValueIn(int offset) {
        isValuesLessThan10(offset);
        return hasValueIn(offset) ? Optional.of(content.get(offset)) : Optional.empty();
    }

    public boolean hasAnyValue() {
        return !content.isEmpty();
    }
}
