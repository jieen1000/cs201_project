package com.kaizen.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.kaizen.model.TestDashboardController;
import com.kaizen.model.entity.News;
import com.kaizen.model.entity.Updates;
import com.kaizen.service.datascraper.NewsService;
import com.kaizen.service.updates.UpdatesService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * {@code DashboardControllerTest} is a test class to do unit testing on
 * {@link DashboardController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
@ContextConfiguration(classes = { DashboardController.class })
@ExtendWith(SpringExtension.class)
class DashboardControllerTest {
    /**
     * The dashboard's controller used for testing.
     */
    @Autowired
    private DashboardController dashboardController;

    /**
     * The mocked updates's service used for testing.
     */
    @MockBean
    private UpdatesService updatesService;

    /**
     * The mocked news's service used for testing.
     */
    @MockBean
    private NewsService newsService;

    /**
     * {@code getUpdates_Found_ExpectOKFound} is a test on
     * {@link DashboardController#getUpdates()} to verify if the method will call
     * {@link UpdatesService#getUpdates()} and return Http Status Ok(200) and
     * content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getUpdates_Found_ExpectOKFound() throws Exception {
        List<Updates> updates = new ArrayList<>();
        when(updatesService.getUpdates()).thenReturn(updates);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestDashboardController.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(dashboardController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        verify(updatesService).getUpdates();
    }

    /**
     * {@code getNews_Found_ExpectOKFound} is a test on
     * {@link DashboardController#getNews()} to verify if the method will call
     * {@link NewsService#getUpdates()} and return Http Status Ok(200) and content
     * type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getNews_Found_ExpectOKFound() throws Exception {
        List<News> news = new ArrayList<>();
        when(newsService.getNews()).thenReturn(news);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestDashboardController.URL_EXTENSION_NEWS);

        MockMvcBuilders.standaloneSetup(dashboardController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        ;

        verify(newsService).getNews();
    }
}
