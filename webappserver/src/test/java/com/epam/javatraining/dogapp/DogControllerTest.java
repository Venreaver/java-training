package com.epam.javatraining.dogapp;

import com.epam.javatraining.dogapp.model.Dog;
import com.epam.javatraining.dogapp.model.ErrorResponse;
import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static com.epam.javatraining.model.DogValidationTest.generateDog;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class DogControllerTest {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080/dog/";
    }

    @Test
    public void getList_results_into_dogCollection() {
        given().accept(JSON).contentType(JSON).when().get()
               .then().statusCode(OK.value()).contentType(JSON).body("size()", greaterThanOrEqualTo(5));
    }

    @Test
    public void getDog_results_into_appropriateDog() {
        Dog postedDog = postDog(generateDog());
        Dog dog = given().accept(JSON).contentType(JSON)
                         .when().get(postedDog.getId())
                         .then().statusCode(OK.value()).contentType(JSON).extract().body().as(Dog.class);
        assertReflectionEquals(postedDog, dog);
    }

    @Test
    public void postDog_results_into_appropriateDogCreated() {
        Dog generatedDog = generateDog();
        Dog dog = postDog(generatedDog);
        generatedDog.setId(dog.getId());
        assertReflectionEquals(generatedDog, dog);
    }

    @Test
    public void updateDog_results_into_appropriateDogUpdated() {
        Dog postedDog = postDog(generateDog());
        Dog generatedDog = generateDog();
        Dog dog = given().body(generatedDog).accept(JSON).contentType(JSON)
                         .when().put(postedDog.getId())
                         .then().statusCode(OK.value()).extract().body().as(Dog.class);
        generatedDog.setId(postedDog.getId());
        assertReflectionEquals(generatedDog, dog);
    }

    @Test
    public void deleteDog_results_into_httpStatusIs204() {
        Dog postedDog = postDog(generateDog());
        given().accept(JSON).contentType(JSON).when().delete(postedDog.getId()).then().statusCode(NO_CONTENT.value());
    }

    @Test
    public void getNotExistingDog_results_into_httpStatusIs404() {
        Dog generatedDog = generateDog();
        ErrorResponse response = given().accept(JSON).contentType(JSON)
                                        .when().get(generatedDog.getId())
                                        .then().statusCode(NOT_FOUND.value()).contentType(JSON)
                                        .extract().body().as(ErrorResponse.class);
        assertThat(response.getInfo(), equalTo(String.format("Dog with id %s does not exist", generatedDog.getId())));
    }

    @Test
    public void updateNotExistingDog_results_into_httpStatusIs404() {
        Dog generatedDog = generateDog();
        ErrorResponse response = given().body(generatedDog).accept(JSON).contentType(JSON)
                                        .when().put(generatedDog.getId())
                                        .then().statusCode(NOT_FOUND.value()).extract().body().as(ErrorResponse.class);
        assertThat(response.getInfo(), equalTo(String.format("Dog with id %s does not exist", generatedDog.getId())));
    }

    @Test
    public void deleteNotExistingDog_results_into_httpStatusIs404() {
        Dog generatedDog = generateDog();
        ErrorResponse response = given().accept(JSON).contentType(JSON)
                                        .when().delete(generatedDog.getId())
                                        .then().statusCode(NOT_FOUND.value())
                                        .extract().body().as(ErrorResponse.class);
        assertThat(response.getInfo(), equalTo(String.format("Dog with id %s does not exist", generatedDog.getId())));
    }

    @Test
    public void postInvalidDog_results_into_httpStatusIs400() {
        Dog generatedDog = generateDog();
        generatedDog.setName("");
        given().body(generatedDog).accept(JSON).contentType(JSON)
               .when().post()
               .then().statusCode(BAD_REQUEST.value()).extract().body().as(ErrorResponse.class);
    }

    @Test
    public void putInvalidDog_results_into_httpStatusIs400() {
        Dog generatedDog = generateDog();
        generatedDog.setDateOfBirth(LocalDate.now());
        given().body(generatedDog).accept(JSON).contentType(JSON)
               .when().put(generatedDog.getId())
               .then().statusCode(BAD_REQUEST.value()).extract().body().as(ErrorResponse.class);
    }

    private Dog postDog(Dog dog) {
        return given().body(dog).accept(JSON).contentType(JSON)
                      .when().post()
                      .then().statusCode(OK.value()).extract().body().as(Dog.class);
    }
}
