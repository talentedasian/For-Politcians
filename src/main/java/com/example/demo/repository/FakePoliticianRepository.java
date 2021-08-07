package com.example.demo.repository;

import com.example.demo.exceptions.PoliticianAlreadyExistsException;
import com.example.demo.model.entities.politicians.Politicians;

import java.util.*;

public class FakePoliticianRepository extends AbstractFakePoliticiansRepo {

    private Map<String, Politicians> database = new HashMap<>();

    @Override
    public List<Politicians> findByLastNameAndFirstName(String lastName, String firstName) {
        List<Politicians> allValues = List.copyOf(database.values());
        List<Politicians> result = new ArrayList<>();
        for (Politicians entity : allValues) {
            if (entity.getFirstName().equals(firstName) && entity.getLastName().equals(lastName)) {
                result.add(entity);
            }
        }
        return result;
    }

    @Override
    public Optional<Politicians> findByPoliticianNumber(String polNumber) {
        return database.get(polNumber) == null ? Optional.empty() : Optional.of(database.get(polNumber));
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

    @Override
    public void delete(Politicians entity) {
        database.remove(entity.getPoliticianNumber());
    }

    @Override
    public <S extends Politicians> S save(S entity) {
        database.put(entity.getPoliticianNumber(), entity);
        return entity;
    }

    @Override
    public <S extends Politicians> List<S> saveAll(Iterable<S> entities) {
        for (Politicians entity : entities) {
            if (database.containsKey(entity.getPoliticianNumber())) {
                throw new PoliticianAlreadyExistsException("Politician with " + entity.getPoliticianNumber() + " already exists");
            }
            database.put(entity.getPoliticianNumber(), entity);
        }
        return (List<S>) List.copyOf(database.values());
    }

}
