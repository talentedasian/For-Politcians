package com.example.demo.domain;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
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

    public boolean doesCurrentPageExist() {
        return !content.isEmpty();
    }

    public int currentPageNumber() {
        return currentPage.pageNumber();
    }

    public long totalPages() {
        if (total == 0) return 0;

        long pages = total / itemsToFetch;
        return getItemSizeOfLastPage() == 0 ? pages : pages + 1;
    }

    public PagedObject<T> nextPage(PagedQuery<T> query) {
        if (!hasPageFor(currentPage.nextPage())) throw new IllegalStateException("There has to be next page to call next page");
        return of(query.find(), total, itemsToFetch, currentPage.nextPage());
    }

    /** Makes a query that fetches the last page of the table. Does a minimal validation
     * whether the query actually returns the last page.
     *
     * @param query the query that is needed to fetch that last page in the database
     * @return a list that contains the very last page of the table
     * @throws NotLastPageException when the result of the query does not return last page
     */
    public PagedObject<T> lastPage(PagedQuery<T> query) {
        List<T> result = query.find();
        Page page = Page.of((int) totalPages() - 1);
        if (result == null || result.isEmpty()) throw new NotLastPageException();
        else if (result.size() < (getItemSizeOfLastPage())) throw new NotLastPageException();
        return of(result, total, itemsToFetch, page);
    }

    public boolean hasPageFor(Page page) {
        return totalPages() > page.pageNumber();
    }

    public List<T> valuesAsList() {
        return values().toList();
    }

    public long totalElements() {
        return this.total;
    }

    private long getItemSizeOfLastPage() {
        return total % itemsToFetch;
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
    @ExcludeFromJacocoGeneratedCoverage
    public String toString() {
        return "PagedObject{" +
                "Content { " + content +
                " }, Page { " + currentPage +
                " }, Total {" + total +
                "} , { itemsToFetch=" + itemsToFetch +
                " }";
    }
}
