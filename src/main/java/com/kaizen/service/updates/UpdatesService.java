package com.kaizen.service.updates;

import java.util.List;

import com.kaizen.model.entity.Updates;

/**
 * {@code UpdatesService} captures what are needed for business's logic for
 * updates.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
public interface UpdatesService {
  /**
   * Get the construction's updates on COVID-19.
   * 
   * @return the list of construction's updates for COVID-19.
   */
  public List<Updates> getUpdates();

  /**
   * Replace all of the updates in the repository with the latest updates.
   */
  public void updateRepository();
}