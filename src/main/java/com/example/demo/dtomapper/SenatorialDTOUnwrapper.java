package com.example.demo.dtomapper;

import com.example.demo.adapter.in.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.adapter.in.dtoRequest.AddSenatorialPoliticianDtoRequest;
import com.example.demo.domain.AverageRating;
import com.example.demo.domain.entities.Name;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.PoliticianTypes;
import com.example.demo.domain.entities.Politicians;
import io.jsonwebtoken.lang.Assert;

import static com.example.demo.domain.entities.Politicians.Type.SENATORIAL;
import static com.example.demo.domain.politicianNumber.PoliticianNumberCalculatorFactory.politicianCalculator;

class SenatorialDTOUnwrapper extends PoliticianDTOUnwrapper{

    @Override
    public Politicians unWrapDTO(AddPoliticianDTORequest dto) {
        Assert.state(dto instanceof AddSenatorialPoliticianDtoRequest,
                "request dto must be a type of senatorial");

        return dtoToEntity((AddSenatorialPoliticianDtoRequest) dto);
    }

    private Politicians dtoToEntity(AddSenatorialPoliticianDtoRequest dto) {
        final PoliticianNumber POLITICIAN_NUMBER = politicianCalculator(SENATORIAL)
                .calculatePoliticianNumber(Name.of(dto.getFirstName(), dto.getLastName()));

        Politicians politician = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setAverageRating(AverageRating.NO_RATING_YET)
                .build();

        return new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politician)
                .setTotalMonthsOfService(dto.getMonthsOfService())
                .setMostSignificantLawMade(dto.getlawMade())
                .build();
    }

}
