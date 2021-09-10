package com.example.demo.adapter.web;

import com.example.demo.adapter.in.web.PoliticianServiceAdapter;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.web.dto.PoliticianDto;
import com.example.demo.domain.InMemoryPoliticianAdapterRepo;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedResult;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.Name;
import com.example.demo.domain.politicians.PoliticianNumber;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpSession;

import static com.example.demo.baseClasses.MultiplePoliticianSetup.pagedPoliticianSetup;
import static com.example.demo.domain.politicianNumber.PoliticianNumberCalculatorFactory.politicianCalculator;
import static com.example.demo.domain.politicians.Politicians.Type.PRESIDENTIAL;
import static org.assertj.core.api.Assertions.assertThat;

public class PoliticianPaginationServiceAdapterTest {

    final String FIRST_NAME = "Mirriam";
    final String LAST_NAME = "Defensor";

    PoliticiansRepository polRepo = new InMemoryPoliticianAdapterRepo();

    Politicians.PoliticiansBuilder politicianBuilder;

    final PoliticianNumber POLITICIAN_NUMBER = politicianCalculator(PRESIDENTIAL).calculatePoliticianNumber(Name.of(FIRST_NAME, LAST_NAME));

    @BeforeEach
    public void setup() {
        politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
                .setPoliticiansRating(null)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setFullName()
                .setRating(new Rating(0D, 0D));
    }

    @AfterEach
    public void teardown() {
        polRepo = new InMemoryPoliticianAdapterRepo();
    }

    @Test
    public void queryingServiceAdapterByAllWithPageShouldCreateSession() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetup(30);

        Page pageZero = Page.asZero();
        service.allPoliticiansWithPage(pageZero, 20, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        assertThat(session)
                .isNotNull();
    }

    @Test
    public void shouldReturnSessionWithPagedObjectAttribute() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetup(30);

        Page pageZero = Page.asZero();

        service.allPoliticiansWithPage(pageZero, 20, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        assertThat((PagedResult<PoliticianDto>) session.getAttribute("paged-objects"))
                .isEqualTo(polRepo.findAllByPage(pageZero, 20));
    }

    void pagedSetup(int numberOfTimes) {
        pagedPoliticianSetup(numberOfTimes, politicianBuilder).stream().forEach(it -> {
            try {
                polRepo.save(it);
            } catch (PoliticianNotPersistableException e) {
                e.printStackTrace();
            }
        });
    }

}
