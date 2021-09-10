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
    private final PagedResult<T> pagedResult;
    private final long total;

    private PagedObject(List<T> content, Page page, PagedResult<T> pagedResult, long total) {
        this.content = content;
        this.currentPage = page;
        this.pagedResult = pagedResult;
        this.total = total;
    }

    public static <Z> PagedObject<Z> of(List<Z> values, long total) {
        isValuesNotNull(values);
        isValuesLessThan(values.size(), (int) total);
        return new PagedObject<Z>(values, Page.asZero(), null, total);
    }

    public static <Z> PagedObject<Z> of(List<Z> values, Page page, PagedResult<Z> pagedResult) {
        isValuesNotNull(values);
        isValuesLessThan(10, 10);
        return new PagedObject<Z>(values, page, pagedResult, 10);
    }

    private static <T> void isValuesLessThan(int size, int total) {
        Assert.state(total > size,
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
        isValuesLessThan(offset, 10);
        return content.size() < offset ? false : true;
    }

    public Optional<T> getValueIn(int offset) {
        isValuesLessThan(offset, 10);
        return hasValueIn(offset) ? Optional.of(content.get(offset)) : Optional.empty();
    }

    public boolean hasAnyValue() {
        return !content.isEmpty();
    }

    public int currentPageNumber() {
        return currentPage.pageNumber();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagedObject<?> that = (PagedObject<?>) o;

        if (!Objects.equals(content, that.content)) return false;
        if (!Objects.equals(currentPage, that.currentPage)) return false;
        return Objects.equals(pagedResult, that.pagedResult);
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (currentPage != null ? currentPage.hashCode() : 0);
        result = 31 * result + (pagedResult != null ? pagedResult.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PagedObject{ " + content + " , " + currentPage.toString() + " }";
    }

    public boolean hasNextPage() {
        return pagedResult == null ? false : pagedResult.hasPageFor(currentPage.nextPage());
    }

    public long totalPages() {
        return 0;
    }

}
