package com.kaizen.model.dto;

import lombok.*;

/**
 * Represents an project's employee DTO.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEmployeeDTO {
    /**
     * Represents the employee's work permit number(id).
     */
    private String workPermitNumber;

    /**
     * Represents the name of the employee.
     */
    private String name;

    /**
     * Represents the image's URL of the employee.
     */
    private String imageUrl;
}
