package com.example.demo.repository;

import com.example.demo.model.entities.politicians.Politicians;

import java.util.List;
import java.util.Optional;

public class FakePoliticianRepository extends AbstractFakePoliticiansRepo {
    @Override
    public List<Politicians> findByLastNameAndFirstName(String lastName, String firstName) {
        return null;
    }

    @Override
    public Optional<Politicians> findByPoliticianNumber(String polNumber) {
        return Optional.empty();
    }

    @Override
    public boolean existsByPoliticianNumber(String polNumber) {
        return false;
    }

    @Override
    public void deleteByPoliticianNumber(String polNumber) {

    }

    @Override
    public List<Politicians> findAll() {
        return null;
    }

    @Override
    public <S extends Politicians> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Politicians> List<S> saveAll(Iterable<S> entities) {
        return null;
    }
}
