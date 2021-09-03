package com.example.demo.baseClasses;

import com.example.demo.domain.entities.AccountNumber;
import com.example.demo.domain.politicians.PoliticianNumber;

public final class NumberTestFactory {

    public static AccountNumber ACC_NUMBER() {
        return new AccountNumber("FLOPM-00000000000000");
    }

    public static PoliticianNumber POL_NUMBER() {
        return new PoliticianNumber(PoliticianNumber.pattern);
    }

}
