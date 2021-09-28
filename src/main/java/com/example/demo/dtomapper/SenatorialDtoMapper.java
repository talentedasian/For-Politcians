package com.example.demo.dtomapper;

import com.example.demo.adapter.web.dto.PoliticianDto;
import com.example.demo.adapter.web.dto.SenatorialPoliticianDto;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.entities.PoliticianTypes;
import com.example.demo.domain.enums.Rating;
import org.springframework.util.Assert;

import java.util.List;

import static java.util.stream.Collectors.toList;

/*
	Callers must use only the superclass PoliticiansDtoMapper.
 */
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
                    .peek(politicians -> {
                        Assert.state(politicians instanceof PoliticianTypes.SenatorialPolitician, "entity must be senatorial");
                    })
                    .map(politicians -> mapToDto((PoliticianTypes.SenatorialPolitician) politicians))
                    .collect(toList());
    }

    private SenatorialPoliticianDto mapToDto(PoliticianTypes.SenatorialPolitician entity) {
        Double rating = entity.averageRating();
        Rating satisfactionRate = Rating.mapToSatisfactionRate(rating);

        return new SenatorialPoliticianDto(entity, satisfactionRate, entity.getTotalMonthsOfServiceAsSenator(),entity.getMostSignificantLawMade());
    }

}
