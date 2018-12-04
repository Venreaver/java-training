package com.epam.javatraining;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class HelloStringTest {
    public void testSum() {
        HelloString helloString = new HelloString();
        String helloStr = helloString.getHello();
        assertEquals(helloStr, "Hello");
        boolean flag = "Hello".equals(helloStr);
        assertTrue(flag);
    }
}
