package com.example.demo.domain.politicians;

public record Name(String firstName, String lastName) {

    public Name(String firstName, String lastName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public static Name of(String firstName, String lastName) {
        return new Name(firstName, lastName);
    }

    public String fullName() {
        if (lastName == null || lastName.isBlank() || lastName.isEmpty()) {
            return firstName;
        }
        return firstName + " " + lastName;
    }

}
