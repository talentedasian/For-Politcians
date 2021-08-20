package com.example.demo.domain.politicianNumber;

import com.example.demo.domain.politicians.Name;

class PresidentialNumberImplementor extends PoliticianNumberImplementor {

    PresidentialNumberImplementor(Name name, String politicianNumber) {
        super(name, politicianNumber);
    }

    public static PresidentialNumberImplementor with(Name name) {
        return new PresidentialNumberImplementor(name, null);
    }

    @Override
    PresidentialNumberImplementor calculatePoliticianNumber() {
        String initialFirstName = calculateFirstName(pattern);
        String initialLastName = calculateLastName(initialFirstName);
        String initialType = calculateType(initialLastName);
        String finalPoliticianNumber = initialType.replaceAll("[0-9]", String.valueOf(Math.abs(getPolitician().hashCode())).substring(0,5));

        return new PresidentialNumberImplementor(new Name(firstName, lastName), finalPoliticianNumber.substring(0, pattern.length()).toUpperCase());
    }

    private String calculateFirstName(String pattern) {
        return pattern.replace(FIRSTNAME_INITIAL, firstName.charAt(0));
    }

    private String calculateLastName(String pattern) {
        char toReplace = (lastName == null | (lastName.isBlank() || lastName.isEmpty())) ? 'P' : lastName().charAt(0);
        return pattern.replace(LASTNAME_INITIAL, toReplace);
    }

    private String calculateType(String pattern) {
        return pattern.replace(TYPE_INITIAL, 'P');
    }

}
