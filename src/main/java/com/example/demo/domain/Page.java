package com.example.demo.domain;

import org.springframework.util.Assert;

public class Page {

    private final int pageNumber;
    public static final int itemsPerPage = 10;

    private Page(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public static Page of(int pageNumber) {
        Assert.state(!(pageNumber < 0), "Page number should not be less than 0");

        return new Page(pageNumber);
    }

    public static Page asDefault() {
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

    public int pageToSkip() {
        return this.pageNumber * 10;
    }

}
