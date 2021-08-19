package com.example.demo.dtomapper;

import com.example.demo.adapter.in.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.adapter.in.dtoRequest.AddPresidentialPoliticianDTORequest;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.Politicians;
import io.jsonwebtoken.lang.Assert;

class PresidentialDTOUnwrapper extends PoliticianDTOUnwrapper {

    @Override
    public Politicians unWrapDTO(AddPoliticianDTORequest dto) {
        Assert.state(dto instanceof AddPresidentialPoliticianDTORequest,
                "request dto must be a type of presidential");

        return dtoToEntity((AddPresidentialPoliticianDTORequest) dto);
    }

    private Politicians dtoToEntity(AddPresidentialPoliticianDTORequest dto) {
        Politicians politician = new Politicians.PoliticiansBuilder("dummy")
                .setFirstName(dto.getFirstName())
                .setLastName(dto.lastName())
                .setFullName()
                .setRating(new Rating(0.00D, 0.00D))
                .build();

        return new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politician)
                .setMostSignificantLawPassed(dto.getMostSignificantLawSigned())
                .build();
    }

}
