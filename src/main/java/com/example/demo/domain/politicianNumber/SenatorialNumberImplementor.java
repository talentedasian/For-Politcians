package com.example.demo.domain.politicianNumber;

import com.example.demo.domain.politicians.Name;

public final class SenatorialNumberImplementor extends PoliticianNumberImplementor{

    SenatorialNumberImplementor(Name name, String politicianNumber) {
        super(name, politicianNumber);
    }

    public static SenatorialNumberImplementor with(Name name) {
        return new SenatorialNumberImplementor(name, null);
    }

    @Override
    SenatorialNumberImplementor calculatePoliticianNumber() {
        String initialFirstName = calculateFirstName(pattern);
        String initialLastName = calculateLastName(initialFirstName);
        String initialType = calculateType(initialLastName);
        String finalPoliticianNumber = initialType.replaceAll("[0-9]", String.valueOf(getPolitician().hashCode()).substring(0,5));

        return new SenatorialNumberImplementor(getPolitician().recordName(), finalPoliticianNumber.substring(0, pattern.length()).toUpperCase());
    }

    private String calculateFirstName(String pattern) {
        return pattern.replace(FIRSTNAME_INITIAL, firstName.charAt(0));
    }

    private String calculateLastName(String pattern) {
        char toReplace = (lastName == null | (lastName.isBlank() || lastName.isEmpty())) ? 'S' : lastName.charAt(0);
        return pattern.replace(LASTNAME_INITIAL, toReplace);
    }

    private String calculateType(String pattern) {
        return pattern.replace(TYPE_INITIAL, 'S');
    }

}
