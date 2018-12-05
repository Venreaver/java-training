package com.epam.javatraining;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class SumCalculatorTest {
    public void testSum() {
        SumCalculator sumCalculator = new SumCalculator();
        int sum = sumCalculator.sum(2, 8);
        assertEquals(sum, 10);
        boolean flag = sum == 10;
        assertTrue(flag);
    }
}
