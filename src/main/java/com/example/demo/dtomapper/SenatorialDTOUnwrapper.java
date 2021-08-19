package com.example.demo.dtomapper;

import com.example.demo.adapter.in.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.adapter.in.dtoRequest.AddSenatorialPoliticianDtoRequest;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.Politicians;
import io.jsonwebtoken.lang.Assert;

class SenatorialDTOUnwrapper extends PoliticianDTOUnwrapper{

    @Override
    public Politicians unWrapDTO(AddPoliticianDTORequest dto) {
        Assert.state(dto instanceof AddSenatorialPoliticianDtoRequest,
                "request dto must be a type of senatorial");

        return dtoToEntity((AddSenatorialPoliticianDtoRequest) dto);
    }

    private Politicians dtoToEntity(AddSenatorialPoliticianDtoRequest dto) {
        Politicians politician = new Politicians.PoliticiansBuilder("dummy")
                .setFirstName(dto.getFirstName())
                .setLastName(dto.lastName())
                .setFullName()
                .setRating(new Rating(0.00D, 0.00D))
                .build();

        return new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politician)
                .setTotalMonthsOfService(dto.getMonthsOfService())
                .setMostSignificantLawMade(dto.getlawMade())
                .build();
    }

}
