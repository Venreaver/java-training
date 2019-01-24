package com.epam.javatraining.model;

import com.epam.javatraining.spring.model.Dog;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class DogValidationTest {
    private static Validator validator;
    private Dog dog;

    @BeforeClass
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeMethod
    private void updateDog() {
        dog = new Dog("1", "First", LocalDate.of(2016, 1, 10), 30, 6);
    }

    @Test
    public void testDogNameIsTooShort() {
        String shortName = RandomStringUtils.randomAlphabetic(0);
        dog.setName(shortName);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("size must be between 1 and 100"));
    }

    @Test
    public void testDogNameIsTooLong() {
        String longName = RandomStringUtils.randomAlphabetic(101);
        dog.setName(longName);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("size must be between 1 and 100"));
    }

    @Test
    public void testDogNameIsNull() {
        dog.setName(null);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must not be null"));
    }

    @Test
    public void testDogBirthDateIsToday() {
        dog.setDateOfBirth(LocalDate.now());
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must be a past date"));
    }

    @Test
    public void testDogBirthDateIsInTheFuture() {
        dog.setDateOfBirth(LocalDate.of(4019, 1, 10));
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must be a past date"));
    }

    @Test
    public void testDogHeightIsNegative() {
        dog.setHeight(-50);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must be greater than 0"));
    }

    @Test
    public void testDogHeightIsNull() {
        dog.setHeight(null);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must not be null"));
    }

    @Test
    public void testDogWeightIsNegative() {
        dog.setHeight(-50);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must be greater than 0"));
    }

    @Test
    public void testDogWeightIsNull() {
        dog.setHeight(null);
        Set<ConstraintViolation<Dog>> constraintViolations = validator.validate(dog);
        assertThat(constraintViolations, hasSize(1));
        assertThat(constraintViolations.iterator().next().getMessage(), equalTo("must not be null"));
    }
}
