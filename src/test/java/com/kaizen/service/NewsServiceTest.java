package com.kaizen.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.kaizen.model.entity.News;
import com.kaizen.repository.NewsRepository;
import com.kaizen.service.datascraper.NewsService;
import com.kaizen.service.datascraper.NewsServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code NewsServiceTest} is a test class to do unit testing on
 * {@link NewsService} using {@link NewsServiceImpl}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
@ContextConfiguration(classes = { NewsServiceImpl.class })
@ExtendWith(SpringExtension.class)
public class NewsServiceTest {
    /**
     * The mocked news's repository used for testing.
     */
    @MockBean
    private NewsRepository newsRepository;

    /**
     * The news's service used for testing.
     */
    @Autowired
    private NewsService newsService;

    /**
     * {@code getNews_Found_ReturnFound} is a test on
     * {@link NewsService#getNews()} to verify if the method will call
     * {@link NewsRepository#findAll()} and return the list of news.
     */
    @Test
    void getNews_Found_ReturnFound() {
        List<News> news = new ArrayList<>();
        when(newsRepository.findAll()).thenReturn(news);

        List<News> foundNewss = newsService.getNews();

        assertSame(news, foundNewss);
        verify(newsRepository).findAll();
    }

    /**
     * {@code updateRepository_Updated} is a test on
     * {@link NewsService#updateRepository()} to verify if the method will call
     * {@link NewsRepository#deleteAll()} and
     * {@link NewsRepository#saveAllAndFlush(Iterable)}.
     */
    @Test
    void updateRepository_Updated() {
        doNothing().when(newsRepository).deleteAll();
        when(newsRepository.saveAllAndFlush(anyIterable())).thenReturn(null);

        newsService.updateRepository();

        verify(newsRepository).deleteAll();
        verify(newsRepository).saveAllAndFlush(anyIterable());
    }
}
