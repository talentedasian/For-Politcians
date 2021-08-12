package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.out.jpa.PoliticiansJpaEntity;
import com.example.demo.domain.politicians.Politicians;

import java.util.List;
import java.util.Optional;

public class PoliticianJpaAdapterRepository implements PoliticiansRepository {

    private final PoliticiansJpaRepository politicianRepository;

    public PoliticianJpaAdapterRepository(PoliticiansJpaRepository politicianRepository) {
        this.politicianRepository = politicianRepository;
    }

    @Override
    public Politicians save(Politicians politician) {
        PoliticiansJpaEntity entitySaved = politicianRepository.save(PoliticiansJpaEntity.from(politician));

        return entitySaved.toPoliticians();
    }

    @Override
    public List<Politicians> findByLastNameAndFirstName(String lastName, String firstName) {
        return politicianRepository.findByLastNameAndFirstName(lastName, firstName);
    }

    @Override
    public Optional<Politicians> findByPoliticianNumber(String polNumber) {
        return politicianRepository.findByPoliticianNumber(polNumber);
    }

    @Override
    public boolean existsByPoliticianNumber(String polNumber) {
        return politicianRepository.existsByPoliticianNumber(polNumber);
    }

    @Override
    public void deleteByPoliticianNumber(String polNumber) {
        politicianRepository.deleteByPoliticianNumber(polNumber);
    }

    @Override
    public List<Politicians> findAll() {
        return politicianRepository.findAll().stream()
                .map(PoliticiansJpaEntity::toPoliticians)
                .toList();
    }
}
