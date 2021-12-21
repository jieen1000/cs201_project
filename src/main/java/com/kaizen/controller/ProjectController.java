package com.kaizen.controller;

import com.kaizen.exceptions.*;
import com.kaizen.model.dto.ProjectDTO;
import com.kaizen.service.project.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import javax.validation.Valid;

/**
 * {@code ProjectController} is a rest controller for Project.
 * 
 * @author Pang Jun Rong
 * @author Teo Keng Swee
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    /**
     * The project's service used to do the business's logic for project.
     */
    private final ProjectService projectService;

    /**
     * Create a project's controller with the specific project's service.
     * 
     * @param projectService  the project's service used by the application.
     */
    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Get all project DTOs through project's service.
     * 
     * @return the list of all project DTOs.
     */
    @GetMapping
    public List<ProjectDTO> getProjects() {
        return projectService.listProjects();
    }

    /**
     * Get all project DTOs of a specific company through project's service.
     * 
     * @param companyId the id of company to get for.
     * @return the list of all project DTOs of the specific company.
     * @exception NullValueException       If the given id is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     */
    @GetMapping(params = "companyId")
    public List<ProjectDTO> getCompanyProjects(@RequestParam String companyId) 
            throws NullValueException, ObjectNotExistsException {
        return projectService.getCompanyProjects(companyId);
    }

    /**
     * Create the specific project through project's service.
     * 
     * @param projectDTO the project DTO to create from.
     * @exception NullValueException If the DTO of project is null.
     * @return the created project's DTO.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProjectDTO createProject(@Valid @RequestBody ProjectDTO projectDTO)
            throws NullValueException {
        return projectService.addProject(projectDTO);
    }

    /**
     * Update the specific project through project's service.
     * 
     * @param id         the id of the project to update.
     * @param projectDTO the project's DTO to update.
     * @exception NullValueException       If the id of the project is null or the
     *                                     project's DTO is null.
     * @exception ObjectNotExistsException If the project is not in the repository.
     * @return the updated project's DTO.
     */
    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, @Valid @RequestBody ProjectDTO projectDTO)
            throws NullValueException, ObjectExistsException {
        return projectService.updateProject(id, projectDTO);
    }

    /**
     * Delete the project with the specific id through project's service.
     * 
     * @param id the id of the project to delete.
     * @exception NullValueException       If the id is null.
     * @exception ObjectNotExistsException If the project is not in the repository.
     */
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) throws NullValueException, ObjectNotExistsException {
        projectService.deleteProject(id);
    }
}