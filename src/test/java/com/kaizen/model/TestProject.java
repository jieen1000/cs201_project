package com.kaizen.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kaizen.model.dto.ProjectCompanyDTO;
import com.kaizen.model.dto.ProjectDTO;
import com.kaizen.model.dto.ProjectEmployeeDTO;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;
import com.kaizen.model.entity.Project;

/**
 * {@code TestProject} is a mock class that stored configurations needed to do
 * testing related to {@link Project} and {@link ProjectController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-27
 */
public class TestProject {
    /**
     * Represents the project's id used for testing.
     */
    public final static Long TEST_ID = 0L;

    /**
     * Represents the URL extension of the project's API endpoint.
     */
    public final static String URL_EXTENSION = "/api/projects/";

    /**
     * Represents the company's id key that used in project's API call.
     */
    public final static String COMPANY_ID_KEY = "companyId";

    /**
     * Create a project with necessary fields filled to use for testing.
     * 
     * @return the project to use for testing.
     */
    public static Project createProject() {
        Project project = new Project();
        project.setId(TEST_ID);
        project.setProjectName("Project 101");
        project.setStartDate(LocalDate.now());
        Set<Employee> employees = new HashSet<>();
        Employee employee = TestEmployee.createEmployee();
        employee.setCompany(TestCompany.createCompany());
        employees.add(employee);
        project.setEmployees(employees);
        return project;
    }

    /**
     * Create a project DTO with necessary fields filled to use for testing.
     * 
     * @return the project DTO to use for testing.
     */
    public static ProjectDTO createProjectDTO() {
        ProjectDTO projectDTO = new ProjectDTO();
        Project project = createProject();
        projectDTO.setId(project.getId());
        projectDTO.setProjectName(project.getProjectName());
        projectDTO.setStartDate(project.getStartDate());
        projectDTO.setEmployeeDTOs(createProjectEmmployeeDTOs());
        projectDTO.setCompanyDTOs(createProjectCompanyDTOs());
        return projectDTO;
    }

    /**
     * Create a list of project employee DTOs with necessary fields filled to use
     * for testing.
     * 
     * @return the list of project employee DTOs to use for testing.
     */
    public static List<ProjectEmployeeDTO> createProjectEmmployeeDTOs() {
        Project project = createProject();
        List<ProjectEmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee employee : project.getEmployees()) {
            employeeDTOs.add(new ProjectEmployeeDTO(employee.getWorkPermitNumber(), employee.getName(), null));
        }
        return employeeDTOs;
    }

    /**
     * Create a list of project company DTOs with necessary fields filled to use for
     * testing.
     * 
     * @return the list of project company DTOs to use for testing.
     */
    public static List<ProjectCompanyDTO> createProjectCompanyDTOs() {
        Project project = createProject();
        List<ProjectCompanyDTO> companyDTOs = new ArrayList<>();
        for (Employee employee : project.getEmployees()) {
            Company company = employee.getCompany();
            ProjectCompanyDTO projectCompanyDTO = new ProjectCompanyDTO(company.getUEN(), company.getName());
            if (!companyDTOs.contains(projectCompanyDTO)) {
                companyDTOs.add(projectCompanyDTO);
            }
        }
        return companyDTOs;
    }

    /**
     * Create an invalid project DTO with necessary fields filled to use for
     * testing.
     * 
     * @return the invalid project DTO to use for testing.
     */
    public static ProjectDTO createInvalidProjectDTO() {
        ProjectDTO projectDTO = createProjectDTO();
        projectDTO.setProjectName(null);
        return projectDTO;
    }
}
