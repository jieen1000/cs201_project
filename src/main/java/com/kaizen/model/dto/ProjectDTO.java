package com.kaizen.model.dto;

import lombok.*;

import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents an project DTO.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-27
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProjectDTO {
    /**
     * Represents the project's id.
     */
    private Long id;

    /**
     * Represents the name of the project.
     */
    @NotNull
    private String projectName;

    /**
     * Represents the date of project commencement.
     */
    @NotNull
    private LocalDate startDate;

    /**
     * Represents the date of completion (both estimated and actual).
     */
    private LocalDate completionDate;

    /**
     * Represents the project budget
     */
    private String budget;

    /**
     * Represents the project progress.
     */
    private double progress;

    /**
     * Represents the company's DTOs involved in project
     */
    private List<ProjectCompanyDTO> companyDTOs;

    /**
     * Represents the employee's DTOs involved in project
     */
    @NotNull
    private List<ProjectEmployeeDTO> employeeDTOs;
}
