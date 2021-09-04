package com.example.demo.adapter.in.dtoRequest;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@JsonTypeName("PRESIDENTIAL")
public class AddPresidentialPoliticianDTORequest extends AddPoliticianDTORequest{

    private String mostSignificantLawSigned;

    public String getMostSignificantLawSigned() {
        return mostSignificantLawSigned;
    }

    public void setMostSignificantLawSigned(String mostSignificantLawSigned) {
        this.mostSignificantLawSigned = mostSignificantLawSigned;
    }

    public AddPresidentialPoliticianDTORequest() {}

    public AddPresidentialPoliticianDTORequest(@NotNull String firstName, @NotNull String lastName,
                           @NotNull BigDecimal rating, String mostSignificantLawSigned) {
        super(firstName, lastName, rating, "Presidential");
        this.mostSignificantLawSigned = mostSignificantLawSigned;
    }

    @Override
    public String toString() {
        return super.toString() + ", {" +
                "mostSignificantLawSigned='" + mostSignificantLawSigned + '\'' +
                '}';
    }
}
