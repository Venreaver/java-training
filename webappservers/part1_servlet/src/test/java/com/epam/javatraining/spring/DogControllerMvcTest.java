package com.epam.javatraining.spring;

import com.epam.javatraining.spring.model.Dog;
import com.epam.javatraining.spring.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.time.LocalDate;

import static com.epam.javatraining.model.DogValidationTest.generateDog;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:test-context.xml")
public class DogControllerMvcTest extends AbstractTestNGSpringContextTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private static String BASE_URI = "/dog/";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext) applicationContext).build();
        objectMapper = Jackson2ObjectMapperBuilder.json().build();
    }

    @Test
    public void givenWhenGetAllDogsThenCollectionWithSizeGreaterThanOrEqualTo5() throws Exception {
        mockMvc.perform(get(BASE_URI).accept(MediaType.APPLICATION_JSON)
                                     .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is(OK.value()))
               .andReturn()
               .getResponse();
    }

    @Test
    public void givenPostedDogWhenGetDogByPostedDogIdThenGotDogIsEqualsToPostedDog() throws Exception {
        Dog postedDog = getObject(mockMvc.perform(post(BASE_URI).content(objectMapper.writeValueAsString(generateDog()))
                                                                .accept(MediaType.APPLICATION_JSON)
                                                                .contentType(MediaType.APPLICATION_JSON))
                                         .andReturn(), Dog.class);
        Dog gotDog = getObject(mockMvc.perform(get(BASE_URI + postedDog.getId()).accept(MediaType.APPLICATION_JSON)
                                                                                .contentType(MediaType.APPLICATION_JSON))
                                      .andExpect(status().is(OK.value()))
                                      .andReturn(), Dog.class);
        assertReflectionEquals(postedDog, gotDog);
    }

    @Test
    public void givenGeneratedDogWhenPostGeneratedDogThenPostedDogIsEqualsToGeneratedDog() throws Exception {
        Dog generatedDog = generateDog();
        Dog dog = getObject(mockMvc.perform(post(BASE_URI).content(objectMapper.writeValueAsString(generatedDog))
                                                          .accept(MediaType.APPLICATION_JSON)
                                                          .contentType(MediaType.APPLICATION_JSON))
                                   .andExpect(status().is(OK.value()))
                                   .andReturn(), Dog.class);
        generatedDog.setId(dog.getId());
        assertReflectionEquals(generatedDog, dog);
    }

    @Test
    public void givenPostedDogAndGeneratedDogWhenUpdatePostedDogByGeneratedDogThenUpdatedDogIsEqualsToGeneratedDogWithPostedDogId() throws Exception {
        Dog postedDog = getObject(mockMvc.perform(post(BASE_URI).content(objectMapper.writeValueAsString(generateDog()))
                                                                .accept(MediaType.APPLICATION_JSON)
                                                                .contentType(MediaType.APPLICATION_JSON))
                                         .andReturn(), Dog.class);
        Dog generatedDog = generateDog();
        Dog dog = getObject(mockMvc.perform(put(BASE_URI + postedDog.getId()).content(objectMapper.writeValueAsString(generatedDog))
                                                                             .accept(MediaType.APPLICATION_JSON)
                                                                             .contentType(MediaType.APPLICATION_JSON))
                                   .andExpect(status().is(OK.value()))
                                   .andReturn(), Dog.class);
        generatedDog.setId(postedDog.getId());
        assertReflectionEquals(generatedDog, dog);
    }

    @Test
    public void givenPostedDogWhenDeleteDogByPostedDogIdThenHttpStatusIs204() throws Exception {
        Dog postedDog = getObject(mockMvc.perform(post(BASE_URI).content(objectMapper.writeValueAsString(generateDog()))
                                                                .accept(MediaType.APPLICATION_JSON)
                                                                .contentType(MediaType.APPLICATION_JSON))
                                         .andReturn(), Dog.class);
        mockMvc.perform(delete(BASE_URI + postedDog.getId()).accept(MediaType.APPLICATION_JSON)
                                                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is(NO_CONTENT.value()));
    }

    @Test
    public void givenGeneratedDogWhenGetNoExistingDogByGeneratedDogIdThenHttpStatusIs404() throws Exception {
        Dog generatedDog = generateDog();
        ErrorResponse response = getObject(mockMvc.perform(get(BASE_URI + generatedDog.getId()).accept(MediaType.APPLICATION_JSON)
                                                                                               .contentType(MediaType.APPLICATION_JSON))
                                                  .andExpect(status().is(NOT_FOUND.value()))
                                                  .andReturn(), ErrorResponse.class);
        assertThat(response.getInfo(), equalTo(String.format("Dog with id %s does not exist", generatedDog.getId())));
    }

    @Test
    public void givenGeneratedDogWhenUpdateNoExistingDogByGeneratedDogIdThenHttpStatusIs404() throws Exception {
        Dog generatedDog = generateDog();
        ErrorResponse response = getObject(mockMvc.perform(put(BASE_URI + generatedDog.getId()).content(objectMapper.writeValueAsString(generatedDog))
                                                                                               .accept(MediaType.APPLICATION_JSON)
                                                                                               .contentType(MediaType.APPLICATION_JSON))
                                                  .andExpect(status().is(NOT_FOUND.value()))
                                                  .andReturn(), ErrorResponse.class);
        assertThat(response.getInfo(), equalTo(String.format("Dog with id %s does not exist", generatedDog.getId())));
    }

    @Test
    public void givenGeneratedDogWhenDeleteNoExistingDogByGeneratedDogIdThenHttpStatusIs404() throws Exception {
        Dog generatedDog = generateDog();
        ErrorResponse response = getObject(mockMvc.perform(delete(BASE_URI + generatedDog.getId()).accept(MediaType.APPLICATION_JSON)
                                                                                                  .contentType(MediaType.APPLICATION_JSON))
                                                  .andExpect(status().is(NOT_FOUND.value()))
                                                  .andReturn(), ErrorResponse.class);
        assertThat(response.getInfo(), equalTo(String.format("Dog with id %s does not exist", generatedDog.getId())));
    }

    @Test
    public void givenGeneratedDogWithInvalidNameWhenPostInvalidDogThenHttpStatusIs400() throws Exception {
        Dog generatedDog = generateDog();
        generatedDog.setName("");
        getObject(mockMvc.perform(post(BASE_URI).content(objectMapper.writeValueAsString(generatedDog))
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON))
                         .andExpect(status().is(BAD_REQUEST.value()))
                         .andReturn(), ErrorResponse.class);
    }

    @Test
    public void givenGeneratedDogWithInvalidDateOfBirthWhenUpdateDogByInvalidDogThenHttpStatusIs400() throws Exception {
        Dog generatedDog = generateDog();
        generatedDog.setDateOfBirth(LocalDate.now());
        getObject(mockMvc.perform(put(BASE_URI + generatedDog.getId()).content(objectMapper.writeValueAsString(generatedDog))
                                                                      .accept(MediaType.APPLICATION_JSON)
                                                                      .contentType(MediaType.APPLICATION_JSON))
                         .andExpect(status().is(BAD_REQUEST.value()))
                         .andReturn(), ErrorResponse.class);
    }

    private <T> T getObject(MvcResult mvcResult, Class<T> clazz) throws IOException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), clazz);
    }
}
