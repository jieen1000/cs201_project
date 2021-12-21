package com.kaizen.repository;

import com.kaizen.model.entity.News;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * News specific extension of
 * {@link org.springframework.data.jpa.repository.JpaRepository}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-19
 */
public interface NewsRepository extends JpaRepository<News, Long> {
}