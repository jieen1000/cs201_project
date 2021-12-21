package com.kaizen.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import com.kaizen.model.dto.ProjectCompanyDTO;
import com.kaizen.model.dto.ProjectEmployeeDTO;
import com.kaizen.model.entity.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Project specific extension of
 * {@link org.springframework.data.jpa.repository.JpaRepository}.
 *
 * @author Pang Jun Rong
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-27
 */

public interface ProjectRepository extends JpaRepository<Project, Long> {
    /**
     * Map the specific project and employee.
     *
     * @param projectId  the id of the project to map.
     * @param employeeId the id of the employee to map.
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO project_employee (project_id, employee_id) VALUES (:projectId, :employeeId)", nativeQuery = true)
    public void addProjectEmployee(@Param("projectId") Long projectId, @Param("employeeId") String employeeId);

    /**
     * Update the project with specific field's values.
     *
     * @param id             the id of the project.
     * @param version        the version of the project.
     * @param projectName    the project name of the project.
     * @param startDate      the start date of the project.
     * @param completionDate the completion date of the project.
     * @param budget         the budget of the project.
     * @param progress       the progress of the project.
     */
    @Transactional
    @Modifying
    @Query("UPDATE Project p set p.version = :version, p.projectName = :projectName, p.startDate = :startDate, p.completionDate = :completionDate, p.budget = :budget, p.progress = :progress where p.id = :id")
    public void updateProject(@Param(value = "id") Long id, @Param(value = "version") int version,
            @Param(value = "projectName") String projectName, @Param(value = "startDate") LocalDate startDate,
            @Param(value = "completionDate") LocalDate completionDate, @Param(value = "budget") String budget,
            @Param(value = "progress") double progress);

    /**
     * Delete the employees involved in the specific project.
     *
     * @param projectId  the id of the project to map.
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM project_employee pe WHERE pe.project_id = :projectId", nativeQuery = true)
    public void deleteProjectEmployees(@Param("projectId") Long projectId);

    /**
     * Get project ids that the specific company involved in.
     *
     * @param companyId the id of the company.
     * @return the project ids that the specific company involved in.
     */
    @Query(value = "SELECT DISTINCT pe.project_id AS id FROM project_employee pe, employee e WHERE e.company_id = :companyId AND pe.employee_id = e.work_permit_number", nativeQuery = true)
    public List<Long> getProjectIds(@Param("companyId") String companyId);

    /**
     * Get project the employee's DTOs involved in.
     *
     * @param projectId the id of the project that the employees involved in.
     * @return the employee's DTOs that involved in the specific project.
     */
    @Query(nativeQuery = true)
    public List<ProjectEmployeeDTO> getProjectEmployeeDTOs(@Param("projectId") Long projectId);

    /**
     * Get project the company's DTOs involved in.
     *
     * @param projectId the id of the project that the companies involved in.
     * @return the company's DTOs that involved in the specific project.
     */
    @Query(nativeQuery = true)
    public List<ProjectCompanyDTO> getProjectCompanyDTOs(@Param("projectId") Long projectId);
}