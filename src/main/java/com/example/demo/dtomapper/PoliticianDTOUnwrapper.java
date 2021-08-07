package com.example.demo.dtomapper;

import com.example.demo.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.model.entities.politicians.Politicians;

public class PoliticianDTOUnwrapper {

    public Politicians unWrapDTO(AddPoliticianDTORequest dto) {
        switch (Politicians.Type.mapToPoliticianType(dto.getType())) {
            case PRESIDENTIAL -> {return new PresidentialDTOUnwrapper().unWrapDTO(dto);}
            case SENATORIAL -> {return new SenatorialDTOUnwrapper().unWrapDTO(dto);}
            default -> throw new IllegalStateException("Unexpected value: " + Politicians.Type.mapToPoliticianType(dto.getType()));
        }
    }
}
