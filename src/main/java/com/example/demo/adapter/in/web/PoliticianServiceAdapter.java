package com.example.demo.adapter.in.web;

import com.example.demo.adapter.in.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.adapter.in.service.PoliticiansService;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.web.dto.PoliticianDto;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import com.example.demo.domain.PagedResult;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.dtomapper.PoliticianDTOUnwrapper;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.exceptions.PoliticianNotFoundException;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    public PoliticianDto savePolitician(AddPoliticianDTORequest dtoRequest) throws PoliticianNotPersistableException {
        return new PoliticiansDtoMapper().mapToDTO(service.savePolitician(new PoliticianDTOUnwrapper().unWrapDTO(dtoRequest)));
    }

    public boolean deletePolitician(String polNumber) {
        if (!service.doesExistWithPoliticianNumber(polNumber)) {
            return false;
        }

        service.deletePolitician(polNumber);
        return true;
    }

    public List<PoliticianDto> allPoliticiansWithPage(Page page, int itemsToFetch, HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        Integer pageNumber = (Integer) session.getAttribute("page-number");
        if (pageNumber == null) {
            PagedObject<Politicians> allWithPage = service.findAllWithPage(page, itemsToFetch);

            session.setAttribute("paged-objects", allWithPage);
            session.setAttribute("page-number", Integer.valueOf(page.pageNumber()));
            return allWithPage.values().map(it -> new PoliticiansDtoMapper().mapToDTO(it)).toList();
        }

        PagedResult<Politicians> attribute = (PagedResult<Politicians>) session.getAttribute("paged-objects");
        PagedObject<Politicians> currentPage = attribute.ofPage(Page.of(pageNumber));

        return currentPage.values().toList().stream().map(it -> new PoliticiansDtoMapper().mapToDTO(it)).toList();
    }

}
