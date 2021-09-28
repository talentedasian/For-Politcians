package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.out.jpa.PoliticiansJpaEntity;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PoliticianJpaAdapterRepository implements PoliticiansRepository {

    private final PoliticiansJpaRepository repo;

    public PoliticianJpaAdapterRepository(PoliticiansJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Politicians save(Politicians politician) throws PoliticianNotPersistableException {
        if (politician.getType() == null) {
            throw new PoliticianNotPersistableException("Politician trying to persist does not have a type");
        }
        PoliticiansJpaEntity entitySaved = repo.save(PoliticiansJpaEntity.from(politician));

        return entitySaved.toPoliticians();
    }

    @Override
    public Politicians update(Politicians politician) {
        PoliticiansJpaEntity entitySaved = repo.save(PoliticiansJpaEntity.fromWithNullRating(politician));

        return entitySaved.toPoliticians();
    }

    @Override
    public List<Politicians> findByLastNameAndFirstName(String lastName, String firstName) {
        return repo.findByLastNameAndFirstName(lastName, firstName).stream()
                .map(entity -> entity.toPoliticians())
                .toList();
    }

    @Override
    public Optional<Politicians> findByPoliticianNumber(String polNumber) {
        Optional<PoliticiansJpaEntity> entity = repo.findById(polNumber);

        return entity.isEmpty() ? Optional.empty() : Optional.of(entity.get().toPoliticians());
    }

    @Override
    public boolean existsByPoliticianNumber(String polNumber) {
        return repo.existsById(polNumber);
    }

    @Override
    public void deleteByPoliticianNumber(String polNumber) {
        repo.deleteById(polNumber);
    }

    @Override
    public List<Politicians> findAll() {
        return repo.findAll().stream()
                .map(PoliticiansJpaEntity::toPoliticians)
                .toList();
    }

    @Override
    public PagedObject<Politicians> findAllByPage(Page page, int itemsToFetch, long total) {
        return PagedObject.of(repo.findAllWithPage(page.itemsToSkip(itemsToFetch), itemsToFetch).stream()
                .map(PoliticiansJpaEntity::toPoliticians).toList(), total, itemsToFetch, page);
    }

    @Override
    public long count() {
        return repo.count();
    }

}
