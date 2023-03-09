package com.senchenko.person.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.senchenko.person.FileUtil;
import com.senchenko.person.model.dto.PersonDto;
import com.senchenko.person.model.entity.Person;
import com.senchenko.person.repository.PersonRepository;
import com.senchenko.person.service.PersonService;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class PersonIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final int EXPECTED_ROWS_COUNT = 1;
    private static final String URL_TEMPLATE = "/v1/persons";

    @Autowired
    private PersonRepository repository;
    @Autowired
    private PersonService personService = mock(PersonService.class);

    @Test
    @SneakyThrows
    @DisplayName("Create person.Should return 200")
    void createPersonTest() {
        // Generate a new PersonDto
        val personDto = getObjectMapper().readValue(
                FileUtil.getResourceFileAsString("json/person/person-dto.json")
                        .replace("{{id}}", UUID.randomUUID().toString()), PersonDto.class);

        getMvc().perform(post(URL_TEMPLATE)
                        .contentType(APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(personDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(getObjectMapper().writeValueAsString(personDto)));
        assertThat(repository.findAll()).hasSize(EXPECTED_ROWS_COUNT);
    }

    @Test
    @SneakyThrows
    @DisplayName("Find a page of person. Should return 200")
    void findAllTest() {
        val person = getObjectMapper().readValue(
                FileUtil.getResourceFileAsString("json/person/person.json")
                        .replace("{{id}}", UUID.randomUUID().toString()), Person.class);
        person.setId(null);
        person.setFirstName("person first name");
        repository.save(person);
        val person2 = getObjectMapper().readValue(
                FileUtil.getResourceFileAsString("json/person/person.json")
                        .replace("{{id}}", UUID.randomUUID().toString()), Person.class);
        person.setId(null);
        person.setFirstName("person second name");
        person.setFirstName("person first name");
        repository.save(person2);

        MvcResult result = getMvc()
                .perform(get(URL_TEMPLATE)
                        .param("offset", "0")
                        .param("limit", "2"))
                .andExpect(status().isOk())
                .andReturn();

        // Check that the response body contains an array of two objects
        String responseBody = result.getResponse().getContentAsString();
        JsonNode jsonNode = getObjectMapper().readTree(responseBody);
        assertTrue(jsonNode.isArray());
        assertEquals(2, jsonNode.size());

    }

    @Test
    @SneakyThrows
    @DisplayName("Find person by ID. Should return 200")
    void findPersonByIdTest()  {
        // Create a new Person and save it to the repository
        val person = getObjectMapper().readValue(
                FileUtil.getResourceFileAsString("json/person/person.json")
                        .replace("{{id}}", UUID.randomUUID().toString()), Person.class);
        person.setId(null);
        repository.save(person);

        // Perform a GET request to retrieve the Person by ID
        MvcResult result = getMvc()
                .perform(get(URL_TEMPLATE + "/" + person.getId()))
                .andExpect(status().isOk())
                .andReturn();

        // Check that the response body contains the expected Person object
        String responseBody = result.getResponse().getContentAsString();
        val personDto = getObjectMapper().readValue(responseBody, PersonDto.class);
        assertEquals(person.getId(), personDto.getId());
        assertEquals(person.getFirstName(), personDto.getFirstName());
        assertEquals(person.getLastName(), personDto.getLastName());
    }

    @Test
    @SneakyThrows
    @DisplayName("Update person test")
    void updateTest() {
        val person = getObjectMapper().readValue(
                FileUtil.getResourceFileAsString("json/person/person.json")
                        .replace("{{id}}", UUID.randomUUID().toString()), Person.class);
        person.setId(null);
        repository.save(person);

        val person2 = getObjectMapper().readValue(
                FileUtil.getResourceFileAsString("json/person/person.json")
                        .replace("{{id}}", UUID.randomUUID().toString()), Person.class);
        person2.setFirstName("second person name");
        person.setId(null);
        repository.save(person);



        val personDto = getObjectMapper().readValue(
                FileUtil.getResourceFileAsString("json/person/person-dto.json")
                        .replace("{{id}}", UUID.randomUUID().toString()), Person.class);
        personDto.setId(null);
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        repository.save(person);
        MvcResult result = getMvc()
                .perform(get(URL_TEMPLATE + "/" + person.getId()))
                .andExpect(status().isOk())
                .andReturn();

        // Check that the response body contains the expected Person object
        String responseBody = result.getResponse().getContentAsString();
       val personDto2 = getObjectMapper().readValue(responseBody, PersonDto.class);
        assertEquals(person.getId(), personDto2.getId());
        assertEquals(person.getFirstName(), personDto2.getFirstName());
        assertEquals(person.getLastName(), personDto2.getLastName());
    }
    @Test
    @DisplayName("Delete person by ID. Should return 204")
    void deletePersonByIdTest() throws Exception {
        // Create a new Person and save it to the repository
        val person = getObjectMapper().readValue(
                FileUtil.getResourceFileAsString("json/person/person.json")
                        .replace("{{id}}", UUID.randomUUID().toString()), Person.class);
        person.setId(null);
        repository.save(person);

        // Perform a DELETE request to delete the Person by ID
        mockMvc.perform(delete(URL_TEMPLATE + "/" + person.getId()))
                .andExpect(status().isNoContent());

        // Verify that the Person has been deleted from the repository
        assertFalse(repository.existsById(person.getId()));
    }
}