package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.dto.RateLimitJpaDto;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.domain.entities.RateLimit;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.Optional;

public class RateLimitJpaAdapterRepository implements RateLimitRepository {

    private final RateLimitRepositoryJpa rateRepo;

    public RateLimitJpaAdapterRepository(RateLimitRepositoryJpa rateRepo) {
        this.rateRepo = rateRepo;
    }

    @Override
    public RateLimit save(RateLimit rateLimit) {
        var dto = RateLimitJpaDto.of(rateLimit);

        dto = rateRepo.save(dto);

        return dto.toRateLimit();
    }

    @Override
    public Optional<RateLimit> findUsingIdAndPoliticianNumber(String id, String politicianNumber) {
        var dto = new RateLimitJpaDto(id, politicianNumber);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("accountNumber", match -> match.exact())
                .withMatcher("politicianNumber", match -> match.exact());

        var queriedDto = rateRepo.findOne(Example.of(dto, matcher));

        return queriedDto.isPresent() ? Optional.of(queriedDto.get().toRateLimit()) : Optional.empty();
    }

    @Override
    public void deleteUsingIdAndPoliticianNumber(String id, String politicianNumber) {
        var rateLimit = new RateLimit();
        rateLimit.setId(id);
        rateLimit.setPoliticianNumber(politicianNumber);

        rateRepo.deleteByIdAndPoliticianNumber(id, politicianNumber);
    }

    @Override
    public long countUsingIdAndPoliticianNumber(String id, String polNumber) {
        var rateLimit = new RateLimitJpaDto(id, polNumber);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("accountNumber", match -> match.exact())
                .withMatcher("politicianNumber", match -> match.exact());

        return rateRepo.count(Example.of(rateLimit, matcher));
    }
}
