package com.kaizen.model.dto;


import lombok.*;

/**
 * Represents an project's company DTO.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCompanyDTO {
    /**
     * Represents the company's UEN.
     */
    private String uen;

    /**
     * Represents the name of the company.
     */
    private String name;
}
