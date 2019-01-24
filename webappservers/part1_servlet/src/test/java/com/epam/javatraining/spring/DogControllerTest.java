package com.epam.javatraining.spring;

import com.epam.javatraining.spring.model.Dog;
import com.epam.javatraining.spring.model.ErrorResponse;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static com.epam.javatraining.spring.controller.DogController.DOG;
import static com.epam.javatraining.spring.controller.DogController.DOG_ID;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static com.jayway.restassured.http.ContentType.TEXT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

public class DogControllerTest {
    private Dog testDog = new Dog("1", "First", LocalDate.of(2016, 1, 10), 30, 6);

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testGetAll() {
        given().when().get(DOG)
               .then().assertThat().statusCode(OK.value()).contentType(JSON).body("size()", greaterThanOrEqualTo(5));
    }

    @Test
    public void testGet() {
        Dog dog = given().when().get(DOG_ID, testDog.getId())
                         .then().statusCode(OK.value()).contentType(JSON).extract().body().as(Dog.class);
        assertThat(dog, equalTo(testDog));
    }

    @Test
    public void testCreate() {
        Dog dog = given().body(testDog).accept(JSON).contentType(JSON)
                         .when().post(DOG)
                         .then().statusCode(OK.value()).extract().body().as(Dog.class);
        assertThat(dog.getDateOfBirth(), equalTo(testDog.getDateOfBirth()));
        assertThat(dog.getName(), equalTo(testDog.getName()));
        assertThat(dog.getHeight(), equalTo(testDog.getHeight()));
        assertThat(dog.getWeight(), equalTo(testDog.getWeight()));
        assertThat(dog.getId(), not(testDog.getId()));
    }

    @Test
    public void testUpdate() {
        Dog dog = given().body(testDog).accept(JSON).contentType(JSON)
                         .when().put(DOG_ID, "2")
                         .then().statusCode(OK.value()).extract().body().as(Dog.class);
        assertThat(dog.getDateOfBirth(), equalTo(testDog.getDateOfBirth()));
        assertThat(dog.getName(), equalTo(testDog.getName()));
        assertThat(dog.getHeight(), equalTo(testDog.getHeight()));
        assertThat(dog.getWeight(), equalTo(testDog.getWeight()));
        assertThat(dog.getId(), equalTo("2"));
    }

    @Test
    public void testDelete() {
        given().when().delete(DOG_ID, "3").then().statusCode(NO_CONTENT.value());
        given().when().get(DOG_ID, "3").then().assertThat().statusCode(NOT_FOUND.value());
    }

    @Test
    public void testGetNotExistingDog() {
        ErrorResponse response = given().when().get(DOG_ID, "888")
                                        .then().statusCode(NOT_FOUND.value()).contentType(JSON)
                                        .extract().body().as(ErrorResponse.class);
        assertThat(response.getInfo(), equalTo(String.format("Dog with id %s does not exist", "888")));
    }

    @Test
    public void testUpdateNotExistingDog() {
        ErrorResponse response = given().body(testDog).accept(JSON).contentType(JSON)
                                        .when().put(DOG_ID, "888")
                                        .then().statusCode(NOT_FOUND.value()).extract().body().as(ErrorResponse.class);
        assertThat(response.getInfo(), equalTo(String.format("Dog with id %s does not exist", "888")));
    }

    @Test
    public void testDeleteNotExistingDog() {
        ErrorResponse response = given().when().delete(DOG_ID, "888")
                                        .then().statusCode(NOT_FOUND.value())
                                        .extract().body().as(ErrorResponse.class);
        assertThat(response.getInfo(), equalTo(String.format("Dog with id %s does not exist", "888")));
    }

    @Test
    public void testWrongMediaTypes() {
        given().body(testDog).accept(JSON).contentType(TEXT).when().post(DOG).then().statusCode(UNSUPPORTED_MEDIA_TYPE.value());
        given().body(testDog).accept(TEXT).contentType(JSON).when().post(DOG).then().statusCode(NOT_ACCEPTABLE.value());
    }

    @Test
    public void testCreateInvalidDog() {
        Dog dog = new Dog(null, "", LocalDate.of(2016, 1, 10), 30, 6);
        given().body(dog).accept(JSON).contentType(JSON)
               .when().post(DOG)
               .then().assertThat().statusCode(BAD_REQUEST.value()).extract().body().as(ErrorResponse.class);
    }

    @Test
    public void testUpdateInvalidDog() {
        Dog dog = new Dog(null, "Second", LocalDate.now(), 30, 6);
        given().body(dog).accept(JSON).contentType(JSON)
               .when().put(DOG_ID, "2")
               .then().assertThat().statusCode(BAD_REQUEST.value()).extract().body().as(ErrorResponse.class);
    }
}
