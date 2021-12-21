package com.kaizen.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.kaizen.model.*;
import com.kaizen.model.dto.ProjectDTO;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;
import com.kaizen.model.entity.Project;
import com.kaizen.repository.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * {@code ProjectControllerIntegrationTest} is a test class to do integration
 * testing from {@link ProjectController} using H2 embeded database.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-27
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProjectControllerIntegrationTest {
    /**
     * The project's controller used for testing.
     */
    @Autowired
    private ProjectController projectController;

    /**
     * The project's repository used for testing.
     */
    @Autowired
    private ProjectRepository projectRepository;

    /**
     * The employee's repository used for testing.
     */
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * The company's repository used for testing.
     */
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * {@code tearDown} is apply after each tests to clear database
     */
    @AfterEach
    public void tearDown() {
        for (Project project : projectRepository.findAll()) {
            projectRepository.deleteProjectEmployees(project.getId());
        }
        projectRepository.deleteAll();
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    /**
     * {@code getProjects_Found_ExpectOKFound} is a test on
     * {@link ProjectController#getProjects()} to verify if the method will return
     * the list of all projects with Http Status Ok(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getProjects_Found_ExpectOKFound() throws Exception {
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestProject.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(projectDTOs)));
    }

    /**
     * {@code getCompanyProjects_CompanyNotFound_ExpectNotFound} is a test on
     * {@link ProjectController#getCompanyProjects(String)} to verify if the method
     * will return Http Status Not Found(404) when the company with the specific id
     * is not found
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getCompanyProjects_CompanyNotFound_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestProject.URL_EXTENSION)
                .param(TestProject.COMPANY_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code getCompanyProjects_Found_ExpectOKFound} is a test on
     * {@link ProjectController#getCompanyProjects(String)} to verify if the method
     * will return the list of all projects with Http Status Ok(200) and content
     * type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getCompanyProjects_Found_ExpectOKFound() throws Exception {
        Company company = TestCompany.createCompany();
        companyRepository.save(company);
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestProject.URL_EXTENSION)
                .param(TestProject.COMPANY_ID_KEY, company.getUEN());

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(projectDTOs)));
    }

    /**
     * {@code addProject_EmployeeNotFound_ExpectNotFound} is a test on
     * {@link ProjectController#createProject(ProjectDTO)} to verify if the method
     * will return Http Status Not Found(404) when the employee with the specific id
     * is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addProject_EmployeeNotFound_ExpectNotFound() throws Exception {
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestProject.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(projectDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code addProject_New_ExpectCreatedSaved} is a test on
     * {@link ProjectController#createProject(ProjectDTO))} to verify if the method
     * will return the created specific project with Http Status Created(201) and
     * content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addProject_New_ExpectCreatedSaved() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        employeeRepository.save(employee);
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestProject.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(projectDTO)).contentType(MediaType.APPLICATION_JSON);

        ResultActions ra = MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder);
        List<Project> projects = projectRepository.findAll();
        projectDTO.setId(projects.get(0).getId());

        ra.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(projectDTO)));
    }

    /**
     * {@code updateProject_NotFound_ExpectNotFound} is a test on
     * {@link ProjectController#updateProject(Long, Project)} to verify if the
     * method will return Http Status Not Found(404) when the project with the
     * specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateProject_NotFound_ExpectNotFound() throws Exception {
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestProject.URL_EXTENSION + projectDTO.getId())
                .content(TestJsonConverter.writeValueAsString(projectDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code updateProject_NotFound_ExpectNotFound} is a test on
     * {@link ProjectController#updateProject(Long, Project)} to verify if the
     * method will return Http Status Not Found(404) when the employee with the
     * specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateProject_EmployeeNotFound_ExpectNotFound() throws Exception {
        Project project = TestProject.createProject();
        project.setEmployees(null);
        project = projectRepository.save(project);
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestProject.URL_EXTENSION + projectDTO.getId())
                .content(TestJsonConverter.writeValueAsString(projectDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code updateProject_Updated_ExpectOKUpdated} is a test on
     * {@link ProjectController#updateProject(Long, Project)} to verify if the
     * method will return the updated specific project with Http Status OK(200) and
     * content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateProject_Updated_ExpectOKUpdated() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        employeeRepository.save(employee);
        Project project = TestProject.createProject();
        Set<Employee> employees = project.getEmployees();
        project.setEmployees(null);
        project = projectRepository.save(project);
        for (Employee e : employees) {
            projectRepository.addProjectEmployee(project.getId(), e.getWorkPermitNumber());
        }
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setBudget("10M");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestProject.URL_EXTENSION + projectDTO.getId())
                .content(TestJsonConverter.writeValueAsString(projectDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(projectController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(projectDTO)));
    }

    /**
     * {@code deleteProject_NotFound_ExpectNotFound} is a test on
     * {@link ProjectController#deleteProject(String)} to verify if the method will
     * return Http Status Not Found(404) when the project with the specific id is
     * not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteProject_NotFound_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestProject.URL_EXTENSION + TestProject.TEST_ID);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code deleteProject_Deleted_ExpectOK} is a test on
     * {@link ProjectController#deleteProject(String)} to verify if the method will
     * return Http Status OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteProject_Deleted_ExpectOK() throws Exception {
        Project project = TestProject.createProject();
        project.setEmployees(null);
        projectRepository.save(project);
        List<Project> projects = projectRepository.findAll();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestProject.URL_EXTENSION + projects.get(0).getId());

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}