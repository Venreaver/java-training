package io.qala.javatraining.dao;

import io.qala.javatraining.HibernateDaoTest;
import io.qala.javatraining.domain.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import static io.qala.javatraining.TestUtils.assertReflectionEquals;

@Test
@HibernateDaoTest
public class HibernateDogDaoTest extends AbstractTransactionalTestNGSpringContextTests {
    /**
     * Find the description of the puzzle in the root README.md
     */
    public void getsTheSameDogAsWasSaved() {
        Dog original = Dog.random();
        dao.createDog(original);
        dao.flushAndClear();
        Dog fromDb = dao.getDog(original.getId());
        assertReflectionEquals(original, fromDb);
    }

    @Autowired
    private HibernateDogDao dao;
}