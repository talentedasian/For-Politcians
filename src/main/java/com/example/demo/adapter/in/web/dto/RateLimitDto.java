package com.example.demo.adapter.in.web.dto;

import com.example.demo.adapter.dto.RateLimitJpaDto;
import com.example.demo.domain.entities.RateLimit;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.hateoas.RepresentationModel;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RateLimitDto extends RepresentationModel<RateLimitJpaDto> {

    private final String id, politicianNumber;

    @JsonProperty("days_left_until_rate")
    private final long daysLeftToRateAgain;

    public RateLimitDto(String id, String politicianNumber, long daysLeftToRateAgain) {
        this.id = id;
        this.politicianNumber = politicianNumber;
        this.daysLeftToRateAgain = daysLeftToRateAgain;
    }

    public static RateLimitDto from(RateLimit rateLimit) {
        return new RateLimitDto(rateLimit.id(), rateLimit.politicianNumber(), rateLimit.daysLeftOfBeingRateLimited().longValue());
    }


}
