package com.kaizen.model.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * Represents an Employee DTO.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-26
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeDTO {
    /**
     * Represents the employee's work permit number(nonnull, 9 to 255 characters) and is the employee's id.
     */
    
    private String workPermitNumber;

    /**
     * Represents the employee's name(nonnull, up to 255 characters).
     */
    private String name;

    /**
     * Represents the employee's passport number(nonnull, 10 to 255 characters).
     */
    private String passportNumber;

    /**
     * Represents the employee's work id(nonnull, 10 to 255 characters).
     */
    private String workId;

    /**
     * Represents the employee's role(nonnull, up to 255 characters).
     */
    private String employeeRole;

    /**
     * Represents the employee's levy(nonnull).
     */
    private int levy;

    /**
     * Represents the employee's work permit date of issue(nonnull, past).
     */
    private LocalDate workPermitDateOfIssue;

    /**
     * Represents the employee's work permit expiry date(nonnull, future).
     */
    private LocalDate workPermitExpiryDate;

    /**
     * Represents the employee's work contact number(nonnull, 8 characters).
     */
    private String workContactNumber;

    /**
     * Represents the employee's work site location(nonnull, 10 to 255 characters).
     */
    private String workSiteLocation;

    /**
     * Represents the employee's singapore address(nonnull, 10 to 255 characters).
     */
    private String singaporeAddress;

    /**
     * Represents the employee's vaccination status(nonnull).
     */
    private boolean vaccStatus;

    /**
     * Represents is the employee for sharing.
     */
    private boolean forSharing;

    /**
     * Represents is the employee shared.
     */
    private boolean shared;

    /**
     * Represents the employee's company(nonnull).
     */
    private String company;

    /**
      * Represents the link to employee's image
      */
    private String profileURL;

    /**
      * Represents the description of employee
      */
    private String description;

}
