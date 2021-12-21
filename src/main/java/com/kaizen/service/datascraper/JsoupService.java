package com.kaizen.service.datascraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * {@code JsoupService} captures what are needed for business's logic for jsoup.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
public abstract class JsoupService {
  /**
   * The user agent to connect.
   */
  private static final String USER_AGENT = "Mozilla";

  /**
   * The key of name data.
   */
  private static final String DATA_NAME_KEY = "name";

  /**
   * The value of name data.
   */
  private static final String DATA_NAME_VALUE = "jsoup";

  /**
   * The key of href attribute.
   */
  protected static final String ATTR_HREF = "href";

  /**
   * The number of contents to store.
   */
  protected int TO_STORE = 5;

  /**
   * Get the webpage content of the specific URL.
   * 
   * @param url the URL to get the webpage content.
   * @return the document containing the webpage content of the specific URL.
   * @throws Exception if any exceptions occurs.
   */
  protected Document getWebpageContent(String url) throws Exception {
    return Jsoup.connect(url).userAgent(USER_AGENT).data(DATA_NAME_KEY, DATA_NAME_VALUE).get();
  }
}