package com.kaizen.model.entity;

import lombok.*;

import javax.persistence.*;

import com.kaizen.model.AbstractEntity;
import com.kaizen.model.dto.ProjectCompanyDTO;
import com.kaizen.model.dto.ProjectEmployeeDTO;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents an project.
 *
 * @author Pang Jun Rong
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-27
 */
@NamedNativeQuery(name = "Project.getProjectEmployeeDTOs", query = "SELECT t.id AS id, t.name AS name, i.profile_url AS profile_url from (SELECT pe.employee_id AS id, e.employee_name AS name FROM employee e, project_employee pe WHERE pe.project_id = :projectId AND pe.employee_id = e.work_permit_number GROUP BY pe.employee_id) AS t LEFT JOIN employeeimage i ON t.id = i.employee_id GROUP BY t.id", resultSetMapping = "Mapping.ProjectEmployeeDTO")
@SqlResultSetMapping(name = "Mapping.ProjectEmployeeDTO", classes = @ConstructorResult(targetClass = ProjectEmployeeDTO.class, columns = {
        @ColumnResult(name = "id"), @ColumnResult(name = "name"), @ColumnResult(name = "profile_url") }))
@NamedNativeQuery(name = "Project.getProjectCompanyDTOs", query = "SELECT e.company_id AS id, c.company_name AS name FROM employee e, project_employee pe, company c WHERE pe.project_id = :projectId AND pe.employee_id = e.work_permit_number AND e.company_id = c.uen GROUP BY e.company_id", resultSetMapping = "Mapping.ProjectCompanyDTO")
@SqlResultSetMapping(name = "Mapping.ProjectCompanyDTO", classes = @ConstructorResult(targetClass = ProjectCompanyDTO.class, columns = {
        @ColumnResult(name = "id"), @ColumnResult(name = "name") }))
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "projects")
public class Project extends AbstractEntity {
    /**
     * Represents the name of the project.
     */
    @Column(name = "projectName")
    private String projectName;

    /**
     * Represents the date of project commencement.
     */
    @Column(name = "startDate")
    private LocalDate startDate;

    /**
     * Represents the date of completion (both estimated AND actual).
     */
    @Column(name = "completionDate")
    private LocalDate completionDate;

    /**
     * Represents the project budget
     */
    @Column(name = "budget")
    private String budget;

    /**
     * Represents the project progress.
     */
    @Column(name = "progress")
    private double progress;

    /**
     * Represents the employees involved in project
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_employee", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees;
}
