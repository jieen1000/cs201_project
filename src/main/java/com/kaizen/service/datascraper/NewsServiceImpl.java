package com.kaizen.service.datascraper;

import org.springframework.stereotype.Service;

import java.util.*;

import org.jsoup.nodes.*;
import org.jsoup.select.*;

import com.kaizen.model.entity.News;
import com.kaizen.repository.NewsRepository;

/**
 * {@code NewsServiceImpl} is an extension of JsoupService and implementation of
 * {@code NewsService}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
@Service
public class NewsServiceImpl extends JsoupService implements NewsService {
    /**
     * The news's repository that stored news.
     */
    private final NewsRepository newsRepository;

    /**
     * The URL of Channel News Asia(CNA).
     */
    private final String CNA_URL = "https://www.channelnewsasia.com/";

    /**
     * The URL to get latest COVID-19's news from Channel News Asia(CNA).
     */
    private final String CNA_COVID19_URL = CNA_URL + "topic/covid-19";

    /**
     * Create a news's service implementation with the specific news's repository.
     * 
     * @param newsRepository the news's repository used by the application.
     */
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    /**
     * Get the news on COVID-19 from news repository.
     * 
     * @return the list of COVID-19's news.
     */
    @Override
    public List<News> getNews() {
        return newsRepository.findAll();
    }

    /**
     * Get the latest news on COVID-19 from CNA by scraping from the CNA COVID-19
     * news page.
     * 
     * @return the list of COVID-19's news from CNA.
     */
    public List<News> getNewsFromWebpage() {
        ArrayList<News> listOfNews = new ArrayList<News>();
        try {
            Document webpageContent = getWebpageContent(CNA_COVID19_URL);

            Elements newsBlock = webpageContent.select("div.views-element-container");
            Elements news = newsBlock.select("h6.h6--.list-object__heading");
            int count = 0;

            for (Element item : news) {
                Element title = item.select("a").first();
                String header = title.text();
                String newsLink = CNA_URL + title.attr(ATTR_HREF);
                Document newsContent = getWebpageContent(newsLink);
                try {
                    Element image = newsContent.select("div.layout__region.layout__region--first")
                            .select("img.image.image--").first();
                    String imageUrl = image.attr("src");
                    String text = newsContent.select("div.text-long").select("p").first().text();
                    listOfNews.add(new News(header, newsLink, text, imageUrl));
                    if (++count >= TO_STORE)
                        break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
       
        return listOfNews;
    }

    /**
     * Replace all of the news in the repository with the news retrieved from
     * webpage.
     */
    @Override
    public void updateRepository() {
        newsRepository.deleteAll();
        newsRepository.saveAllAndFlush(getNewsFromWebpage());
    }
}