package com.example.demo.dtomapper;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.dtoRequest.AddPresidentialPoliticianDTORequest;
import com.example.demo.model.entities.Rating;
import com.example.demo.model.entities.politicians.PoliticianTypes;
import com.example.demo.model.entities.politicians.Politicians;
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
                .setLastName(dto.getLastName())
                .setFullName()
                .setRating(new Rating(0.00D, 0.00D))
                .build();

        return new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politician)
                .setMostSignificantLawPassed(dto.getMostSignificantLawSigned())
                .build();
    }

}
