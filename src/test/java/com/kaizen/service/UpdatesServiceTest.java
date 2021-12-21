package com.kaizen.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.kaizen.model.entity.Updates;
import com.kaizen.repository.UpdatesRepository;
import com.kaizen.service.updates.UpdatesService;
import com.kaizen.service.updates.UpdatesServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code UpdatesServiceTest} is a test class to do unit testing on
 * {@link UpdatesService} using {@link UpdatesServiceImpl}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
@ContextConfiguration(classes = { UpdatesServiceImpl.class })
@ExtendWith(SpringExtension.class)
public class UpdatesServiceTest {
    /**
     * The mocked updates's repository used for testing.
     */
    @MockBean
    private UpdatesRepository updatesRepository;

    /**
     * The updates's service used for testing.
     */
    @Autowired
    private UpdatesService updatesService;

    /**
     * {@code getUpdates_Found_ReturnFound} is a test on
     * {@link UpdatesService#getUpdates()} to verify if the method will call
     * {@link UpdatesRepository#findAll()} and return the list of updates.
     */
    @Test
    void getUpdates_Found_ReturnFound() {
        List<Updates> updates = new ArrayList<>();
        when(updatesRepository.findAll()).thenReturn(updates);

        List<Updates> foundUpdatess = updatesService.getUpdates();

        assertSame(updates, foundUpdatess);
        verify(updatesRepository).findAll();
    }

    /**
     * {@code updateRepository_Updated} is a test on
     * {@link UpdatesService#updateRepository()} to verify if the method will call
     * {@link UpdatesRepository#deleteAll()} and
     * {@link UpdatesRepository#saveAllAndFlush(Iterable)}.
     */
    @Test
    void updateRepository_Updated() {
        doNothing().when(updatesRepository).deleteAll();
        when(updatesRepository.saveAllAndFlush(anyIterable())).thenReturn(null);

        updatesService.updateRepository();

        verify(updatesRepository).deleteAll();
        verify(updatesRepository).saveAllAndFlush(anyIterable());
    }
}
