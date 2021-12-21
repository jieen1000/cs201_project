package com.kaizen.repository;

import java.util.List;

import com.kaizen.model.entity.Employee;
import com.kaizen.model.entity.Image;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * News specific extension of
 * {@link org.springframework.data.jpa.repository.JpaRepository}.
 *
 * @author Tan Jie En
 * @version 1.0
 * @since 2021-10-20
 */

public interface ImageRepository extends JpaRepository<Image, Long> {
    

    /**
      * Find the Lists of Image of an Employee.
      *
      * @param emp the Employee object
      * @return the list of Images that belongs to the Employee.
      */
    List<Image> findByEmployee(Employee emp);
}
