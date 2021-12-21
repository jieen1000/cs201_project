package com.kaizen.model.dto;

import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeSkillDTO {
   /**
     * Represents the employee's work permit number(nonnull, 9 to 255 characters) and is the company's id.
     */
    
    private String workPermitNumber;

    /**
     * Represents the employee's name(nonnull, up to 255 characters).
     */
    private String name;

    /**
     * Represents the employee's passport number(nonnull, 10 to 255 characters).
     */
    private String description;

    /**
     * Represents the employee's role(nonnull, up to 255 characters).
     */
    private String employeeRole;

    /**
     * Represents the employee's work id(nonnull, 10 to 255 characters).
     */
    private String skillName;


    /**
     * Represents the employee's levy(nonnull).
     */
    private int experience;

    /**
     * Represents the employee's work contact number(nonnull, 8 characters).
     */
    private double cost;

    /**
     * Represents the employee's work site location(nonnull, 10 to 255 characters).
     */
    private double rating;

    /**
     * Represents the employee's singapore address(nonnull, 10 to 255 characters).
     */
    private String imageURL;

    /**
     * Represents the employee's vaccination status(nonnull).
     */
    private String company;

    /**
     * Represents the company's uen.
     */
    private String uen;

}
