package com.epam.javatraining.dao;

import com.epam.javatraining.dogapp.dao.DogDao;
import com.epam.javatraining.dogapp.exception.DogNotFoundException;
import com.epam.javatraining.dogapp.model.Dog;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.number.OrderingComparison;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Collection;

import static com.epam.javatraining.model.DogValidationTest.generateDog;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertThrows;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ActiveProfiles({"postgres"})
@ContextConfiguration("classpath:web-context.xml")
public class DogDaoTest extends AbstractTestNGSpringContextTests {
    @Autowired
    DogDao dogDao;

    @Test
    public void getList_results_into_dogCollection() {
        Collection<Dog> dogs = dogDao.getAll();
        assertThat(dogs.size(), greaterThanOrEqualTo(5));
    }

    @Test
    public void getDog_results_into_appropriateDog() {
        Dog createdDog = createDog(generateDog());
        Dog dog = dogDao.get(createdDog.getId());
        assertReflectionEquals(createdDog, dog);
    }

    @Test
    public void postDog_results_into_appropriateDogCreated() {
        Dog generatedDog = generateDog();
        generatedDog.setName(RandomStringUtils.randomAlphabetic(100));
        Dog dog = createDog(generatedDog);
        generatedDog.setId(dog.getId());
        assertReflectionEquals(generatedDog, dog);
    }

    @Test
    public void updateDog_results_into_appropriateDogUpdated() {
        Dog createdDog = createDog(generateDog());
        Dog generatedDog = generateDog();
        generatedDog.setId(createdDog.getId());
        Dog dog = dogDao.update(generatedDog);
        assertReflectionEquals(generatedDog, dog);
    }

    @Test
    public void deleteDog_results_without_exceptions() {
        Dog createdDog = createDog(generateDog());
        dogDao.delete(createdDog.getId());
    }

    @Test
    public void getNotExistingDog_results_into_Null() {
        assertNull(dogDao.get(generateDog().getId()));
    }

    @Test
    public void updateNotExistingDog_results_into_Null() {
        assertNull(dogDao.update(generateDog()));
    }

    @Test
    public void deleteNotExistingDog_results_into_Null() {
        assertThat(dogDao.delete(generateDog().getId()), is(0));
    }

    @Test
    public void createDogWithMinConstraints_results_into_appropriateCreation() {
        Dog generatedDog = generateDog();
        generatedDog.setName("q");
        generatedDog.setWeight(Integer.MIN_VALUE);
        generatedDog.setHeight(Integer.MIN_VALUE);
        Dog dog = dogDao.create(generatedDog);
        generatedDog.setId(dog.getId());
        assertReflectionEquals(generatedDog, dog);
    }

    @Test
    public void createDogWithMaxConstraints_results_into_appropriateCreation() {
        Dog generatedDog = generateDog();
        generatedDog.setName(RandomStringUtils.randomAlphabetic(100));
        generatedDog.setWeight(Integer.MAX_VALUE);
        generatedDog.setHeight(Integer.MAX_VALUE);
        Dog dog = dogDao.create(generatedDog);
        generatedDog.setId(dog.getId());
        assertReflectionEquals(generatedDog, dog);
    }

    @Test
    public void createDogWithNullBirthDate_results_into_appropriateCreation() {
        Dog generatedDog = generateDog();
        generatedDog.setDateOfBirth(null);
        Dog dog = dogDao.create(generatedDog);
        generatedDog.setId(dog.getId());
        assertReflectionEquals(generatedDog, dog);
    }

    private Dog createDog(Dog dog) {
        return dogDao.create(dog);
    }

    @Test
    public void sqlInjection_has_no_effects() {
        Dog createdDog = dogDao.create(generateDog());
        createdDog.setName("'; TRUNCATE TABLE dog; --");
        dogDao.update(createdDog);
        MatcherAssert.assertThat(dogDao.getAll().size(), OrderingComparison.greaterThanOrEqualTo(5));
    }
}
