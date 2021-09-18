package com.example.demo.domain.politicianNumber;

import com.example.demo.domain.entities.Name;
import com.example.demo.domain.entities.PoliticianNumber;

import static com.example.demo.domain.entities.Politicians.Type.PRESIDENTIAL;

class PresidentialNumberImplementor extends AbstractPoliticianNumberCalculator {

    public static PoliticianNumberCalculator create() {
        return new PresidentialNumberImplementor();
    }

    @Override
    public PoliticianNumber calculatePoliticianNumber(Name name) {
        String initialFirstName = calculateFirstName(pattern, name.firstName());
        String initialLastName = calculateLastName(initialFirstName, name.lastName());
        String initialType = calculateType(initialLastName);
        String finalPoliticianNumber = initialType.replaceAll("[\\d]+", numberOfPoliticianUsingNameAndType(name).substring(0, 4)).toUpperCase();

        return new PoliticianNumber(finalPoliticianNumber);
    }

    private String calculateFirstName(String pattern, String firstName) {
        return pattern.replace(FIRSTNAME_INITIAL, firstName.charAt(0));
    }

    private String calculateLastName(String pattern, String lastName) {
        char toReplace = (lastName == null | (lastName.isBlank() || lastName.isEmpty())) ? 'P' : lastName.charAt(0);
        return pattern.replace(LASTNAME_INITIAL, toReplace);
    }

    private String calculateType(String pattern) {
        return pattern.replace(TYPE_INITIAL, 'P');
    }

    private String numberOfPoliticianUsingNameAndType(Name name) {
        int result = name.firstName().hashCode();
        result = 31 * result + name.lastName() == null ? "lastname".hashCode() : name.lastName().hashCode();
        result = 31 * result + name.fullName().hashCode();
        result = 31 * result + PRESIDENTIAL.toString().hashCode();
        return String.valueOf(Math.abs(result));
    }

}
