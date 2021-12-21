package com.kaizen.service.datascraper;

import java.util.List;

import com.kaizen.model.entity.News;

/**
 * {@code NewsService} captures what are needed for business's logic for news.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
public interface NewsService {
  /**
   * Get the news on COVID-19.
   * 
   * @return the list of COVID-19's news.
   */
  public List<News> getNews();

  /**
   * Replace all of the news in the repository with the latest news.
   */
  public void updateRepository();
}