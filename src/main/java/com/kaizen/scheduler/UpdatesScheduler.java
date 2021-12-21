package com.kaizen.scheduler;

import com.kaizen.service.updates.UpdatesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * {@code UpdatesScheduler} is a scheduler for the updates.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
@Component
public class UpdatesScheduler {
    /**
     * The updates's service used to do the business's logic for updates.
     */
    private final UpdatesService updatesService;

    /**
     * Create an updates's scheduler with the specific updates's service.
     * 
     * @param updatesService the updates's service used by the application.
     */
    @Autowired
    public UpdatesScheduler(UpdatesService updatesService) {
        this.updatesService = updatesService;
    }

    /**
     * Update the updates in repository at the start of application and every hour e.g.
     * 1am, 2am, 3am, ...etc.
     */
    @Bean(initMethod = "init")
    @Scheduled(cron = "0 0 0/1 * * *")
    public void updateUpdatesRepository() {
        updatesService.updateRepository();
    }

}
