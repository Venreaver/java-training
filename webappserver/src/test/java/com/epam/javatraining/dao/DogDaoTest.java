package com.epam.javatraining.dao;

import com.epam.javatraining.dogapp.dao.DogDao;
import com.epam.javatraining.dogapp.exception.DogNotFoundException;
import com.epam.javatraining.dogapp.model.Dog;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Collection;

import static com.epam.javatraining.model.DogValidationTest.generateDog;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertThrows;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration("classpath:web-context.xml")
public class DogDaoTest extends AbstractTestNGSpringContextTests {
    @Autowired
    DogDao dogDao;

    @Test
    public void getList_results_intro_dogCollection() {
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
    public void getNotExistingDog_results_into_DogNotFoundException() {
        assertThrows(DogNotFoundException.class, () -> dogDao.get(generateDog().getId()));
    }

    @Test
    public void updateNotExistingDog_results_into_DogNotFoundException() {
        assertThrows(DogNotFoundException.class, () -> dogDao.update(generateDog()));
    }

    @Test
    public void deleteNotExistingDog_results_into_DogNotFoundException() {
        assertThrows(DogNotFoundException.class, () -> dogDao.delete(generateDog().getId()));
    }

    private Dog createDog(Dog dog) {
        return dogDao.create(dog);
    }
}
