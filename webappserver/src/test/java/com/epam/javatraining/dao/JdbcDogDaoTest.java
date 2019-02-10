package com.epam.javatraining.dao;

import com.epam.javatraining.dogapp.model.Dog;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static com.epam.javatraining.model.DogValidationTest.generateDog;
import static org.testng.Assert.assertThrows;

public abstract class JdbcDogDaoTest extends DogDaoTest {
    @Test
    public void createInvalidDog_results_into_RuntimeException() {
        Dog generatedDog = generateDog();
        generatedDog.setName(RandomStringUtils.randomAlphabetic(101));
        assertThrows(RuntimeException.class, () -> dogDao.create(generatedDog));
        generatedDog.setName(null);
        assertThrows(RuntimeException.class, () -> dogDao.create(generatedDog));
        generatedDog.setName("q");
        generatedDog.setHeight(null);
        assertThrows(RuntimeException.class, () -> dogDao.create(generatedDog));
        generatedDog.setHeight(32);
        generatedDog.setWeight(null);
        assertThrows(RuntimeException.class, () -> dogDao.create(generatedDog));
    }

    @Test
    public void createDogWithNullName_results_into_RuntimeException() {
        Dog generatedDog = generateDog();
        generatedDog.setName(null);
        assertThrows(RuntimeException.class, () -> dogDao.create(generatedDog));
    }
}
