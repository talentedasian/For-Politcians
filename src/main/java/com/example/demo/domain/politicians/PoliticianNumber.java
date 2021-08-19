package com.example.demo.domain.politicians;

import com.example.demo.domain.politicianNumber.PoliticianNumberImplementor;

public record PoliticianNumber(Name name, Politicians.Type type) {

    public static PoliticianNumber from(String firstName, String lastName, Politicians.Type type) {
        return new PoliticianNumber(new Name(firstName, lastName), type);
    }

    public String returnPoliticianNumber() {
        var politician = new Politicians.PoliticiansBuilder()
                .setFirstName(name.firstName())
                .setLastName(name.lastName())
                .build();
        politician.setType(type);

        return PoliticianNumberImplementor.with(politician.recordName(), politician.getType()).calculateEntityNumber().getPoliticianNumber();
    }

}
