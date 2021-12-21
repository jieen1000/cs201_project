package com.kaizen.repository;

import java.util.List;

import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Employee specific extension of
 * {@link org.springframework.data.jpa.repository.JpaRepository}.
 *
 * @author Teo Keng Swee
 * @version 1.0
 * @since 2021-10-15
 */
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    /**
     * Finds all employees in a company.
     *
     * @param company the company to find employees in
     * @return a list of employees in the company
     */
    List<Employee> findByCompany(Company company);
}