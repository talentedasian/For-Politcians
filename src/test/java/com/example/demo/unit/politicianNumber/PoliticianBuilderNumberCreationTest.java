package com.example.demo.unit.politicianNumber;

import com.example.demo.domain.politicianNumber.PoliticianNumberCalculator;
import com.example.demo.domain.politicianNumber.PoliticianNumberCalculatorFactory;
import com.example.demo.domain.politicians.Name;
import com.example.demo.domain.politicians.PoliticianTypes;
import com.example.demo.domain.politicians.Politicians;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PoliticianBuilderNumberCreationTest {

    final String FIRST_NAME = "firstname";
    final String LAST_NAME = "lastname";

    PoliticianNumberCalculator polNumberCalculator = PoliticianNumberCalculatorFactory.politicianCalculator(Politicians.Type.PRESIDENTIAL);
    final String POLITICIAN_NUMBER  = polNumberCalculator.calculatePoliticianNumber(new Name(FIRST_NAME, LAST_NAME)).politicianNumber();

    Politicians politicianBuilder = new Politicians.PoliticiansBuilder(POLITICIAN_NUMBER)
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setFullName()
            .build();

    @Test  // INFO : Using a Presidential Politician ( fullName was too long already)
    public void politicianNumberRetrievalShouldReturnCorrectPoliticianNumberWhenInstanceIsCreated() {
        var presidential = new PoliticianTypes.PresidentialPolitician.PresidentialBuilder(politicianBuilder)
                .build();

        Politicians.Type POLITICIAN_TYPE = presidential.getType();

        PoliticianNumberCalculator polNumberCalculator = PoliticianNumberCalculatorFactory.politicianCalculator(Politicians.Type.PRESIDENTIAL);
        final String EXPECTED_POLITICIAN_NUMBER = polNumberCalculator.calculatePoliticianNumber(presidential.recordName()).politicianNumber();

        assertThat(EXPECTED_POLITICIAN_NUMBER)
                .isEqualTo(presidential.retrievePoliticianNumber());
    }

    @Test  // INFO : Using a Senatorial Politician ( fullName was too long already)
    public void politicianNumberRetrievalShouldReturnCorrectPoliticianNumberWhenInstanceIsCreated2() {
        var presidential = new PoliticianTypes.SenatorialPolitician.SenatorialBuilder(politicianBuilder)
                .setTotalMonthsOfService(12)
                .build();

        Politicians.Type POLITICIAN_TYPE = presidential.getType();

        PoliticianNumberCalculator polNumberCalculator = PoliticianNumberCalculatorFactory.politicianCalculator(Politicians.Type.SENATORIAL);
        final String EXPECTED_POLITICIAN_NUMBER = polNumberCalculator.calculatePoliticianNumber(presidential.recordName()).politicianNumber();

        assertThat(EXPECTED_POLITICIAN_NUMBER)
                .isEqualTo(presidential.retrievePoliticianNumber());
    }

}
