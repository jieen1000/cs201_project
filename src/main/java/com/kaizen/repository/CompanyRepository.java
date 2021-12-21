package com.kaizen.repository;

import com.kaizen.model.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Company specific extension of
 * {@link org.springframework.data.jpa.repository.JpaRepository}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
public interface CompanyRepository extends JpaRepository<Company, String> {
    Company findByName(String name);
}