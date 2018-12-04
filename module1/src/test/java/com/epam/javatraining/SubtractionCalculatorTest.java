package com.epam.javatraining;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class SubtractionCalculatorTest {
    public void testSum() {
        SubtractionCalculator subtractionCalculator = new SubtractionCalculator();
        int diff = subtractionCalculator.subtract(8, 2);
        assertEquals(diff, 6);
        boolean flag = diff == 6;
        assertTrue(flag);
    }
}
