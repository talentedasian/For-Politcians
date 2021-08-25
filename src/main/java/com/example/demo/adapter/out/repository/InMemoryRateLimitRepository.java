package com.example.demo.adapter.out.repository;

import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.RateLimit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryRateLimitRepository implements RateLimitRepository {

    Map<String, RateLimit> database = new HashMap<>();

    @Override
    public RateLimit save(RateLimit rateLimit) {
        return database.put(rateLimit.id(), rateLimit);
    }

    @Override
    public Optional<RateLimit> findUsingIdAndPoliticianNumber(String id, String politicianNumber) {
        for (RateLimit entity : List.copyOf(database.values())) {
            if (entity.id().equals(id) && entity.politicianNumber().equals(politicianNumber)) {
                return Optional.of(entity);
            }
        }

        return Optional.empty();
    }

    @Override
    public void deleteUsingIdAndPoliticianNumber(String id, String politicianNumber) {
        for (RateLimit entity : List.copyOf(database.values())) {
            if (entity.id().equals(id) && entity.politicianNumber().equals(politicianNumber)) {
                database.remove(entity);
            }
        }
    }

    @Override
    public long countUsingIdAndPoliticianNumber(String id, String polNumber) {
        long count = 0;
        for (RateLimit entity : List.copyOf(database.values())) {
            if (entity.id().equals(id) && entity.politicianNumber().equals(polNumber)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public List<RateLimit> findUsingId(String id) {
        return null;
    }
}
