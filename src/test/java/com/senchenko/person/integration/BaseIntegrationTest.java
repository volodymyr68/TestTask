package com.senchenko.person.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@Getter
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
@Testcontainers
@ContextConfiguration(initializers = {BaseIntegrationTest.Initializer.class})
@TestPropertySource(properties = {"spring.config.location=classpath:application.yaml"})
public abstract class BaseIntegrationTest {

    private static final String[] PG_TABLES_TO_CLEAN_UP = new String[]{"person"};
    private static final String DATABASE_NAME = "TestTask";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate postgresJdbcTemplate;


    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
        clearAllTables();
    }

    private void clearAllTables() {
        JdbcTestUtils.deleteFromTables(postgresJdbcTemplate, PG_TABLES_TO_CLEAN_UP);
    }

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11.1")
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME);

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "CONTAINER.USERNAME=" + postgreSQLContainer.getUsername(),
                    "CONTAINER.PASSWORD=" + postgreSQLContainer.getPassword(),
                    "CONTAINER.URL=" + postgreSQLContainer.getJdbcUrl()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}
