package com.example.demo.adapter.web;

import com.example.demo.adapter.in.web.PoliticianServiceAdapter;
import com.example.demo.adapter.out.repository.PoliticiansRepository;
import com.example.demo.adapter.web.dto.PoliticianDto;
import com.example.demo.domain.AverageRating;
import com.example.demo.domain.InMemoryPoliticianAdapterRepo;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import com.example.demo.domain.entities.Name;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.domain.entities.Rating;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import com.example.demo.exceptions.PoliticianNotPersistableException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.example.demo.baseClasses.MultiplePoliticianSetup.pagedPoliticianSetupPresidential;
import static com.example.demo.domain.entities.Politicians.Type.PRESIDENTIAL;
import static com.example.demo.domain.politicianNumber.PoliticianNumberCalculatorFactory.politicianCalculator;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

public class PoliticianPaginationServiceAdapterTest {

    final String FIRST_NAME = "Mirriam";
    final String LAST_NAME = "Defensor";

    PoliticiansRepository polRepo;

    Politicians.PoliticiansBuilder politicianBuilder;

    final PoliticianNumber POLITICIAN_NUMBER = politicianCalculator(PRESIDENTIAL).calculatePoliticianNumber(Name.of(FIRST_NAME, LAST_NAME));

    @BeforeEach
    public void setup() {
        politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
                .setPoliticiansRating(null)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setFullName()
                .setRating(new Rating(0D, AverageRating.of(valueOf(0))));

        polRepo = new InMemoryPoliticianAdapterRepo();
    }

    @AfterEach
    public void teardown() {
        polRepo = new InMemoryPoliticianAdapterRepo();
    }

    @Test
    public void shouldCreateSessionWith300SecondsAsTimeout() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetup(30);

        Page pageZero = Page.asZero();
        service.allPoliticiansWithPage(pageZero, 20, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        assertThat(session)
                .isNotNull();
        assertThat(session.getMaxInactiveInterval())
                .isEqualTo(300);
    }

    @Test       // INFO : NEXT PAGE MEANS JUST QUERYING FOR A PAGE INCREMENTED FROM THE LAST EXISTING SESSION AND STILL HAVING THE
                // SAME ITEMS TO FETCH
    public void queryingForNextPageShouldReturnCorrectPagedObjectAndStillRetainSessionAttributesFromBefore() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        int numberOfTimes = 30;
        pagedSetup(numberOfTimes);

        Page pageZero = Page.asZero();

        int itemsToFetch = 20;
        service.allPoliticiansWithPage(pageZero, itemsToFetch, mockRequest);
        service.allPoliticiansWithPage(pageZero.nextPage(), itemsToFetch, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        PagedObject pagedObjectFromSession = (PagedObject) session.getAttribute("paged-objects");
        PagedObject expectedPagedObject = PagedObject.of(polRepo.findAllByPage(pageZero.nextPage(), itemsToFetch, numberOfTimes).valuesAsList(),
                                                         numberOfTimes, itemsToFetch, pageZero.nextPage());

        assertThat(pagedObjectFromSession)
                .isEqualTo(expectedPagedObject);
    }

    @Test
    public void shouldReturnSessionWithTotalPageAndItemsToFetchAndTotalAsAttributeOnFirstPaginationQuery() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        int numberOfTimes = 30;
        pagedSetup(numberOfTimes);

        Page pageZero = Page.asZero();

        int itemsToFetch = 20;
        service.allPoliticiansWithPage(pageZero, itemsToFetch, mockRequest);

        HttpSession session = mockRequest.getSession(false);
        assertThat((long)session.getAttribute("total-page"))
                .isEqualTo(2);

        assertThat((int)session.getAttribute("items-to-fetch"))
                .isEqualTo(itemsToFetch);

