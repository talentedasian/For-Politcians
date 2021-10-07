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

    public static <Z> PagedObject<Z> of(List<Z> values, long total, int itemsToFetch, Page page) {
        isValuesNotNull(values);
        isValuesLessThanOrEqualValidation(values.size(), (int) total);
        return new PagedObject<Z>(values, page, total, itemsToFetch);
    }

    public static <Z> PagedObject<Z> of(List<Z> values, long total, int itemsToFetch) {
        isValuesNotNull(values);
        isValuesLessThanOrEqualValidation(values.size(), (int) total);
        return new PagedObject<Z>(values, Page.asZero(), total, itemsToFetch);
    }

    private static <T> void isValuesLessThanOrEqualValidation(int size, int total) {
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
        return total % itemsToFetch == 0 ? pages : pages + 1;
    }

    /**
     * Does a minimal validation whether the query result matches the specified constraints.
     * Also delegates to the {@link #lastPage(PagedQuery)} method if the {@link Page} requested
     * is the last page in the {@link PagedObject}
     *
     * @return PagedObject with content as  the result of the query and {@link Page} from the page parameter
     */
    public PagedObject<T> toPage(PagedQuery<T> query, Page page) {
        if (!hasPageFor(currentPage.nextPage())) throw new IllegalStateException("There has to be next page to call next page");
        if (isOnLastPage(page)) return lastPage(query);

        boolean isContentSizeEqualToItemsToFetch = query.find().size() == itemsToFetch;
        if (!isContentSizeEqualToItemsToFetch) throw new IllegalStateException("""
                Content size must be equal to itemsToFetch. Unless the page requested is the last page
                """);

        return of(query.find(), total, itemsToFetch, page);
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
        Page page = Page.of(lastPageNumber());
        if (result == null || result.isEmpty()) throw new NotLastPageException("Content cannot be null for last page");
        else if (isResultNotLastPage(result)) throw new NotLastPageException("TODO");
        else if (isOnLastPage(currentPage)) throw new IllegalStateException("Currently on the last page");
        return of(result, total, itemsToFetch, page);
    }

    private boolean isOnLastPage(Page page) {
        return lastPageNumber() == page.pageNumber();
    }

    private boolean isResultNotLastPage(List<T> result) {
        return result.size() != sizeOfLastPage();
    }

    public boolean hasPageFor(Page page) {
        return totalPages() >= (page.pageNumber() + 1);
    }

    public List<T> valuesAsList() {
        return values().toList();
    }

    public long totalElements() {
        return this.total;
    }

    private int sizeOfLastPage() {
        return totalPages() == 1 ? 1 : (int) (total - lastPageNumber() * itemsToFetch);
    }

    private int lastPageNumber() {
        return (int) totalPages() - 1;
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
