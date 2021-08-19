package com.example.demo.adapter.in.web;

import com.example.demo.adapter.dto.PoliticianDto;
import com.example.demo.adapter.in.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.adapter.in.service.PoliticiansService;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.dtomapper.PoliticianDTOUnwrapper;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.exceptions.PoliticianNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliticianServiceAdapter {

    private final PoliticiansService service;

    public PoliticianServiceAdapter(PoliticiansRepository politiciansRepository) {
        this.service = new PoliticiansService(politiciansRepository);
    }

    public PoliticianDto findPoliticianUsingNumber(String polNumber) {
        Politicians politician = service.findPoliticianByNumber(polNumber)
                .orElseThrow(() -> new PoliticianNotFoundException("Politician does not exist by " + polNumber));
        return new PoliticiansDtoMapper().mapToDTO(politician);
    }

    public List<PoliticianDto> findPoliticianUsingName(String lastName, String firstName) {
        List<Politicians> politician = service.findPoliticianByName(lastName, firstName);
        if (politician.isEmpty()) {
            throw new PoliticianNotFoundException("Politicians with " + firstName + lastName + " as fullName does not exist");
        }

        return politician.stream()
                .map(entity -> new PoliticiansDtoMapper().mapToDTO(entity))
                .toList();
    }

    public List<PoliticianDto> allPoliticians() {
        return service.allPoliticians().stream()
                .map(entity -> new PoliticiansDtoMapper().mapToDTO(entity))
                .toList();
    }

    public PoliticianDto savePolitician(AddPoliticianDTORequest dtoRequest) {
        return new PoliticiansDtoMapper().mapToDTO(service.savePolitician(new PoliticianDTOUnwrapper().unWrapDTO(dtoRequest)));
    }

    public boolean deletePolitician(String polNumber) {
        if (!service.doesExistWithPoliticianNumber(polNumber)) {
            return false;
        }

        service.deletePolitician(polNumber);
        return true;
    }

}
