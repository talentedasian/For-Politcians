package com.example.demo.adapter.in.web.dto;

import com.example.demo.domain.entities.RateLimit;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.hateoas.RepresentationModel;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RateLimitDto extends RepresentationModel<RateLimitDto> {

    private final String id, politicianNumber;

    private final long daysLeftToRateAgain;

    public String getId() {
        return id;
    }

    public String getPoliticianNumber() {
        return politicianNumber;
    }

    public long getDaysLeftToRateAgain() {
        return daysLeftToRateAgain;
    }

    public RateLimitDto(String id, String politicianNumber, long daysLeftToRateAgain) {
        this.id = id;
        this.politicianNumber = politicianNumber;
        this.daysLeftToRateAgain = daysLeftToRateAgain;
    }

    public static RateLimitDto from(RateLimit rateLimit) {
        Integer integer;
        try {
            integer = rateLimit.daysLeftOfBeingRateLimited();
        } catch(IllegalStateException e) {
            integer = 0;
        }
        return new RateLimitDto(rateLimit.id(), rateLimit.politicianNumber(), integer.longValue());
    }


    public boolean isNotRateLimited() {
        return daysLeftToRateAgain == 0;
    }
}
