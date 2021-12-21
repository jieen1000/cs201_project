package com.kaizen.repository;

import java.util.List;

import com.kaizen.model.entity.Art;
import com.kaizen.model.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * ART specific extension of
 * {@link org.springframework.data.jpa.repository.JpaRepository}.
 *
 * @author Teo Keng Swee
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-10-15
 */


public interface ArtRepository extends JpaRepository<Art, Long> {


    /**
     * Finding the list of latest ARTS
     *
     * 
     * @return a list of latest ARTs
     */
    @Query(value = "SELECT * FROM covidtest WHERE concat(employee_id, date_of_test) IN (SELECT concat(employee_id, max(date_of_test)) FROM covidtest GROUP BY employee_id)", nativeQuery = true)
    List<Art> findLatestResultsforAllEmployee();

     /**
      * Find the List of ARTs done within a specific company.
      *
      * @param company the company object to which the art is done in
      * @return the list of ART's done within a specific company.
      */
    List<Art> findByCompany(Company company);
}