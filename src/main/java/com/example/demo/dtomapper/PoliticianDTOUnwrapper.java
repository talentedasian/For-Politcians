package com.example.demo.dtomapper;

import com.example.demo.adapter.in.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.domain.politicians.Politicians;

public class PoliticianDTOUnwrapper {

    public Politicians unWrapDTO(AddPoliticianDTORequest dto) {
        switch (Politicians.Type.mapToPoliticianType(dto.getType())) {
            case PRESIDENTIAL -> {return new PresidentialDTOUnwrapper().unWrapDTO(dto);}
            case SENATORIAL -> {return new SenatorialDTOUnwrapper().unWrapDTO(dto);}
            default -> throw new IllegalStateException("Unexpected value: " + Politicians.Type.mapToPoliticianType(dto.getType()));
        }
    }
}
