package com.example.demo.dtoRequest;

import com.fasterxml.jackson.annotation.JsonTypeName;

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

    public AddPresidentialPoliticianDTORequest(String mostSignificantLawSigned) {
        this.mostSignificantLawSigned = mostSignificantLawSigned;
    }

    public AddPresidentialPoliticianDTORequest(String firstName, String lastName, BigDecimal rating, String mostSignificantLawSigned) {
        super(firstName, lastName, rating, "Presidential");
        this.mostSignificantLawSigned = mostSignificantLawSigned;
    }
    
}
