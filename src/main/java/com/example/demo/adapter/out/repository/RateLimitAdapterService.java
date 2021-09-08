package com.example.demo.adapter.out.repository;

import com.example.demo.adapter.web.dto.RateLimitJpaEntity;
import com.example.demo.adapter.in.service.RateLimitingService;
import com.example.demo.domain.RateLimitRepository;
import com.example.demo.exceptions.RateLimitNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RateLimitAdapterService {

    private final RateLimitingService service;

    public RateLimitAdapterService(RateLimitRepository rateRepo) {
        this.service = new RateLimitingService(rateRepo);
    }

    @Transactional(readOnly = true)
    public RateLimitJpaEntity findUsingAccountNumberAndPoliticianNumber(RateLimitJpaEntity dto) {
        var rateLimit = service.findRateLimitInPolitician(dto.getAccountNumber(), dto.getPoliticianNumber())
                .orElseThrow(() -> new RateLimitNotFoundException("User is not rate limited on this politician"));

        return RateLimitJpaEntity.of(rateLimit);
    }


}
