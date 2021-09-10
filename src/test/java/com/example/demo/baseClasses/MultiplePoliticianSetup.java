package com.example.demo.baseClasses;

import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicians.Politicians.PoliticiansBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.domain.politicians.PoliticianNumber.of;
import static java.lang.String.valueOf;

public class MultiplePoliticianSetup {

    public static List<Politicians> pagedPoliticianSetup(int numberOfTimes, PoliticiansBuilder politicianBuilder) {
        List<Politicians> result = new ArrayList<>();

        for (int i = 0; i < numberOfTimes; i++) {
            Politicians presidential = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder
                    .setPoliticianNumber(of(NumberTestFactory.POL_NUMBER().politicianNumber().concat(valueOf(i))).politicianNumber()))
                    .build();

            result.add(presidential);
        }

        return result;
    }

}
