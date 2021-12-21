package com.kaizen.scheduler;

import com.kaizen.service.datascraper.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * {@code NewsScheduler} is a scheduler for the news.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
@Component
public class NewsScheduler {
    /**
     * The news's service used to do the business's logic for news.
     */
    private final NewsService newsService;

    /**
     * Create a news's scheduler with the specific news's service.
     * 
     * @param newsService the news's service used by the application.
     */
    @Autowired
    public NewsScheduler(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * Update the news in repository at the start of application and every hour e.g.
     * 1am, 2am, 3am, ...etc.
     */
    @Bean(initMethod = "init")
    @Scheduled(cron = "0 0 0/1 * * *")
    public void updateNewsRepository() {
        newsService.updateRepository();
    }

}
