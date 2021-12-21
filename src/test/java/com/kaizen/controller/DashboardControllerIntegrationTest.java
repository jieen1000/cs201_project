package com.kaizen.controller;

import com.kaizen.model.*;
import com.kaizen.repository.NewsRepository;
import com.kaizen.repository.UpdatesRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * {@code DashboardControllerIntegrationTest} is a test class to do integration
 * testing from {@link DashboardController} using H2 embeded database.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DashboardControllerIntegrationTest {
    /**
     * The dashboard's controller used for testing.
     */
    @Autowired
    private DashboardController dashboardController;

    /**
     * The updates's repository used for testing.
     */
    @Autowired
    private UpdatesRepository updatesRepository;

    /**
     * The news's repository used for testing.
     */
    @Autowired
    private NewsRepository newsRepository;

    /**
     * {@code tearDown} is apply after each tests to clear database
     */
    @AfterEach
    public void tearDown() {
        updatesRepository.deleteAll();
        newsRepository.deleteAll();
    }

    /**
     * {@code getUpdates_Found_ExpectOKFound} is a test on
     * {@link DashboardController#getUpdates()} to verify if the method will return
     * Http Status Ok(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getUpdates_Found_ExpectOKFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestDashboardController.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(dashboardController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * {@code getNews_Found_ExpectOKFound} is a test on
     * {@link DashboardController#getNews()} to verify if the method will return
     * Http Status Ok(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getNews_Found_ExpectOKFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestDashboardController.URL_EXTENSION_NEWS);

        MockMvcBuilders.standaloneSetup(dashboardController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}