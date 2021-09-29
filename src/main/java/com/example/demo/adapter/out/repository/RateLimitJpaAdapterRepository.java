package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.web.dto.RateLimitJpaEntity;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.RateLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class RateLimitJpaAdapterRepository implements RateLimitRepository {

    private final RateLimitRepositoryJpa rateRepo;

    @Autowired
    public RateLimitJpaAdapterRepository(RateLimitRepositoryJpa rateRepo) {
        this.rateRepo = rateRepo;
    }

    @Override
    @Transactional
    public RateLimit save(RateLimit rateLimit) {
        var dto = RateLimitJpaEntity.of(rateLimit);

        dto = rateRepo.save(dto);

        return dto.toRateLimit();
    }

    @Override
    public Optional<RateLimit> findUsingIdAndPoliticianNumber(String id, PoliticianNumber politicianNumber) {
        var queriedDto = rateRepo.findByAccountNumberAndPoliticianNumber(id, politicianNumber.politicianNumber());

        return queriedDto.isPresent() ? Optional.of(queriedDto.get().toRateLimit()) : Optional.empty();
    }

    @Override
    public void deleteUsingIdAndPoliticianNumber(String id, PoliticianNumber politicianNumber) {
        rateRepo.deleteByAccountNumberAndPoliticianNumber(id, politicianNumber.politicianNumber());
    }

    @Override
    public long countUsingIdAndPoliticianNumber(String id, PoliticianNumber polNumber) {
        return rateRepo.countByAccountNumberAndPoliticianNumber(id, polNumber.politicianNumber());
    }

    @Override
    public List<RateLimit> findUsingId(String id) {
        return rateRepo.findByAccountNumber(id).stream()
                .map(it -> it.toRateLimit())
                .toList();
    }

    @Override
    public void saveAll(List<RateLimit> rateLimits) {
        rateRepo.saveAll(rateLimits.stream().map(RateLimitJpaEntity::of).toList());
    }

}
