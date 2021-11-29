package com.example.demo.adapter.web.dto;

import com.example.demo.annotations.ExcludeFromJacocoGeneratedCoverage;
import com.example.demo.domain.enums.Rating;
import com.example.demo.domain.entities.Politicians;

import java.util.Objects;

import static com.example.demo.dtomapper.PoliticiansDtoMapper.NO_RATING;

public class SenatorialPoliticianDto extends PoliticianDto {

    private final Integer monthsOfService;

    private final String mostSignificantLawMade;

    public Integer getMonthsOfService() {
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
        String rating = entity.hasRating() ? entity.averageRating() : NO_RATING;
        return new SenatorialPoliticianDto(entity.fullName(), entity.retrievePoliticianNumber(), rating,
                satisfactionRate, monthsOfService, lawMade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SenatorialPoliticianDto that = (SenatorialPoliticianDto) o;

        if (!Objects.equals(monthsOfService, that.monthsOfService))
            return false;
        return Objects.equals(mostSignificantLawMade, that.mostSignificantLawMade);
    }

    @Override
    @ExcludeFromJacocoGeneratedCoverage
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (monthsOfService != null ? monthsOfService.hashCode() : 0);
        result = 31 * result + (mostSignificantLawMade != null ? mostSignificantLawMade.hashCode() : 0);
        return result;
    }

    @Override
    @ExcludeFromJacocoGeneratedCoverage
    public String toString() {
        return "SenatorialPoliticianDto [fullName=" + this.getName() + ", id=" + this.getId() +
                ", rating=" + this.getRating() + ", satisfactionRate=" + this.getSatisfactionRate() +
                ", monthsOfService=" + this.monthsOfService + ", mostSignificantLawSigned=" + this.mostSignificantLawMade + "]";
    }

}
