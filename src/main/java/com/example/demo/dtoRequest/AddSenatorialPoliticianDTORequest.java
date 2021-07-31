package com.example.demo.dtoRequest;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonTypeName("Senatorial")
public class AddSenatorialPoliticianDTORequest extends AddPoliticianDTORequest {

    @NotNull
    private int monthsOfService;

    private String lawMade;

    public int getMonthsOfService() {
        return monthsOfService;
    }

    public void setMonthsOfService(int monthsOfService) {
        this.monthsOfService = monthsOfService;
    }

    public String getlawMade() {
        return lawMade;
    }

    public void setlawMade(String lawMade) {
        this.lawMade = lawMade;
    }

    public AddSenatorialPoliticianDTORequest(String firstName, String lastName, BigDecimal rating, int monthsOfService, String lawMade) {
        super(firstName, lastName, rating);
        this.monthsOfService = monthsOfService;
        this.lawMade = lawMade;
    }

    public AddSenatorialPoliticianDTORequest() {}

    @Override
    public String toString() {
        return "AddSenatorialPoliticianDTORequest{" +
                "monthsOfService=" + monthsOfService +
                ", lawMade='" + lawMade + '\'' +
                '}';
    }

}
