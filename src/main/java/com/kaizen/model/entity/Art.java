package com.kaizen.model.entity;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.PastOrPresent;
import com.kaizen.model.AbstractEntity;

import java.time.LocalDate;

/**
 * Represents an employee.
 *
 * @author Teo Keng Swee
 * @author Pang Jun Rong
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-20
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "covidtest")
public class Art extends AbstractEntity {
    /**
     * Represents the date when the ART is taken.
     */
    @Column(name = "dateOfTest")
    @PastOrPresent
    private LocalDate dateOfTest;

    /**
     * Represents the date at which the employee is required to take ART again.
     */
    @Column(name = "expiryDate")
    private LocalDate expiryDate;

    /**
     * Represents the date at which the employee is required to take ART again.
     */
    @Column(name = "result")
    private boolean result;

    /**
     * Represents the employee that took the tests.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    /**
     * Represents the company of employee.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_uen")
    private Company company;
}
