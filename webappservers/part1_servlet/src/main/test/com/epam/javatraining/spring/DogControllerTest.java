package com.epam.javatraining.spring;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.springframework.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.epam.javatraining.spring.controller.DogController.DOG;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class DogControllerTest {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    //TODO
    @Test
    public void testGetAll() {
        given().accept(ContentType.JSON).when().get(DOG).then().assertThat()
               .statusCode(HttpStatus.OK.value())
               .contentType(ContentType.JSON).body("size()", greaterThanOrEqualTo(4));
    }
}
