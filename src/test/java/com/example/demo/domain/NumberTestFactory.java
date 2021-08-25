package com.example.demo.domain;

import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.politicians.PoliticianNumber;

public final class NumberTestFactory {

    public static String ACC_NUMBER() {
        return AccountNumber.pattern;
    }

    public static String POL_NUMBER() {
        return PoliticianNumber.pattern;
    }

}
