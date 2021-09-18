package com.example.demo.adapter.in.web.dto;

import com.example.demo.domain.entities.RateLimit;
import com.example.demo.domain.entities.PoliticianNumber;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

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
        return new RateLimitDto(rateLimit.id(), rateLimit.politicianNumber(), rateLimit.daysLeftOfBeingRateLimited().longValue());
    }


    public RateLimit toRateLimit() {
        var dateNow = LocalDate.now();

        if (daysLeftToRateAgain == 0l) {
            return new RateLimit(id, new PoliticianNumber(politicianNumber), dateNow.minusDays(7));
        }

        var dateCreated = Integer.signum(Long.valueOf(daysLeftToRateAgain).intValue()) == 0 ?
                   dateNow.minusDays(7).plusDays(daysLeftToRateAgain) : dateNow.minusDays(7).minusDays(Math.abs(daysLeftToRateAgain));

        return new RateLimit(id, new PoliticianNumber(politicianNumber), dateCreated);
    }
}