        assertThat((long)session.getAttribute("total"))
                .isEqualTo(numberOfTimes);
    }

    @Test
    public void shouldOverwriteItemsToFetchAndTotalPageWhenSessionExistsButItemsToFetchIsDifferentFromExistingSession() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        int numberOfTimes = 50;
        pagedSetup(numberOfTimes);

        Page pageZero = Page.asZero();

        service.allPoliticiansWithPage(pageZero, 20, mockRequest);
        service.allPoliticiansWithPage(pageZero, 10, mockRequest);

        HttpSession session = mockRequest.getSession(false);
        assertThat((long) session.getAttribute("total-page"))
                .isEqualTo(5);

        assertThat((int) session.getAttribute("items-to-fetch"))
                .isEqualTo(10);

        assertThat((long) session.getAttribute("total"))
                .isEqualTo(numberOfTimes);
    }

    @Test
    public void shouldReturnEmptyListWhenPageRequestedDoesNotExistButStillSetTotalPageInSession() throws Exception{
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
    public void shouldOnlyQueryOnceForCountAndSubsequentRequestsForPaginationShouldNotDoSoAgain() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        int numberOfTimes = 100;
        pagedSetup(numberOfTimes);

        Page pageZero = Page.of(0);

        service.allPoliticiansWithPage(pageZero, 20, mockRequest);
        service.allPoliticiansWithPage(pageZero.nextPage(), 20, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        assertThat((long) session.getAttribute("total"))
                .isEqualTo(numberOfTimes);
    }

    @Test
    public void totalAttributeInSessionShouldStayTheSameForAllSubsequentRequestsThatHaveExistingSessions() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        int numberOfTimes = 214214;
        pagedSetup(numberOfTimes);

        Page pageZero = Page.of(0);

        service.allPoliticiansWithPage(pageZero, 20, mockRequest);
        service.allPoliticiansWithPage(pageZero.nextPage(), 20, mockRequest);
        service.allPoliticiansWithPage(pageZero.nextPage(3), 20, mockRequest);
        service.allPoliticiansWithPage(pageZero.nextPage(321), 20, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        assertThat((long) session.getAttribute("total"))
                .isEqualTo(numberOfTimes);
    }

    @Test
    public void shouldGivePagedObjectButWithLastPageAsContent() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetup(100);

        Page pageZero = Page.of(0);
        Page lastPage = Page.of(4);

        service.allPoliticiansWithPage(pageZero, 20, mockRequest);
        List<PoliticianDto> politicianDtos = service.allPoliticiansWithPage(lastPage, 20, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        List<PoliticianDto> expectedContent = pagedPoliticianSetupPresidential(100, politicianBuilder).stream().skip(80)
                .map(it -> new PoliticiansDtoMapper().mapToDTO(it)).toList();

        PagedObject<Politicians> pagedObject = (PagedObject<Politicians>) session.getAttribute("paged-objects");

        assertThat(politicianDtos)
                .isEqualTo(expectedContent);

        assertThat(pagedObject)
                .isEqualTo(polRepo.findAllByPage(lastPage, 20, 100l));
    }

    @Test
    public void shouldOverwriteAttributesForItemsToFetchWhenAnExistingSessionExistWithDifferentItemsToFetch() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetupForExistingSession(100, polRepo);

        int itemsToFetch = 20;
        int differentItemsToFetch = 10;

        service.allPoliticiansWithPage(Page.asZero(), itemsToFetch, mockRequest);

        HttpSession session = mockRequest.getSession(false);
        assertThat((int)session.getAttribute("items-to-fetch"))
                .isEqualTo(itemsToFetch);

        service.allPoliticiansWithPage(Page.asZero(), differentItemsToFetch, mockRequest);

        assertThat((int)session.getAttribute("items-to-fetch"))
                .isEqualTo(differentItemsToFetch);
    }

    @Test
    public void countQueryShouldOnlyBeExecutedForTheFirstPaginationRequest() throws Exception{
        var inMemoryRepo = new InMemoryPoliticianAdapterRepo();
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(inMemoryRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetupForExistingSession(100, inMemoryRepo);

        Page lastPage = Page.of(4);

        int itemsToFetch = 20;
        service.allPoliticiansWithPage(Page.asZero(), itemsToFetch, mockRequest);
        service.allPoliticiansWithPage(lastPage, 10, mockRequest);
        service.allPoliticiansWithPage(Page.asZero().nextPage(), itemsToFetch, mockRequest);

        assertThat(inMemoryRepo.countQueries())
                .isEqualTo(1);
    }

    @Test
    public void shouldNotQueryAgainForCountWhenUserHasSessionAndRequestsForLastPageWithExactAmountOfItemsToFetchAsLastRequest() throws Exception{
        var inMemoryRepo = new InMemoryPoliticianAdapterRepo();
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(inMemoryRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetupForExistingSession(100, inMemoryRepo);

        Page lastPage = Page.of(4);

        service.allPoliticiansWithPage(Page.asZero(), 20, mockRequest);
        service.allPoliticiansWithPage(lastPage, 20, mockRequest);

        assertThat(inMemoryRepo.countQueries())
                .isEqualTo(1);
    }

    void pagedSetupForExistingSession(int numberOfTimes, PoliticiansRepository repo) {
        pagedPoliticianSetupPresidential(numberOfTimes, politicianBuilder).stream().forEach(it -> {
            try {
                repo.save(it);
            } catch (PoliticianNotPersistableException e) {
                e.printStackTrace();
            }
        });
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
