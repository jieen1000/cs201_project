package com.kaizen.service.project;

import com.kaizen.exceptions.*;
import com.kaizen.model.dto.*;
import com.kaizen.model.entity.Project;
import com.kaizen.repository.ProjectRepository;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.employee.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ProjectServiceImpl} is an implementation of {@code ProjectService}.
 * 
 * @author Pang Jun Rong
 * @author Teo Keng Swee
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-27
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    /**
     * The project's repository that stored projects.
     */
    private final ProjectRepository projectRepository;

    /**
     * The company's service that used by the application.
     */
    private final CompanyService companyService;

    /**
     * The employee's service that used by the application.
     */
    private final EmployeeService employeeService;

    /**
     * Represents the simple name of the Project's class.
     */
    private final String PROJECT_SIMPLE_NAME;

    /**
     * Create a project's service implementation with the specific project's
     * repository, company's service and employee's service and set the
     * {@code PROJECT_SIMPLE_NAME} with the simple name of the Project's class
     * 
     * @param projectRepository the project's repository used by the application.
     * @param companyService    the company's service used by the application.
     * @param employeeService   the employee's service used by the application.
     */
    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, 
            CompanyService companyService, EmployeeService employeeService) {
        this.projectRepository = projectRepository;
        this.companyService = companyService;
        this.employeeService = employeeService;
        PROJECT_SIMPLE_NAME = Project.class.getSimpleName();
    }

    /**
     * Get all project's DTOs stored in the repository.
     * 
     * @return the list of all project's DTOs.
     */
    @Override
    public List<ProjectDTO> listProjects() {
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            projectDTOs.add(convertToDTO(project));
        }
        return projectDTOs;
    }

    /**
     * Get all project's DTOs of a specific company that are stored in the
     * repository.
     * 
     * @param companyId the id of company to get for.
     * @return the list of all project's DTOs of the specific company.
     * @exception NullValueException       If the given id is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     */
    @Override
    public List<ProjectDTO> getCompanyProjects(String companyId) throws NullValueException, ObjectNotExistsException {
        validateCompanyId(companyId);
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        for (Long projectId : projectRepository.getProjectIds(companyId)) {
            Project project = projectRepository.getById(projectId);
            projectDTOs.add(convertToDTO(project));
        }
        return projectDTOs;
    }

    // /**
    // * Get the list of Project with the work permit number of the workId from the
    // repository.
    // *
    // * @param EmpWpNo the work permit number of the employee.
    // * @exception NullValueException If the work permit is null.
    // * @exception ObjectNotExistsException If the Project is not in the
    // repository.
    // * @return the Project with that id.
    // */
    // @Override
    // public Project getProject(String EmpWp) throws NullValueException,
    // ObjectNotExistsException {
    // validateIdNotNull(dateOfTest);
    // return ProjectRepository.findById(dateOfTest).orElseThrow(() -> new
    // ObjectNotExistsException(Project_SIMPLE_NAME, dateOfTest));
    // }

    /**
     * Create the specific project in the repository.
     * 
     * @param projectDTO the DTO of project to create.
     * @exception NullValueException If the DTO of project is null or the id of the employee is null.
     * @exception ObjectNotExistsException If the employee is not in the repository.
     * @return the created project's DTO.
     */
    @Override
    public ProjectDTO addProject(ProjectDTO projectDTO) throws NullValueException, ObjectNotExistsException {
        validateProjectDTONotNull(projectDTO);
        validateEmployees(projectDTO.getEmployeeDTOs());
        return saveToRepository(projectDTO);
    }

    /**
     * Update the project with the specific id and project DTO in the repository.
     * 
     * @param id the id of the project to update.
     * @param projectDTO the project's DTO to update.
     * @exception NullValueException If the id of the project/employee is null or the project's DTO is null.
     * @exception ObjectNotExistsException If the project/employee is not in the repository.
     * @return the updated project's DTO.
     */
    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO)
            throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        validateProjectDTONotNull(projectDTO);
        validateProjectExists(id);
        validateEmployees(projectDTO.getEmployeeDTOs());
        projectRepository.deleteProjectEmployees(id);
        return updateToRepository(id, projectDTO);
    }

    /**
     * Delete the Project with the specific id in the repository.
     * 
     * @param id the id of the Project to delete.
     * @exception NullValueException       If the id of the Project is null.
     * @exception ObjectNotExistsException If the Project is not in the repository.
     */
    @Override
    public void deleteProject(Long id) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        validateProjectExists(id);
        projectRepository.deleteProjectEmployees(id);
        projectRepository.deleteById(id);
    }

    /**
     * Validate the specific id is not null.
     * 
     * @param id the id of the project to validate.
     * @exception NullValueException If the id of the project is null.
     */
    private void validateIdNotNull(Long id) throws NullValueException {
        if (id == null) {
            throw new NullValueException(PROJECT_SIMPLE_NAME + "'s id");
        }
    }

    /**
     * Validate the specific Project is not null.
     * 
     * @param Project the Project to validate.
     * @exception NullValueException If the Project is null.
     */
    private void validateProjectDTONotNull(ProjectDTO projectDTO) throws NullValueException {
        if (projectDTO == null) {
            throw new NullValueException(PROJECT_SIMPLE_NAME);
        }
    }

    /**
     * Validate the Project with the specific id is in the repository.
     * 
     * @param id the id of the Project to validate.
     * @exception ObjectNotExistsException If the Project is not in the repository.
     */
    private void validateProjectExists(Long id) throws ObjectNotExistsException {
        if (projectRepository.findById(id).isEmpty()) {
            throw new ObjectNotExistsException(PROJECT_SIMPLE_NAME, "" + id);
        }
    }

    /**
     * Create a project DTO from the specific project.
     * 
     * @param project the project to create project's DTO.
     * @return the DTO of the specific project.
     * @exception NullValueException If the Project is null.
     */
    private ProjectDTO convertToDTO(Project project) throws NullValueException {
        validateProjectNotNull(project);
        Long projectId = project.getId();
        List<ProjectEmployeeDTO> employeeDTOs = projectRepository.getProjectEmployeeDTOs(projectId);
        List<ProjectCompanyDTO> companyDTOs = projectRepository.getProjectCompanyDTOs(projectId);
        return new ProjectDTO(projectId, project.getProjectName(), project.getStartDate(), project.getCompletionDate(),
                project.getBudget(), project.getProgress(), companyDTOs, employeeDTOs);
    }

    /**
     * Create a project from the specific project's DTO.
     * 
     * @param projectDTO the project's DTO to create project.
     * @return the project of specific project's DTO.
     */
    private Project convertToEntity(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setProjectName(projectDTO.getProjectName());
        project.setStartDate(projectDTO.getStartDate());
        project.setCompletionDate(projectDTO.getCompletionDate());
        project.setBudget(projectDTO.getBudget());
        project.setProgress(projectDTO.getProgress());
        return project;
    }

    /**
     * Save a project from the specific project's DTO into the repository.
     * 
     * @param projectDTO the project's DTO to save project.
     * @return the project's DTO of specific project.
     */
    private ProjectDTO saveToRepository(ProjectDTO projectDTO) {
        Project project = projectRepository.save(convertToEntity(projectDTO));
        return addProjectEmployees(project.getId(), projectDTO);
    }

    /**
     * Update a project from the specific project's DTO in the repository.
     * 
     * @param id         the id of the project.
     * @param projectDTO the project's DTO to update project.
     * @return the project's DTO of specific project.
     * @exception NullValueException If the Project is null.
     */
    private ProjectDTO updateToRepository(Long id, ProjectDTO projectDTO) throws NullValueException {
        validateProjectDTONotNull(projectDTO);
        Project project = convertToEntity(projectDTO);
        projectRepository.updateProject(id, project.getVersion(), project.getProjectName(), project.getStartDate(),
                project.getCompletionDate(), project.getBudget(), project.getProgress());
        return addProjectEmployees(id, projectDTO);
    }

    /**
     * Add the project emplloyees from the specific project's DTO into the
     * repository.
     * 
     * @param id         the id of the project.
     * @param projectDTO the project's DTO to save project.
     * @return the project's DTO of specific project.
     * @exception NullValueException If the Project is null.
     */
    private ProjectDTO addProjectEmployees(Long id, ProjectDTO projectDTO) throws NullValueException  {
        validateProjectDTONotNull(projectDTO);
        List<ProjectEmployeeDTO> employeeDTOs = projectDTO.getEmployeeDTOs();
        for (ProjectEmployeeDTO employeeDTO : employeeDTOs) {
            projectRepository.addProjectEmployee(id, employeeDTO.getWorkPermitNumber());
        }
        return convertToDTO(projectRepository.findById(id).get());
    }

    /**
     * Validate the employees is in the repository.
     * 
     * @param employeeDTOs the DTOs of the employees to validate.
     * @exception NullValueException       If the given id is null.
     * @exception ObjectNotExistsException If the employee is not in the repository.
     */
    private void validateEmployees(List<ProjectEmployeeDTO> employeeDTOs)
            throws NullValueException, ObjectNotExistsException {
        for (ProjectEmployeeDTO employeeDTO : employeeDTOs) {
            validateEmployeeId(employeeDTO.getWorkPermitNumber());
        }
    }

    /**
     * Validate the employee with the specific id is in the repository.
     * 
     * @param employeeId the id of the employee to validate.
     * @exception NullValueException       If the given id is null.
     * @exception ObjectNotExistsException If the employee is not in the repository.
     */
    private void validateEmployeeId(String employeeId) throws NullValueException, ObjectNotExistsException {
        employeeService.getEmployee(employeeId);
    }

    /**
     * Validate the company with the specific id is in the repository.
     * 
     * @param companyId the id of the company to validate.
     * @exception NullValueException       If the given id is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     */
    private void validateCompanyId(String companyId) throws NullValueException, ObjectNotExistsException {
        companyService.getCompany(companyId);
    }


    /**
     * Validate the project is not null.
     * 
     * @param project the id of the company to validate.
     * @exception NullValueException If the given id is null.
     */
    private void validateProjectNotNull(Project project) throws NullValueException {
        if (project == null) {
            throw new NullValueException(PROJECT_SIMPLE_NAME);
        }
    }
}