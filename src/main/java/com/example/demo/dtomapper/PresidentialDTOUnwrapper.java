package com.example.demo.dtomapper;

import com.example.demo.adapter.in.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.adapter.in.dtoRequest.AddPresidentialPoliticianDTORequest;
import com.example.demo.domain.AverageRating;
import com.example.demo.domain.entities.*;
import io.jsonwebtoken.lang.Assert;

import static com.example.demo.domain.entities.Politicians.Type.PRESIDENTIAL;
import static com.example.demo.domain.politicianNumber.PoliticianNumberCalculatorFactory.politicianCalculator;
import static java.math.BigDecimal.valueOf;

class PresidentialDTOUnwrapper extends PoliticianDTOUnwrapper {

    @Override
    public Politicians unWrapDTO(AddPoliticianDTORequest dto) {
        Assert.state(dto instanceof AddPresidentialPoliticianDTORequest,
                "request dto must be a type of presidential");

        return dtoToEntity((AddPresidentialPoliticianDTORequest) dto);
    }

    private Politicians dtoToEntity(AddPresidentialPoliticianDTORequest dto) {
        final PoliticianNumber POLITICIAN_NUMBER = politicianCalculator(PRESIDENTIAL)
                .calculatePoliticianNumber(new Name(dto.getFirstName(), dto.getLastName()));

        Politicians politician = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setFullName()
                .setRating(new Rating(0.00D, AverageRating.of(valueOf(0))))
                .setPoliticiansRating(null)
                .build();

        return new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politician)
                .setMostSignificantLawPassed(dto.getMostSignificantLawSigned())
                .build();
    }

}
