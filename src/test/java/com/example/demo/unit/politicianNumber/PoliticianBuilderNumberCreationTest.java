package com.example.demo.unit.politicianNumber;

import com.example.demo.domain.politicianNumber.PoliticianNumberImplementor;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.Politicians;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PoliticianBuilderNumberCreationTest {

    final String FIRST_NAME = "firstname";
    final String LAST_NAME = "lastname";

    Politicians politicianBuilder = new Politicians.PoliticiansBuilder()
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setFullName()
            .build();

    @Test  // INFO : Using a Presidential Politician ( name was too long already)
    public void politicianNumberRetrievalShouldReturnCorrectPoliticianNumberWhenInstanceIsCreated() {
        var presidential = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder)
                .build();

        Politicians.Type POLITICIAN_TYPE = presidential.getType();

        PoliticianNumberImplementor polNumberCalculator = PoliticianNumberImplementor.with(presidential.recordName(), POLITICIAN_TYPE);
        final String EXPECTED_POLITICIAN_NUMBER = polNumberCalculator.calculateEntityNumber().getPoliticianNumber();

        assertThat(EXPECTED_POLITICIAN_NUMBER)
                .isEqualTo(presidential.retrievePoliticianNumber());
    }

    @Test  // INFO : Using a Senatorial Politician ( name was too long already)
    public void politicianNumberRetrievalShouldReturnCorrectPoliticianNumberWhenInstanceIsCreated2() {
        var presidential = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politicianBuilder)
                .setTotalMonthsOfService(12)
                .build();

        Politicians.Type POLITICIAN_TYPE = presidential.getType();

        PoliticianNumberImplementor polNumberCalculator = PoliticianNumberImplementor.with(presidential.recordName(), POLITICIAN_TYPE);
        final String EXPECTED_POLITICIAN_NUMBER = polNumberCalculator.calculateEntityNumber().getPoliticianNumber();

        assertThat(EXPECTED_POLITICIAN_NUMBER)
                .isEqualTo(presidential.retrievePoliticianNumber());
    }

}
