package com.example.demo.domain;

import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.exceptions.PoliticianAlreadyExistsException;
import com.example.demo.exceptions.PoliticianNotPersistableException;

import java.util.*;

public class InMemoryPoliticianAdapterRepo implements PoliticiansRepository {

    Map<String, Politicians> database = new HashMap<>();

    @Override
    public Politicians save(Politicians politician) throws PoliticianNotPersistableException {
        if (politician.getType() == null) {
            throw new PoliticianNotPersistableException("Politician trying to persist does not have a type");
        }

        if (database.containsKey(politician.retrievePoliticianNumber())) {
            throw new PoliticianAlreadyExistsException("Politician already exists in the database");
        }
        database.put(politician.retrievePoliticianNumber(), politician);
        return politician;
    }

    @Override
    public Politicians update(Politicians politician) {
        return database.put(politician.retrievePoliticianNumber(), politician);
    }

    @Override
    public List<Politicians> findByLastNameAndFirstName(String lastName, String firstName) {
        List<Politicians> result = new ArrayList<>();
        for (Politicians entity : List.copyOf(database.values())) {
            if (entity.firstName().equals(firstName) && entity.lastName().equals(lastName)) {
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

    @Override
    public PagedResult<Politicians> findAllByPage(Page page, int itemsToFetch) {
        return PagedResult.of(database.values().stream().skip(page.itemsToSkip(10)).limit(itemsToFetch).toList());
    }

}
