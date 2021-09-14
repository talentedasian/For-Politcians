package com.example.demo.adapter.web;

import com.example.demo.adapter.in.web.PoliticianServiceAdapter;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.web.dto.PoliticianDto;
import com.example.demo.domain.InMemoryPoliticianAdapterRepo;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import com.example.demo.domain.entities.Rating;
import com.example.demo.domain.politicians.Name;
import com.example.demo.domain.politicians.PoliticianNumber;
import com.example.demo.domain.politicians.Politicians;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.example.demo.baseClasses.MultiplePoliticianSetup.pagedPoliticianSetupPresidential;
import static com.example.demo.domain.politicianNumber.PoliticianNumberCalculatorFactory.politicianCalculator;
import static com.example.demo.domain.politicians.Politicians.Type.PRESIDENTIAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void shouldCreateSession() throws Exception{
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

        PagedObject<Politicians> pagedObject = (PagedObject<Politicians>) session.getAttribute("paged-objects");
        assertThat(pagedObject)
                .isEqualTo(polRepo.findAllByPage(pageZero, 20));
    }

    @Test
    public void shouldCreateAttributeAndSessionWithTotalPageIfNotExist() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetup(30);

        Page pageZero = Page.asZero();

        service.allPoliticiansWithPage(pageZero, 20, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        assertThat((long) session.getAttribute("total-page"))
                .isEqualTo(2);
    }

    @Test
    public void shouldReturnEmptyListIfWhenPageRequestDoesNotExistButStillSetTotalPageInSession() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetup(100);

        Page pageZero = Page.of(6);

        service.allPoliticiansWithPage(pageZero, 20, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        PagedObject<Politicians> pagedObject = (PagedObject<Politicians>) session.getAttribute("paged-objects");
        assertThat(pagedObject.valuesAsList())
                .isEqualTo(List.of());

        assertThat((long) session.getAttribute("total-page"))
                .isEqualTo(5);
    }

    @Test
    public void shouldThrowNextPageNotAvailableWhenNoSessionAttributeOfPagedObjectIsFound() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetup(100);

        Page pageZero = Page.of(0);

        assertThrows(NoSessionFoundException.class,
                () -> service.allPoliticiansWithPage(pageZero, 20, mockRequest, true));
    }

    @Test
    public void shouldGiveSamePagedObjectButWithLastPageAsContent() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetup(100);

        Page pageZero = Page.of(0);

        service.allPoliticiansWithPage(pageZero, 20, mockRequest);
        List<PoliticianDto> politicianDtos = service.allPoliticiansWithPage(pageZero, 20, mockRequest, true);

        HttpSession session = mockRequest.getSession(false);

        List<PoliticianDto> expectedContent = pagedPoliticianSetupPresidential(100, politicianBuilder).stream().skip(80)
                .map(it -> new PoliticiansDtoMapper().mapToDTO(it)).toList();

        PagedObject<Politicians> pagedObject = (PagedObject<Politicians>) session.getAttribute("paged-objects");

        assertThat(politicianDtos)
                .isEqualTo(expectedContent);

        assertThat(pagedObject)
                .isEqualTo(polRepo.findAllByPage(pageZero, 20));

        assertThat((long) session.getAttribute("total-page"))
                .isEqualTo(5);
    }

    void pagedSetup(int numberOfTimes) {
        pagedPoliticianSetupPresidential(numberOfTimes, politicianBuilder).stream().forEach(it -> {
            try {
                polRepo.save(it);
            } catch (PoliticianNotPersistableException e) {
                e.printStackTrace();
            }
        });
    }

}
