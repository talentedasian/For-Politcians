package com.example.demo.adapter.out.jpa;

import com.example.demo.adapter.out.repository.RateLimitRepositoryJpa;
import com.example.demo.adapter.web.dto.RateLimitJpaEntity;
import com.example.demo.baseClasses.BaseClassTestsThatUsesDatabase;
import com.example.demo.baseClasses.NumberTestFactory;
import com.example.demo.domain.ExpirationZonedDate;
import com.example.demo.domain.entities.RateLimit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import testAnnotations.DatabaseTest;

import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class RateLimitJpaRepoTest extends BaseClassTestsThatUsesDatabase {

    @Autowired RateLimitRepositoryJpa repo;

    @Test
    public void shouldReturn2WhenQueryingCountUsingIdAndPoliticianNumber() {
        var rateLimit = new RateLimit("1", NumberTestFactory.POL_NUMBER(), ExpirationZonedDate.now().now());

        var entity = RateLimitJpaEntity.of(rateLimit);
        var entity2 = RateLimitJpaEntity.of(rateLimit);

        repo.save(entity);
        repo.save(entity2);

        long count = repo.countByAccountNumberAndPoliticianNumber("1", NumberTestFactory.POL_NUMBER().politicianNumber());
        assertThat(count)
                .isEqualTo(2);
    }

}
