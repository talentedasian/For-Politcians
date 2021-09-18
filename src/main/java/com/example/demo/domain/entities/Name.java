package com.example.demo.domain.entities;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public record Name(String firstName, String lastName) {

    public Name(String firstName, String lastName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public static Name of(String firstName, String lastName) {
        return new Name(firstName, lastName);
    }

    public String fullName() {
        Assert.state(firstName != null || StringUtils.hasText(firstName), "first name must not be null or empty");
        if (lastName == null || lastName.isBlank() || lastName.isEmpty()) {
            return firstName;
        }
        return firstName + " " + lastName;
    }

}
