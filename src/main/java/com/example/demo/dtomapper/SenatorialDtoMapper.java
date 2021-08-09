package com.example.demo.dtomapper;

import com.example.demo.adapter.dto.PoliticianDTO;
import com.example.demo.adapter.dto.SenatorialPoliticianDTO;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.enums.Rating;
import org.springframework.util.Assert;

import java.util.List;

import static java.util.stream.Collectors.toList;

/*
	Callers must use only the superclass PoliticiansDtoMapper.
 */
final class SenatorialDtoMapper extends PoliticiansDtoMapper {

    @Override
    public SenatorialPoliticianDTO mapToDTO(Politicians entity) {
        org.springframework.util.Assert.state(PoliticianTypes.SenatorialPolitician.class.isInstance(entity),
                "entity must be of type senatorial");
        return mapToDto((PoliticianTypes.SenatorialPolitician) entity);
    }

    @Override
    public List<PoliticianDTO> mapToDTO(List<Politicians> entity) {
        if (entity.size() == 0) {
            return List.of();
        };

        return 	entity.stream()
                    .peek(politicians -> {
                        Assert.state(PoliticianTypes.SenatorialPolitician.class.isInstance(politicians), "entity must be senatorial");
                    })
                    .map(politicians -> mapToDto((PoliticianTypes.SenatorialPolitician) politicians))
                    .collect(toList());
    }

    private SenatorialPoliticianDTO mapToDto(PoliticianTypes.SenatorialPolitician entity) {
        Double rating = entity.getRating().getAverageRating();
        Rating satisfactionRate = Rating.mapToSatisfactionRate(rating);

        return new SenatorialPoliticianDTO(entity, satisfactionRate, entity.getTotalMonthsOfServiceAsSenator(),entity.getMostSignificantLawMade());
    }

}
