package com.kaizen.repository;

import com.kaizen.model.entity.Skill;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Skill specific extension of
 * {@link org.springframework.data.jpa.repository.JpaRepository}.
 *
 * @author Chong Zhan Han
 * @version 1.0
 * @since 2021-10-15
 */
public interface SkillRepository extends JpaRepository<Skill, String> {
}

