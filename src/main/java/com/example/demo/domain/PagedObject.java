package com.example.demo.domain;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
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

    public static <Z> PagedObject<Z> of(List<Z> values, long total, int itemsToFetch, Page page) {
        isValuesNotNull(values);
        isValuesLessThanOrEqual(values.size(), (int) total);
        return new PagedObject<Z>(values, page, total, itemsToFetch);
    }

    public static <Z> PagedObject<Z> of(List<Z> values, long total, int itemsToFetch) {
        isValuesNotNull(values);
        isValuesLessThanOrEqual(values.size(), (int) total);
        return new PagedObject<Z>(values, Page.asZero(), total, itemsToFetch);
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

    /** Makes a query that fetches the last page of the table
     *
     * @param query the query that is needed to fetch that last page in the database
     * @return a list that contains the very last page of the table
     */
    public PagedObject<T> lastPage(PagedQuery<T> query) {
        return of(query.find(), total, itemsToFetch, Page.of((int) totalPages()));
    }

    public boolean hasPageFor(Page page, int itemsToFetch) {
        return total > page.itemsToSkip(itemsToFetch);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagedObject<?> that = (PagedObject<?>) o;

        if (total != that.total) return false;
        if (itemsToFetch != that.itemsToFetch) return false;
        if (!content.equals(that.content)) return false;
        return currentPage.equals(that.currentPage);
    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + currentPage.hashCode();
        result = 31 * result + (int) (total ^ (total >>> 32));
        result = 31 * result + itemsToFetch;
        return result;
    }

    @Override
    public String toString() {
        return "PagedObject{ " + content + " , " + currentPage.toString() + " }";
    }

    public List<T> valuesAsList() {
        return values().toList();
    }
}
