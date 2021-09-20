package com.example.demo.adapter.out.jpa;

import com.example.demo.domain.entities.PoliticianTypes.SenatorialPolitician.SenatorialBuilder;
import com.example.demo.domain.entities.Politicians;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@DiscriminatorValue("Senatorial")
public class SenatorialJpaEntity extends PoliticiansJpaEntity {

    /*
     * This column must not be null when constructing Senatorial politicians.
     * This is only nullable in the database for JPA inheritance.
     */
    @Column(nullable = true, name = "months_of_service")
    private int totalMonthsOfServiceAsSenator;

    @Column(nullable = true, name = "law_made")
    private String mostSignificantLawMade;

    public int getTotalMonthsOfServiceAsSenator() {
        return totalMonthsOfServiceAsSenator;
    }

    public String getMostSignificantLawMade() {
        return mostSignificantLawMade;
    }

    // used for jpa
    SenatorialJpaEntity() {}

    protected SenatorialJpaEntity(PoliticiansJpaEntity politician, String mostSignificantLawMade, Integer totalMonthsOfServiceAsSenator) {
        super(politician.getId(), politician.getFirstName(), politician.getLastName(), politician.getFullName(),
                politician.getRatingJpaEntity(), politician.getTotalCountRating(), politician.getPoliticiansRating());
        this.totalMonthsOfServiceAsSenator = totalMonthsOfServiceAsSenator;
        this.mostSignificantLawMade = mostSignificantLawMade;
    }

    @Override
    public String toString() {
        return super.toString() + ", {mostSignificantLawMade=" + mostSignificantLawMade + ", totalMonthsOfService=" + totalMonthsOfServiceAsSenator + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SenatorialJpaEntity that = (SenatorialJpaEntity) o;

        if (totalMonthsOfServiceAsSenator != that.totalMonthsOfServiceAsSenator) return false;
        return Objects.equals(mostSignificantLawMade, that.mostSignificantLawMade);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + totalMonthsOfServiceAsSenator;
        result = 31 * result + (mostSignificantLawMade != null ? mostSignificantLawMade.hashCode() : 0);
        return result;
    }

    @Override
    public Politicians toPoliticians() {
        var politician = super.toPoliticians();

        return new SenatorialBuilder(politician)
                .setTotalMonthsOfService(totalMonthsOfServiceAsSenator)
                .setMostSignificantLawMade(mostSignificantLawMade)
                .build();
    }
}
