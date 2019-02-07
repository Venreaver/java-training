package com.epam.javatraining.dao;

import com.epam.javatraining.dogapp.model.Dog;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.Test;

import static com.epam.javatraining.model.DogValidationTest.generateDog;
import static org.testng.Assert.assertThrows;

@ActiveProfiles("jdbc")
public class JdbcDogDaoTest extends DogDaoTest {
    @Test
    public void createDogWithInvalidName_results_into_RuntimeException() {
        Dog generatedDog = generateDog();
        generatedDog.setName(RandomStringUtils.randomAlphabetic(101));
        assertThrows(RuntimeException.class, () -> dogDao.create(generatedDog));
    }
}
