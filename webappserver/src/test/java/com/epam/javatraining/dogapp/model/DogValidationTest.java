package com.epam.javatraining.dogapp.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
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

@ContextConfiguration("classpath:test-context.xml")
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
    public void emptyName_results_into_notValidNameSize() {
        Dog dog = generateDog();
        String shortName = RandomStringUtils.randomAlphabetic(0);
        dog.setName(shortName);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("size must be between 1 and 100"));
    }

    @Test
    public void tooLongName_results_into_notValidNameSize() {
        Dog dog = generateDog();
        String longName = RandomStringUtils.randomAlphabetic(101);
        dog.setName(longName);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("size must be between 1 and 100"));
    }

    @Test
    public void nullName_results_into_notValidName() {
        Dog dog = generateDog();
        dog.setName(null);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must not be null"));
    }

    @Test
    public void todayBirthDate_results_into_notValidBirthDate() {
        Dog dog = generateDog();
        dog.setDateOfBirth(LocalDate.now());
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must be a past date"));
    }

    @Test
    public void futureBirthDate_results_into_notValidBirthDate() {
        Dog dog = generateDog();
        dog.setDateOfBirth(LocalDate.of(4019, 1, 10));
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must be a past date"));
    }

    @Test
    public void negativeHeight_results_into_notValidPositiveHeight() {
        Dog dog = generateDog();
        dog.setHeight(-50);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must be greater than 0"));
    }

    @Test
    public void nullHeight_results_into_notValidHeight() {
        Dog dog = generateDog();
        dog.setHeight(null);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must not be null"));
    }

    @Test
    public void negativeWeight_results_into_notValidWeight() {
        Dog dog = generateDog();
        dog.setHeight(-50);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must be greater than 0"));
    }

    @Test
    public void nullWeight_results_into_notValidWeight() {
        Dog dog = generateDog();
        dog.setHeight(null);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must not be null"));
    }
}
