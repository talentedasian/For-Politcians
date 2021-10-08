package com.example.demo.adapter.web;

import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.domain.entities.Politicians.PoliticiansBuilder;
import com.example.demo.exceptions.PoliticianNotPersistableException;

import static com.example.demo.baseClasses.MultiplePoliticianSetup.pagedPoliticianSetupPresidential;
import static com.example.demo.baseClasses.MultiplePoliticianSetup.pagedPoliticianSetupSenatorial;

public class PaginationSetup {

    public static void pagedSetupPresidential(int numberOfTimes, PoliticiansBuilder politicianBuilder, PoliticiansRepository polRepo) {
        pagedPoliticianSetupPresidential(numberOfTimes, politicianBuilder).stream().forEach(it -> {
            try {
                polRepo.save(it);
            } catch (PoliticianNotPersistableException e) {
                e.printStackTrace();
            }
        });
    }

    public static void pagedSetupSenatorial(int numberOfTimes, PoliticiansBuilder politicianBuilder, PoliticiansRepository polRepo) {
        pagedPoliticianSetupSenatorial(numberOfTimes, politicianBuilder).stream().forEach(it -> {
            try {
                polRepo.save(it);
            } catch (PoliticianNotPersistableException e) {
                e.printStackTrace();
            }
        });
    }

}
