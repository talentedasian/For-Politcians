package com.example.demo.dtoRequest;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@JsonTypeName("Presidential")
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
    
}
