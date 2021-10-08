package com.example.demo.adapter.web;

import com.example.demo.adapter.in.web.PoliticianServiceAdapter;
import com.example.demo.adapter.web.dto.PoliticianDto;
import com.example.demo.domain.InMemoryPoliticianAdapterRepo;
import com.example.demo.domain.Page;
import com.example.demo.domain.PagedObject;
import com.example.demo.domain.entities.Name;
import com.example.demo.domain.entities.PoliticianNumber;
import com.example.demo.domain.entities.Politicians;
import com.example.demo.dtomapper.PoliticiansDtoMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.example.demo.baseClasses.MultiplePoliticianSetup.pagedPoliticianSetupPresidential;
import static com.example.demo.domain.entities.Politicians.Type.PRESIDENTIAL;
import static com.example.demo.domain.politicianNumber.PoliticianNumberCalculatorFactory.politicianCalculator;
import static org.assertj.core.api.Assertions.assertThat;

public class PoliticianPaginationServiceAdapterTest {

    final String FIRST_NAME = "Mirriam";
    final String LAST_NAME = "Defensor";

    InMemoryPoliticianAdapterRepo polRepo;

    Politicians.PoliticiansBuilder politicianBuilder;

    final PoliticianNumber POLITICIAN_NUMBER = politicianCalculator(PRESIDENTIAL).calculatePoliticianNumber(Name.of(FIRST_NAME, LAST_NAME));

    @BeforeEach
    public void setup() {
        politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
                .setPoliticiansRating(null)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME);

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

        pagedSetupForExistingSession(30);

        Page pageZero = Page.asZero();
        service.allPoliticiansWithPage(pageZero, 20, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        assertThat(session)
                .isNotNull();
        assertThat(session.getMaxInactiveInterval())
                .isEqualTo(300);
    }

                // INFO : NEXT PAGE MEANS JUST QUERYING FOR A PAGE INCREMENTED FROM THE LAST EXISTING SESSION AND STILL HAVING THE
    @Test       // SAME ITEMS TO FETCH
    public void queryingForNextPageShouldReturnPagedObjectFromInitialSessionCreation() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        int numberOfTimes = 30;
        pagedSetupForExistingSession(numberOfTimes);

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
        pagedSetupForExistingSession(numberOfTimes);

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
        pagedSetupForExistingSession(numberOfTimes);

        Page pageZero = Page.asZero();

        int itemsToFetchOnInitialRequest = 20;
        int differentItemsToFetchWithSession = 10;
        service.allPoliticiansWithPage(pageZero, itemsToFetchOnInitialRequest, mockRequest);
        service.allPoliticiansWithPage(pageZero, differentItemsToFetchWithSession, mockRequest);

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

        pagedSetupForExistingSession(100);

        Page nonExistentPage = Page.of(6);

        service.allPoliticiansWithPage(nonExistentPage, 20, mockRequest);

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
        pagedSetupForExistingSession(numberOfTimes);

        Page pageZero = Page.of(0);

        service.allPoliticiansWithPage(pageZero, 20, mockRequest);
        service.allPoliticiansWithPage(pageZero.nextPage(), 20, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        assertThat((long) session.getAttribute("total"))
                .isEqualTo(numberOfTimes);
        assertThat(polRepo.countQueries())
                .isEqualTo(1);
    }

    @Test
    public void totalAttributeInSessionShouldStayTheSameForAllSubsequentRequestsThatHaveExistingSessions() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        int numberOfTimes = 214214;
        pagedSetupForExistingSession(numberOfTimes);

        Page pageZero = Page.of(0);

        int itemsToFetch = 20;
        service.allPoliticiansWithPage(pageZero, itemsToFetch, mockRequest);
        service.allPoliticiansWithPage(pageZero.nextPage(), itemsToFetch, mockRequest);
        service.allPoliticiansWithPage(pageZero.nextPage(3), itemsToFetch, mockRequest);
        service.allPoliticiansWithPage(pageZero.nextPage(321), itemsToFetch, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        assertThat((long) session.getAttribute("total"))
                .isEqualTo(numberOfTimes);
    }

    @Test
    public void shouldGivePagedObjectButWithLastPageAsContent() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        int totalPoliticiansInDatabase = 100;
        pagedSetupForExistingSession(totalPoliticiansInDatabase);

        Page pageZero = Page.of(0);
        Page lastPage = Page.of(4);

        int itemsToFetch = 20;
        service.allPoliticiansWithPage(pageZero, itemsToFetch, mockRequest);
        List<PoliticianDto> politicianDtos = service.allPoliticiansWithPage(lastPage, itemsToFetch, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        List<PoliticianDto> expectedContent = pagedPoliticianSetupPresidential(totalPoliticiansInDatabase, politicianBuilder)
                .stream().skip(totalPoliticiansInDatabase - itemsToFetch)
                .map(it -> new PoliticiansDtoMapper().mapToDTO(it)).toList();

        PagedObject<Politicians> pagedObject = (PagedObject<Politicians>) session.getAttribute("paged-objects");

        assertThat(politicianDtos)
                .isEqualTo(expectedContent);
        assertThat(pagedObject)
                .isEqualTo(polRepo.findAllByPage(lastPage, itemsToFetch, totalPoliticiansInDatabase));
    }

    @Test
    public void shouldOverwriteAttributesForItemsToFetchWhenAnExistingSessionExistWithDifferentItemsToFetch() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        int totalPoliticiansInDatabase = 100;
        pagedSetupForExistingSession(totalPoliticiansInDatabase);

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

        pagedSetupForExistingSession(100);

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
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetupForExistingSession(100);

        Page lastPage = Page.of(4);

        int itemsToFetch = 20;
        service.allPoliticiansWithPage(Page.asZero(), itemsToFetch, mockRequest);
        service.allPoliticiansWithPage(lastPage, itemsToFetch, mockRequest);

        assertThat(polRepo.countQueries())
                .isEqualTo(1);
    }

    @Test
    public void queryingForADifferentPageAfterExistingSessionShouldReturnPagedObjectWithPageRequested() throws Exception{
        PoliticianServiceAdapter service = new PoliticianServiceAdapter(polRepo);

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        pagedSetupForExistingSession(100);

        int itemsToFetch = 20;
        service.allPoliticiansWithPage(Page.asZero(), itemsToFetch, mockRequest);
        Page differentPageRequested = Page.of(3);
        service.allPoliticiansWithPage(differentPageRequested, itemsToFetch, mockRequest);

        HttpSession session = mockRequest.getSession(false);

        PagedObject<Politicians> pagedObject = (PagedObject<Politicians>) session.getAttribute("paged-objects");

        int currentPage = pagedObject.currentPageNumber();
        assertThat(currentPage)
                .isEqualTo(differentPageRequested.pageNumber());
    }

    void pagedSetupForExistingSession(int numberOfTimes) {
        PaginationSetup.pagedSetupPresidential(numberOfTimes, politicianBuilder, polRepo);
    }

}
