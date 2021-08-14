package com.example.demo.domain;

import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.exceptions.PoliticianAlreadyExistsException;

import java.util.*;

public class InMemoryPoliticianAdapterRepo implements PoliticiansRepository {

    Map<String, Politicians> database = new HashMap<>();

    @Override
    public Politicians save(Politicians politician) {
        if (database.containsKey(politician.getPoliticianNumber())) {
            throw new PoliticianAlreadyExistsException("Politician already exists in the database");
        }
        database.put(politician.getPoliticianNumber(), politician);
        return politician;
    }

    @Override
    public Politicians update(Politicians politician) {
        return database.put(politician.getPoliticianNumber(), politician);
    }

    @Override
    public List<Politicians> findByLastNameAndFirstName(String lastName, String firstName) {
        List<Politicians> result = new ArrayList<>();
        for (Politicians entity : List.copyOf(database.values())) {
            if (entity.getFirstName().equals(firstName) && entity.getLastName().equals(lastName)) {
                result.add(entity);
            }
        }
        return result;
    }

    @Override
    public Optional<Politicians> findByPoliticianNumber(String polNumber) {
        Politicians entity = database.get(polNumber);
        return entity == null ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public boolean existsByPoliticianNumber(String polNumber) {
        return database.containsKey(polNumber);
    }

    @Override
    public void deleteByPoliticianNumber(String polNumber) {
        database.remove(polNumber);
    }

    @Override
    public List<Politicians> findAll() {
        return List.copyOf(database.values());
    }
}
