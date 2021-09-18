package com.example.demo.adapter.out.repository;

import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.RateLimit;
import com.example.demo.domain.entities.PoliticianNumber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryRateLimitRepository implements RateLimitRepository {

    Map<String, RateLimit> database = new HashMap<>();

    @Override
    public RateLimit save(RateLimit rateLimit) {
        database.put(rateLimit.id(), rateLimit);
        return database.get(rateLimit.id());
    }

    @Override
    public Optional<RateLimit> findUsingIdAndPoliticianNumber(String id, PoliticianNumber politicianNumber) {
        for (RateLimit entity : List.copyOf(database.values())) {
            if (entity.id().equals(id) && entity.politicianNumber().equals(politicianNumber.politicianNumber())) {
                return Optional.of(entity);
            }
        }

        return Optional.empty();
    }

    @Override
    public void deleteUsingIdAndPoliticianNumber(String id, PoliticianNumber politicianNumber) {
        for (RateLimit entity : List.copyOf(database.values())) {
            if (entity.id().equals(id) && entity.politicianNumber().equals(politicianNumber.politicianNumber())) {
                database.remove(entity);
            }
        }
    }

    @Override
    public long countUsingIdAndPoliticianNumber(String id, PoliticianNumber polNumber) {
        long count = 0;
        for (RateLimit entity : List.copyOf(database.values())) {
            if (entity.id().equals(id) && entity.politicianNumber().equals(polNumber.politicianNumber())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public List<RateLimit> findUsingId(String id) {
        return null;
    }

    @Override
    public void saveAll(List<RateLimit> rateLimits) {
        rateLimits.stream()
                .forEach(it -> database.put(it.id(), it));
    }
}
