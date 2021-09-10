package com.example.demo.baseClasses;

import org.assertj.core.api.WritableAssertionInfo;
import org.assertj.core.internal.Iterables;

import java.util.List;

public class MockMvcListAssert<S> {

    Iterables iterables = Iterables.instance();

    WritableAssertionInfo assertionInfo = new WritableAssertionInfo();

    private final List<String> actual;

    private MockMvcListAssert(List<String> actual) {
        this.actual = actual;
    }

    public static  MockMvcListAssert of(List<String> actual) {
        return new MockMvcListAssert(actual);
    }

    public MockMvcListAssert<S> containsInList(String expected) {
        iterables.assertContains(assertionInfo, actual, List.of(expected).toArray(new String[0]));
        return this;
    }

}
