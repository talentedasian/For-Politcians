package com.example.demo.domain;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import org.springframework.util.Assert;

public class Page {

    private final int pageNumber;

    private Page(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public static Page of(int pageNumber) {
        Assert.state((pageNumber >= 0), "Page number should not be less than 0");

        return new Page(pageNumber);
    }

    public static Page asZero() {
        return new Page(0);
    }

    public int pageNumber() {
        return this.pageNumber;
    }

    public Page nextPage() {
        return of(this.pageNumber + 1);
    }

    public Page nextPage(int pageNumber) {
        return of(this.pageNumber + pageNumber);
    }

    public Page previousPage() {
        return of(this.pageNumber - 1);
    }

    public int itemsToSkip(int multiple) {
        return this.pageNumber * multiple;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        return pageNumber == page.pageNumber;
    }

    @Override
    public int hashCode() {
        return pageNumber;
    }

    @Override
    @ExcludeFromJacocoGeneratedCoverage
    public String toString() {
        return "Page{ " + pageNumber + " }";
    }
}
