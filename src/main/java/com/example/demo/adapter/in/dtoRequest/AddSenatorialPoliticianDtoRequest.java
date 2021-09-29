package com.example.demo.adapter.in.dtoRequest;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.math.BigDecimal;

@JsonTypeName("SENATORIAL")
public class AddSenatorialPoliticianDtoRequest extends AddPoliticianDTORequest {

    private int monthsOfService;

    private String lawMade;

    public int getMonthsOfService() {
        return monthsOfService;
    }

    public String getlawMade() {
        return lawMade;
    }

    @ExcludeFromJacocoGeneratedCoverage
    public AddSenatorialPoliticianDtoRequest(String firstName, String lastName, BigDecimal rating, int monthsOfService, String lawMade) {
        super(firstName, lastName, rating, "Senatorial");
        this.monthsOfService = monthsOfService;
        this.lawMade = lawMade;
    }

    @ExcludeFromJacocoGeneratedCoverage
    public AddSenatorialPoliticianDtoRequest() {}

    @Override
    @ExcludeFromJacocoGeneratedCoverage
    public String toString() {
        return "AddSenatorialPoliticianDtoRequest{" +
                "monthsOfService=" + monthsOfService +
                ", lawMade='" + lawMade + '\'' +
                '}';
    }

}
