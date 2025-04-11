package com.polarbookshop.catalogservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

public class BookValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var book = Book.of("1234567890", "Title", "Author", 9.90, "Polarsophia");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnIsDefinedButIncorrectThenValidationFails() {
        var book = Book.of("a23456790", "Title", "Author", 9.90, "Polarsophia");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The ISBN format must be valid.");
    }
}
