package com.example.demo.adapter.web.dto;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.enums.Rating;
import com.example.demo.domain.entities.Politicians;

import static com.example.demo.dtomapper.PoliticiansDtoMapper.NO_RATING;

public class SenatorialPoliticianDto extends PoliticianDto {

    private final Integer monthsOfService;

    private final String mostSignificantLawMade;

    public int getMonthsOfService() {
        return this.monthsOfService;
    }

    public String getMostSignificantLawMade() {
        return this.mostSignificantLawMade;
    }

    private SenatorialPoliticianDto(String name, String id, String averageRating, Rating satisfactionRate,
                                    int monthsOfService, String mostSignificantLawMade) {
        super(name, id, averageRating, satisfactionRate, "SENATORIAL");
        this.monthsOfService = monthsOfService;
        this.mostSignificantLawMade = mostSignificantLawMade;
    }

    public static SenatorialPoliticianDto of(Politicians entity, Rating satisfactionRate, int monthsOfService, String lawMade) {
        String rating = entity.hasRating() ? String.valueOf(entity.averageRating()) : NO_RATING;
        return new SenatorialPoliticianDto(entity.fullName(), entity.retrievePoliticianNumber(), rating,
                satisfactionRate, monthsOfService, lawMade);
    }

    @Override
    @ExcludeFromJacocoGeneratedCoverage
    public String toString() {
        return "SenatorialPoliticianDto [fullName=" + this.getName() + ", id=" + this.getId() +
                ", rating=" + this.getRating() + ", satisfactionRate=" + this.getSatisfactionRate() +
                ", monthsOfService=" + this.monthsOfService + ", mostSignificantLawSigned=" + this.mostSignificantLawMade + "]";
    }

}
