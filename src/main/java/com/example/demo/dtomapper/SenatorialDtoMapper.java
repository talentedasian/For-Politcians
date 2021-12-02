package com.example.demo.dtomapper;

import com.example.demo.adapter.web.dto.PoliticianDto;
import com.example.demo.adapter.web.dto.SenatorialPoliticianDto;
import com.example.demo.domain.entities.PoliticianTypes;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.enums.Rating;

import java.util.List;

import static java.util.stream.Collectors.toList;

final class SenatorialDtoMapper extends PoliticiansDtoMapper {

    @Override
    public SenatorialPoliticianDto mapToDTO(Politicians entity) {
        org.springframework.util.Assert.state(entity instanceof PoliticianTypes.SenatorialPolitician,
                "entity must be of type senatorial");
        return mapToDto((PoliticianTypes.SenatorialPolitician) entity);
    }

    @Override
    public List<PoliticianDto> mapToDTO(List<Politicians> entity) {
        if (entity.size() == 0) {
            return List.of();
        }

        return 	entity.stream()
                    .map(politicians -> mapToDTO(politicians))
                    .collect(toList());
    }

    private SenatorialPoliticianDto mapToDto(PoliticianTypes.SenatorialPolitician entity) {
        boolean polHasRating = entity.hasRating();
        String rating = polHasRating ? entity.averageRating() : "N/A";
        Rating satisfactionRate = polHasRating ? Rating.LOW : Rating.mapToSatisfactionRate(Double.parseDouble(rating));

        return SenatorialPoliticianDto.of(entity, satisfactionRate, entity.getTotalMonthsOfServiceAsSenator(),entity.getMostSignificantLawMade());
    }

}
