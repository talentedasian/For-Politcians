package com.example.demo.domain;

import java.util.List;
import java.util.Objects;

/**
 *         For now, there isn't really any reason to make this class implement
 *  INFO : an interface and maybe call it "TenPagedObject" since it deals with PagedObjects having
 *         sizes of 10.
 */
public class PagedResult<T> {

    public static final int ITEMS_TO_SKIP = 10;

    private final List<T> contents;

    private PagedResult(List<T> contents) {
        this.contents = contents;
    }

    public static <Z> PagedResult of(List<Z> objects) {
        return new PagedResult<Z>(objects);
    }

    public PagedObject<T> ofPage(Page page) {
        return PagedObject.<T>of(contents.stream().skip(page.itemsToSkip(ITEMS_TO_SKIP)).limit(10).toList(), page, this);
    }

    public boolean hasPageFor(Page page) {
        return contents.size() > page.itemsToSkip(ITEMS_TO_SKIP);
    }

    // INFO : Current pagination works as zero based so first page technically is 0
    public PagedObject<T> firstPage() {
        return PagedObject.<T>of(contents.stream().limit(ITEMS_TO_SKIP).toList(), Page.asZero(), this);
    }

    public PagedObject<T> lastPage() {
        var lastPage = Page.of(getLastPageItemsNumber());
        return PagedObject.of(contents.stream().skip(lastPage.itemsToSkip(ITEMS_TO_SKIP)).limit(ITEMS_TO_SKIP).toList(), lastPage, this);
    }

    private int getLastPageItemsNumber() {
        return totalPages() - 1;
    }

    // INFO : Total pages starts off with 1 and is not zero based
    public int totalPages() {
        return contents.isEmpty() ? 0 : (contents.size() / 10) + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagedResult<?> that = (PagedResult<?>) o;

        return Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return contents != null ? contents.hashCode() : 0;
    }

}
