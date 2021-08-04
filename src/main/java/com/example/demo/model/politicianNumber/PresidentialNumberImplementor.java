package com.example.demo.model.politicianNumber;

import com.example.demo.model.entities.politicians.Politicians;

class PresidentialNumberImplementor extends PoliticianNumberImplementor {

    private PresidentialNumberImplementor(Politicians politician) {
        super(politician);
    }

    static PresidentialNumberImplementor with(String politicianNumber) {
        final Politicians politician = new Politicians.PoliticiansBuilder(politicianNumber).build();
        return new PresidentialNumberImplementor(politician);
    }


    @Override
    public AbstractPoliticianNumber calculateEntityNumber() {
        String initialFirstName = calculateFirstName(pattern);
        String initialLastName = calculateLastName(initialFirstName);
        String initialType = calculateType(initialLastName);
        String finalPoliticianNumber = initialType.replaceAll("[0-9]", String.valueOf(getPolitician().hashCode()).substring(0,5));

        return with(finalPoliticianNumber);
    }

    private String calculateFirstName(String pattern) {
        return pattern.replace(FIRSTNAME_INITIAL, firstName.charAt(0));
    }

    private String calculateLastName(String pattern) {
        char toReplace = (lastName == null | (lastName.isBlank() || lastName.isEmpty())) ? 'P' : getLastName().charAt(0);
        return pattern.replace(LASTNAME_INITIAL, toReplace);
    }

    private String calculateType(String pattern) {
        return pattern.replace(TYPE_INITIAL, 'P');
    }

}
