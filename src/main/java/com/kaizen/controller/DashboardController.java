package com.kaizen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.kaizen.model.entity.News;
import com.kaizen.model.entity.Updates;
import com.kaizen.service.datascraper.NewsService;
import com.kaizen.service.updates.UpdatesService;

/**
 * {@code DashboardController} is a rest controller for dashboard.
 *
 * @author Teo Keng Swee
 * @author Bryan Tan
 * @author Tan Jie En
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
   /**
    * The updates's service used to do the business's logic for updates.
    */
   private final UpdatesService updatesService;

   /**
    * The news's service used to do the business's logic for news.
    */
   private final NewsService newsService;

   /**
    * Create a dashboard's controller with the specific updates's service and
    * news's service.
    * 
    * @param updatesService the updates's service used by the application.
    * @param newsService    the news's service used by the application.
    */
   @Autowired
   public DashboardController(UpdatesService updatesService, NewsService newsService) {
      this.updatesService = updatesService;
      this.newsService = newsService;
   }

   /**
    * Get the latest construction's updates on COVID-19.
    * 
    * @return the list of construction's updates for COVID-19.
    */
   @GetMapping
   public List<Updates> getUpdates() {
      return updatesService.getUpdates();
   }

   /**
    * Get the latest news on COVID-19.
    * 
    * @return the list of COVID-19's news.
    */
   @GetMapping("/news")
   public List<News> getNews() {
      return newsService.getNews();
   }
}