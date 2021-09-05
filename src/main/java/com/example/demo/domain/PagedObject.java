package com.example.demo.domain;

import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

public class PagedObject<T> {

    private final List<T> content;
    private final Page page;

    private PagedObject(List<T> content) {
        this.content = content;
        this.page = Page.asDefault();
    }

    private PagedObject(List<T> content, Page page) {
        this.content = content;
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

    private static void isValuesLessThan10(int size) {
        Assert.state(size < 10, "Paged object cannot contain more than 10 values");
    }

    private static void isValuesNotNull(Object value) {
        Assert.state(value != null, "Values cannot be null be can be empty");
    }


    public int pagedObjectSize() {
        return content.size();
    }

    public int currentPage() {
        return page.pageNumber();
    }

    public PagedObject<T> nextPage(List<T> values) {
        Assert.state((values != null), "Values cannot be null but it can be empty");
        return new PagedObject<T>(values, page.nextPage());
    }

    public boolean hasValueIn(int offset) {
        return content.size() < offset ? false : true;
    }

    public Optional<T> getValueIn(int offset) {
        return hasValueIn(offset) ? Optional.of(content.get(offset)) : Optional.empty();
    }

    public boolean hasAnyValue() {
        return !content.isEmpty();
    }
}
