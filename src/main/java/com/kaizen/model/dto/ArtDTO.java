package com.kaizen.model.dto;

import lombok.*;

import javax.validation.constraints.*;

import com.kaizen.model.entity.Company;

import java.time.LocalDate;

/**
 * Represents an ART DTO.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-20
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ArtDTO {
    /**
     * Represents the ART's id.
     */
    private Long id;

    /**
     * Represents the date when the ART is taken.
     */
    @NotNull(message = "ART's Date Of Test should not be null")
    private LocalDate dateOfTest;

    /**
     * Represents the date at which the employee is required to take ART again.
     */
    private LocalDate expiryDate;

    /**
     * Represents the result whether the employee is tested positive.
     */
    @NotNull(message = "ART's Result should not be null")
    private boolean result;

    /**
     * Represents the employee's work permit number that took the test.
     */
    private String employeeWP;

    /**
     * Represents the employee's name that took the test.
     */
    private String employeeName;

    /**
     * Represents the employee's company.
     */
    private String company;
}
