package com.example.demo.domain;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *         For now, there isn't really any reason to make this class implement
 *  INFO : an interface and maybe call it "TenPagedObject" since it deals with PagedObjects having
 *         sizes of 10.
 */
public class PagedObject<T> {

    private final List<T> content;
    private final Page currentPage;
    private final long total;
    private final int itemsToFetch;

    private PagedObject(List<T> content, Page page, long total, int itemsToFetch) {
        this.content = content;
        this.currentPage = page;
        this.total = total;
        this.itemsToFetch = itemsToFetch;
    }

    public static <Z> PagedObject<Z> of(List<Z> values, long total) {
        isValuesNotNull(values);
        isValuesLessThanOrEqual(values.size(), (int) total);
        return new PagedObject<Z>(values, Page.asZero(), total, 10);
    }

    public static <Z> PagedObject<Z> of(List<Z> values, long total, int itemsToFetch) {
        isValuesNotNull(values);
        isValuesLessThanOrEqual(values.size(), (int) total);
        return new PagedObject<Z>(values, Page.asZero(), total, itemsToFetch);
    }

    public static <Z> PagedObject<Z> of(List<Z> values, Page page, PagedResult<Z> pagedResult) {
        isValuesNotNull(values);
        isValuesLessThanOrEqual(10, 10);
        return new PagedObject<Z>(values, page, 10, 0);
    }

    private static <T> void isValuesLessThanOrEqual(int size, int total) {
        Assert.state(total >= size,
                "Content size must be less than total specified");
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

    public boolean hasValueIn(int offset) {
        isValuesLessThanOrEqual(offset, 10);
        return content.size() < offset ? false : true;
    }

    public Optional<T> getValueIn(int offset) {
        isValuesLessThanOrEqual(offset, 10);
        return hasValueIn(offset) ? Optional.of(content.get(offset)) : Optional.empty();
    }

    public boolean hasAnyValue() {
        return !content.isEmpty();
    }

    public int currentPageNumber() {
        return currentPage.pageNumber();
    }

    public long totalPages() {
        if (total == 0) return 1;
        if (total > 0 && content.isEmpty()) {
            return 2;
        }
        return total % itemsToFetch == 0 ? total / itemsToFetch : total / itemsToFetch + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagedObject<?> that = (PagedObject<?>) o;

        if (!Objects.equals(content, that.content)) return false;
        return Objects.equals(currentPage, that.currentPage);
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (currentPage != null ? currentPage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PagedObject{ " + content + " , " + currentPage.toString() + " }";
    }

}
