package com.kaizen.service.project;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.dto.ProjectDTO;

import java.util.List;

/**
 * {@code ProjectService} captures what are needed for business's logic for
 * Project. Based on ArtService
 * 
 * @author Pang Jun Rong
 * @author Teo Keng Swee
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-27
 */
public interface ProjectService {
    /**
     * Get all project's DTOs stored in the repository.
     * 
     * @return the list of all project's DTOs.
     */
    public List<ProjectDTO> listProjects();

    /**
     * Get all project's DTOs of a specific company that are stored in the
     * repository.
     * 
     * @param companyId the id of company to get for.
     * @return the list of all project's DTOs of the specific company.
     * @exception NullValueException       If the given id is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     */
    public List<ProjectDTO> getCompanyProjects(String companyId) throws NullValueException, ObjectNotExistsException;
    
    /**
     * Create the specific project in the repository.
     * 
     * @param projectDTO the DTO of project to create.
     * @exception NullValueException       If the DTO of project is null or the id
     *                                     of the employee is null.
     * @exception ObjectNotExistsException If the employee is not in the repository.
     * @return the created project's DTO.
     */
    public ProjectDTO addProject(ProjectDTO projectDTO) throws NullValueException, ObjectNotExistsException;

    /**
     * Update the project with the specific id and project DTO in the repository.
     * 
     * @param id         the id of the project to update.
     * @param projectDTO the project's DTO to update.
     * @exception NullValueException       If the id of the project/employee is null
     *                                     or the project's DTO is null.
     * @exception ObjectNotExistsException If the project/employee is not in the
     *                                     repository.
     * @return the updated project's DTO.
     */
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) throws NullValueException, ObjectNotExistsException;

    /**
     * Delete the Project with the specific id in the repository.
     * 
     * @param id the id of the Project to delete.
     * @exception NullValueException       If the id of the Project is null.
     * @exception ObjectNotExistsException If the Project is not in the repository.
     */
    public void deleteProject(Long id) throws NullValueException, ObjectNotExistsException;
}