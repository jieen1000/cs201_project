package com.kaizen.repository;

import com.kaizen.model.entity.Updates;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Updates specific extension of
 * {@link org.springframework.data.jpa.repository.JpaRepository}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
public interface UpdatesRepository extends JpaRepository<Updates, Long> {
}