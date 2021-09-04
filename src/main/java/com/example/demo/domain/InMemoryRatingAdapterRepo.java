package com.example.demo.domain;

import com.example.demo.adapter.out.repository.RatingRepository;
import com.example.demo.domain.entities.PoliticiansRating;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryRatingAdapterRepo implements RatingRepository {

    Map<String, PoliticiansRating> database = new HashMap<>();

    @Override
    public Optional<PoliticiansRating> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public PoliticiansRating save(PoliticiansRating rating) {
        database.put(rating.getId().toString(), rating);
        return database.get(rating.getId().toString());
    }

    @Override
    public List<PoliticiansRating> findByRater_Email(String email) {
        return List.copyOf(database.values()).stream()
                .filter(it -> it.getRater().email().equals(email))
                .toList();
    }

    @Override
    public long countByPolitician_Id(Integer id) {
        return List.copyOf(database.values()).stream()
                .filter(it -> it.getPolitician().retrievePoliticianNumber().equals(id.toString()))
                .count();
    }

    @Override
    public List<PoliticiansRating> findByRater_UserAccountNumber(String accountNumber) {
        return List.copyOf(database.values()).stream()
                .filter(it -> it.getRater().returnUserAccountNumber().equals(accountNumber))
                .toList();
    }

    @Override
    public void deleteByRater_UserAccountNumber(String accountNumber) {
        List.copyOf(database.values()).stream()
            .filter(it -> it.getRater().returnUserAccountNumber().equals(accountNumber))
            .forEach(it -> database.remove(it.getId().toString()));
    }

    @Override
    public boolean existsByRater_UserAccountNumber(String accountNumber) {
        return List.copyOf(database.values()).stream()
                .filter(it -> it.getRater().returnUserAccountNumber().equals(accountNumber))
                .count() > 1;
    }

    @Override
    public void deleteById(Integer id) {
        database.remove(id.toString());
    }
}