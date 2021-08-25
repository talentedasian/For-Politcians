package com.example.demo.adapter.out.jpa;

import com.example.demo.domain.politicians.PoliticianTypes.PresidentialPolitician.PresidentialBuilder;
import com.example.demo.domain.politicians.Politicians;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@DiscriminatorValue("Presidential")
public class PresidentialJpaEntity extends PoliticiansJpaEntity{

    @Column(nullable = true, name = "law_signed")
    private String mostSignificantLawSigned;

    protected PresidentialJpaEntity(PoliticiansJpaEntity politician, String mostSignificantLawSigned) {
        super(politician.getId(), politician.getFirstName(), politician.getLastName(),
                politician.toPoliticians().fullName(), politician.getRatingJpaEntity(), politician.getTotalCountRating(), politician.getPoliticiansRating());
        this.mostSignificantLawSigned = mostSignificantLawSigned;
    }

    PresidentialJpaEntity() {}

    public String getMostSignificantLawSigned() {
        return mostSignificantLawSigned;
    }

    public void setMostSignificantLawSigned(String mostSignificantLawSigned) {
        this.mostSignificantLawSigned = mostSignificantLawSigned;
    }

    @Override
    public String toString() {
        return super.toString() + ", {mostSignificantLawSigned=" + mostSignificantLawSigned + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PresidentialJpaEntity that = (PresidentialJpaEntity) o;

        if (!Objects.equals(mostSignificantLawSigned, that.mostSignificantLawSigned)) return false;
        if (!getId().equals(that.getId())) return false;
        return super.equals(that);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mostSignificantLawSigned != null ? mostSignificantLawSigned.hashCode() : 0;
        return result;
    }

    @Override
    public Politicians toPoliticians() {
        var politician = super.toPoliticians();
        politician.setType(Politicians.Type.PRESIDENTIAL);

        return new PresidentialBuilder(politician)
                .setMostSignificantLawPassed(mostSignificantLawSigned)
                .build();
    }
}
