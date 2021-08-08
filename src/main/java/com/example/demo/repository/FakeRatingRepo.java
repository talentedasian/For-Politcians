package com.example.demo.repository;

import com.example.demo.model.entities.PoliticiansRating;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

public class FakeRatingRepo extends AbstractFakeRatingRepository {

    int id = 0;

    Map<String, PoliticiansRating> database = new HashMap<>();

    @Override
    public Optional<PoliticiansRating> findById(Integer integer) {
        return database.get(String.valueOf(integer)) == null ? Optional.empty() : Optional.of(database.get(String.valueOf(integer)));
    }

    @Override
    public List<PoliticiansRating> findByRater_Email(String email) {
        List<PoliticiansRating> allValues = List.copyOf(database.values());
        List<PoliticiansRating> result = new ArrayList<>();
        for (PoliticiansRating entity : allValues) {
            if (entity.getRater().getEmail().equals(email)) {
                result.add(entity);
            }
        }

        return result;
    }

    @Override
    public long countByPolitician_Id(Integer id) {
        return 0;
    }

    @Override
    public List<PoliticiansRating> findByRater_UserAccountNumber(String accountNumber) {
        List<PoliticiansRating> allValues = List.copyOf(database.values());
        List<PoliticiansRating> result = new ArrayList<>();
        for (PoliticiansRating entity : allValues) {
            if (entity.getRater().getUserAccountNumber().equals(accountNumber)) {
                result.add(entity);
            }
        }

        return result;
    }

    @Override
    public void deleteByRater_UserAccountNumber(String accountNumber) {
        List<PoliticiansRating> allValues = List.copyOf(database.values());
        List<PoliticiansRating> result = new ArrayList<>();
        for (PoliticiansRating entity : allValues) {
            if (entity.getRater().getUserAccountNumber().equals(accountNumber)) {
                database.remove(entity);
            }
        }
    }

    @Override
    public boolean existsByRater_UserAccountNumber(String accountNumber) {
        List<PoliticiansRating> allValues = List.copyOf(database.values());
        for (PoliticiansRating entity : allValues) {
            if (entity.getRater().getUserAccountNumber().equals(accountNumber)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<PoliticiansRating> findAll() {
        return List.copyOf(database.values());
    }

    @Override
    public void delete(PoliticiansRating entity) {
        database.remove(String.valueOf(entity.getId()));
    }

    @Override
    public <S extends PoliticiansRating> S save(S entity) {
        entity.setId(++id);
        database.put(String.valueOf(id), entity);
        return entity;
    }

    @Override
    public <S extends PoliticiansRating> List<S> saveAll(Iterable<S> entities) {
        for (PoliticiansRating entity : entities) {
            if (database.containsKey(entity.getId())) {
                throw new DataIntegrityViolationException("Rating with " + entity.getId() + "already exists");
            }
            entity.setId(++id);
            database.put(String.valueOf(id), entity);

        }

        return (List<S>) List.copyOf(database.values());
    }

}
