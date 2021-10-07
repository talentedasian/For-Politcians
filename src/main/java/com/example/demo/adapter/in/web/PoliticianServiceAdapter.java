package com.example.demo.adapter.in.web;

import com.example.demo.adapter.in.dtoRequest.AddPoliticianDTORequest;
import com.example.demo.adapter.in.service.PoliticiansService;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.web.dto.PoliticianDto;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import com.example.demo.domain.entities.Politicians;
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
    private final PoliticiansRepository repo;

    public PoliticianServiceAdapter(PoliticiansRepository politiciansRepository) {
        this.service = new PoliticiansService(politiciansRepository);
        this.repo = politiciansRepository;
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
        var totalPage = session.getAttribute("total-page");

        if (doesRequestNotHaveExistingSession(totalPage)) return responseAndSession(session, page, itemsToFetch);

        if (checkIfRequestDoesNotHaveSameItemsToFetchAsLastRequest(itemsToFetch, session)) return responseAndSession(session, page, itemsToFetch);

        long total = (long) session.getAttribute("total");

        String pagedObjectAttribute = "paged-objects";
        PagedObject pagedObjectFromSession = (PagedObject) session.getAttribute(pagedObjectAttribute);

        PagedObject<Politicians> query = repo.findAllByPage(page, itemsToFetch, total);
        if (doesRequestQueryForLastPage(session, page)) {
            session.setAttribute(pagedObjectAttribute, pagedObjectFromSession.lastPage(() -> query.valuesAsList()));
        } else {
            session.setAttribute(pagedObjectAttribute, pagedObjectFromSession.toPage(() -> query.valuesAsList(), page));
        }

        return query.values().map(it -> new PoliticiansDtoMapper().mapToDTO(it)).toList();
    }

    private boolean doesRequestQueryForLastPage(HttpSession session, Page lastPage) {
        return ((long)session.getAttribute("total-page")) == lastPage.pageNumber() + 1;
    }

    private List<PoliticianDto> responseAndSession(HttpSession session, Page page, int itemsToFetch) {

        Long total = (Long) session.getAttribute("total");
        PagedObject<Politicians> allWithPage = service.findAllWithPage(page, itemsToFetch, total);

        session.setAttribute("paged-objects", allWithPage);
        session.setAttribute("total", allWithPage.totalElements());
        session.setAttribute("total-page", allWithPage.totalPages());
        session.setAttribute("items-to-fetch", itemsToFetch);
        session.setMaxInactiveInterval(300);

        return allWithPage.values().map(it -> new PoliticiansDtoMapper().mapToDTO(it)).toList();

    }

    private boolean doesRequestNotHaveExistingSession(Object totalPage) {
        return totalPage == null;
    }

    private boolean checkIfRequestDoesNotHaveSameItemsToFetchAsLastRequest(int itemsToFetch, HttpSession session) {
        return ((int) session.getAttribute("items-to-fetch") != itemsToFetch);
    }

}
