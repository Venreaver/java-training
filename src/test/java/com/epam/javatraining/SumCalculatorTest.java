package com.epam.javatraining;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class SumCalculatorTest {
    public void testSum() {
        SumCalculator sumCalculator = new SumCalculator();
        assertEquals(sumCalculator.sum(2, 8), 10);
    }
}
