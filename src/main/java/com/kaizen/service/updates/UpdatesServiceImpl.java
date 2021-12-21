package com.kaizen.service.updates;

import org.springframework.stereotype.Service;

import java.util.*;

import org.jsoup.nodes.*;
import org.jsoup.select.*;

import com.kaizen.model.entity.Updates;
import com.kaizen.repository.UpdatesRepository;
import com.kaizen.service.datascraper.JsoupService;

/**
 * {@code UpdatesServiceImpl} is an extension of JsoupService and implementation
 * of {@code UpdatesService}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
@Service
public class UpdatesServiceImpl extends JsoupService implements UpdatesService {
    /**
     * The updates's repository that stored updates.
     */
    private final UpdatesRepository updatesRepository;

    /**
     * The URL to get latest construction's updates for COVID-19 from Building and
     * Construction Authority(BCA).
     */
    private final String BCA_COVID19_CONSTRUCTION_UPDATES_URL = "https://www1.bca.gov.sg/COVID-19/construction-updates";

    /**
     * Create an updates's service implementation with the specific updates's
     * repository.
     * 
     * @param updatesRepository the updates's repository used by the application.
     */
    public UpdatesServiceImpl(UpdatesRepository updatesRepository) {
        this.updatesRepository = updatesRepository;
    }

    /**
     * Get the construction's updates on COVID-19 from news repository.
     * 
     * @return the list of construction's updates on COVID-19.
     */
    @Override
    public List<Updates> getUpdates() {
        return updatesRepository.findAll();
    }

    /**
     * Get the latest construction's updates on COVID-19 from BCA by scraping from
     * the BCA COVID-19 construction updates page.
     * 
     * @return the list of construction's updates for COVID-19
     *         from BCA.
     */
    public List<Updates> getUpdatesFromWebpage() {
        List<Updates> listOfUpdates = new ArrayList<Updates>();

        try {
            Document webpageContent = getWebpageContent(BCA_COVID19_CONSTRUCTION_UPDATES_URL);

            Elements updatesBlock = webpageContent.select("div.sfContentBlock");
            Elements rows = updatesBlock.select("td");
            int count = 0;

            rowloop: for (Element row : rows) {
                String date = row.select("strong:containsOwn( )").text();
                // System.out.println("***Date:" + date + " ***");
                Element firstUpdate = row.select("a:contains( )").first();
                Set<String> headerSet = new HashSet<>();
                headerSet.add(firstUpdate.text());

                Elements updates = firstUpdate.parent().children();
                updates = updates.next("br");

                for (Element update : updates) {
                    update = update.nextElementSibling();
                    if (update == null || headerSet.contains(update.text()) || update.attr(ATTR_HREF).length() == 0)
                        continue;
                    String header = update.text();
                    String url = update.attr(ATTR_HREF);
                    headerSet.add(header);
                    listOfUpdates.add(new Updates(header, url, date));
                    if (++count >= TO_STORE)
                        break rowloop;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // for (ScrapBox item : listOfUpdates) {
        // System.out.println(item.getHeader());
        // }
        return listOfUpdates;
    }

    /**
     * Replace all of the news in the repository with the news retrieved from
     * webpage.
     */
    @Override
    public void updateRepository() {
        updatesRepository.deleteAll();
        updatesRepository.saveAllAndFlush(getUpdatesFromWebpage());
    }
}