package com.epam.javatraining.dogapp.controller;

import com.epam.javatraining.dogapp.model.Dog;
import com.epam.javatraining.dogapp.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDate;

import static com.epam.javatraining.dogapp.model.DogValidationTest.generateDog;
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

@WebAppConfiguration
@ContextConfiguration("classpath:web-context.xml")
public class DogControllerMvcTest extends AbstractTestNGSpringContextTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private static String BASE_URI = "/dog/";

    @BeforeMethod
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext) applicationContext).build();
        objectMapper = Jackson2ObjectMapperBuilder.json().build();
    }

    @Test
    public void getList_results_into_dogCollection() throws Exception {
        mockMvc.perform(get(BASE_URI).accept(MediaType.APPLICATION_JSON)
                                     .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is(OK.value()))
               .andReturn()
               .getResponse();
    }

    @Test
    public void getDog_results_into_appropriateDog() throws Exception {
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
    public void postDog_results_into_appropriateDogCreated() throws Exception {
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
    public void updateDog_results_into_appropriateDogUpdated() throws Exception {
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
    public void deleteDog_results_into_httpStatusIs204() throws Exception {
        Dog postedDog = getObject(mockMvc.perform(post(BASE_URI).content(objectMapper.writeValueAsString(generateDog()))
                                                                .accept(MediaType.APPLICATION_JSON)
                                                                .contentType(MediaType.APPLICATION_JSON))
                                         .andReturn(), Dog.class);
        mockMvc.perform(delete(BASE_URI + postedDog.getId()).accept(MediaType.APPLICATION_JSON)
                                                            .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is(NO_CONTENT.value()));
    }

    @Test
    public void getNotExistingDog_results_into_httpStatusIs404() throws Exception {
        Dog generatedDog = generateDog();
        ErrorResponse response = getObject(mockMvc.perform(get(BASE_URI + generatedDog.getId()).accept(MediaType.APPLICATION_JSON)
                                                                                               .contentType(MediaType.APPLICATION_JSON))
                                                  .andExpect(status().is(NOT_FOUND.value()))
                                                  .andReturn(), ErrorResponse.class);
        assertThat(response.getInfo(), equalTo(String.format("Dog with id %s does not exist", generatedDog.getId())));
    }

    @Test
    public void updateNotExistingDog_results_into_httpStatusIs404() throws Exception {
        Dog generatedDog = generateDog();
        ErrorResponse response = getObject(mockMvc.perform(put(BASE_URI + generatedDog.getId()).content(objectMapper.writeValueAsString(generatedDog))
                                                                                               .accept(MediaType.APPLICATION_JSON)
                                                                                               .contentType(MediaType.APPLICATION_JSON))
                                                  .andExpect(status().is(NOT_FOUND.value()))
                                                  .andReturn(), ErrorResponse.class);
        assertThat(response.getInfo(), equalTo(String.format("Dog with id %s does not exist", generatedDog.getId())));
    }

    @Test
    public void deleteNotExistingDog_results_into_httpStatusIs404() throws Exception {
        Dog generatedDog = generateDog();
        ErrorResponse response = getObject(mockMvc.perform(delete(BASE_URI + generatedDog.getId()).accept(MediaType.APPLICATION_JSON)
                                                                                                  .contentType(MediaType.APPLICATION_JSON))
                                                  .andExpect(status().is(NOT_FOUND.value()))
                                                  .andReturn(), ErrorResponse.class);
        assertThat(response.getInfo(), equalTo(String.format("Dog with id %s does not exist", generatedDog.getId())));
    }

    @Test
    public void postInvalidDog_results_into_httpStatusIs400() throws Exception {
        Dog generatedDog = generateDog();
        generatedDog.setName("");
        getObject(mockMvc.perform(post(BASE_URI).content(objectMapper.writeValueAsString(generatedDog))
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON))
                         .andExpect(status().is(BAD_REQUEST.value()))
                         .andReturn(), ErrorResponse.class);
    }

    @Test
    public void putInvalidDog_results_into_httpStatusIs400() throws Exception {
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
