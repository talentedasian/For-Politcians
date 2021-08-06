package com.example.demo.dtomapper;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.dtoRequest.AddSenatorialPoliticianDTORequest;
import com.example.demo.model.averageCalculator.LowSatisfactionAverageCalculator;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.Politicians;
import io.jsonwebtoken.lang.Assert;

class SenatorialDTOUnwrapper extends PoliticianDTOUnwrapper{

    @Override
    public Politicians unWrapDTO(AddPoliticianDTORequest dto) {
        Assert.state(dto instanceof AddSenatorialPoliticianDTORequest,
                "request dto must be a type of senatorial");

        return dtoToEntity((AddSenatorialPoliticianDTORequest) dto);
    }

    private Politicians dtoToEntity(AddSenatorialPoliticianDTORequest dto) {
        Politicians politician = new Politicians.PoliticiansBuilder("dummy")
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setFullName()
                .setRating(new Rating(0.00D, 0.00D, new LowSatisfactionAverageCalculator(0.00D, 0D)))
                .build();

        var entity = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politician)
                .setTotalMonthsOfService(dto.getMonthsOfService())
                .setMostSignificantLawMade(dto.getlawMade())
                .build();

        return entity;
    }

}
