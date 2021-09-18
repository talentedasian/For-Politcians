package com.example.demo.domain.politicianNumber;

import com.example.demo.domain.entities.PoliticianNumber;

public abstract class AbstractPoliticianNumberCalculator implements PoliticianNumberCalculator{

    protected final static String pattern = PoliticianNumber.pattern;

    protected final static char FIRSTNAME_INITIAL = 'F';
    protected final static char LASTNAME_INITIAL = 'L';
    protected final static char TYPE_INITIAL = 'T';
}
