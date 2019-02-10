package com.epam.javatraining.dao;

import com.epam.javatraining.dogapp.model.Dog;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.Test;

import static com.epam.javatraining.model.DogValidationTest.generateDog;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

@ActiveProfiles("h2-prep")
public class JdbcPreparedStatementDogDaoTest extends JdbcDogDaoTest {
    @Test
    public void sqlInjection_results_into_truncatingTable() {
        Dog createdDog = dogDao.create(generateDog());
        createdDog.setName("'; TRUNCATE TABLE dog; --");
        dogDao.update(createdDog);
        assertThat(dogDao.getAll().size(), greaterThanOrEqualTo(5));
    }
}
