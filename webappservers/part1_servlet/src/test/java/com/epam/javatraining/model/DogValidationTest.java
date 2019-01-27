package com.epam.javatraining.model;

import com.epam.javatraining.spring.model.Dog;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class DogValidationTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static Dog generateDog() {
        return new Dog(UUID.randomUUID().toString(), "MyDog", LocalDate.of(2018, 2, 15), 45, 20);
    }

    @Test
    public void givenIsDogWithEmptyNameWhenValidateThenSizeConstraintViolation() {
        Dog dog = generateDog();
        String shortName = RandomStringUtils.randomAlphabetic(0);
        dog.setName(shortName);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("size must be between 1 and 100"));
    }

    @Test
    public void givenIsDogWithToLongNameWhenValidateThenSizeConstraintViolation() {
        Dog dog = generateDog();
        String longName = RandomStringUtils.randomAlphabetic(101);
        dog.setName(longName);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("size must be between 1 and 100"));
    }

    @Test
    public void givenIsDogWithNullNameWhenValidateThenNotNullConstraintViolation() {
        Dog dog = generateDog();
        dog.setName(null);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must not be null"));
    }

    @Test
    public void givenIsDogWithTodayBirthDateWhenValidateThenPastDateConstraintViolation() {
        Dog dog = generateDog();
        dog.setDateOfBirth(LocalDate.now());
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must be a past date"));
    }

    @Test
    public void givenIsDogWithFutureBirthDateWhenValidateThenPastDateConstraintViolation() {
        Dog dog = generateDog();
        dog.setDateOfBirth(LocalDate.of(4019, 1, 10));
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must be a past date"));
    }

    @Test
    public void givenIsDogWithNegativeHeightWhenValidateThenPositiveNumberConstraintViolation() {
        Dog dog = generateDog();
        dog.setHeight(-50);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must be greater than 0"));
    }

    @Test
    public void givenIsDogNullHeightWhenValidateThenNotNullConstraintViolation() {
        Dog dog = generateDog();
        dog.setHeight(null);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must not be null"));
    }

    @Test
    public void givenIsDogWithNegativeWeightWhenValidateThenPositiveNumberConstraintViolation() {
        Dog dog = generateDog();
        dog.setHeight(-50);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must be greater than 0"));
    }

    @Test
    public void givenIsDogNullWeightWhenValidateThenNotNullConstraintViolation() {
        Dog dog = generateDog();
        dog.setHeight(null);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must not be null"));
    }
}
