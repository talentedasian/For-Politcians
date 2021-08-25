package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.dto.RateLimitJpaEntity;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.RateLimit;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RateLimitJpaAdapterRepository implements RateLimitRepository {

    private final RateLimitRepositoryJpa rateRepo;

    public RateLimitJpaAdapterRepository(RateLimitRepositoryJpa rateRepo) {
        this.rateRepo = rateRepo;
    }

    @Override
    public RateLimit save(RateLimit rateLimit) {
        var dto = RateLimitJpaEntity.of(rateLimit);

        dto = rateRepo.save(dto);

        return dto.toRateLimit();
    }

    @Override
    public Optional<RateLimit> findUsingIdAndPoliticianNumber(String id, String politicianNumber) {
        var dto = new RateLimitJpaEntity(id, politicianNumber);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("daysLeft");

        var queriedDto = rateRepo.findOne(Example.of(dto, matcher));

        return queriedDto.isPresent() ? Optional.of(queriedDto.get().toRateLimit()) : Optional.empty();
    }

    @Override
    public void deleteUsingIdAndPoliticianNumber(String id, String politicianNumber) {
        var rateLimit = new RateLimit();
        rateLimit.setId(id);
        rateLimit.setPoliticianNumber(politicianNumber);

        rateRepo.deleteByAccountNumberAndPoliticianNumber(id, politicianNumber);
    }

    @Override
    public long countUsingIdAndPoliticianNumber(String id, String polNumber) {
        var rateLimit = new RateLimitJpaEntity(id, polNumber);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("accountNumber", match -> match.exact())
                .withMatcher("politicianNumber", match -> match.exact());

        return rateRepo.count(Example.of(rateLimit, matcher));
    }

    @Override
    public List<RateLimit> findUsingId(String id) {
        return rateRepo.findByAccountNumber(id).stream()
                .map(it -> it.toRateLimit())
                .toList();
    }
}
