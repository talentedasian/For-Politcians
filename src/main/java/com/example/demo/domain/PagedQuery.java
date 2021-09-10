package com.example.demo.domain;

import java.util.List;

public interface PagedQuery<T> {

    List<T> find();
}
