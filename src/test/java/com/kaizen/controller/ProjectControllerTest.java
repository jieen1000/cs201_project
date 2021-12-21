package com.kaizen.controller;

import static org.mockito.Mockito.*;

import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.*;
import com.kaizen.model.dto.ProjectDTO;
import com.kaizen.model.entity.Project;
import com.kaizen.service.project.ProjectService;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * {@code ProjectControllerTest} is a test class to do unit testing on
 * {@link ProjectController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-27
 */
@ContextConfiguration(classes = { ProjectController.class })
@ExtendWith(SpringExtension.class)
class ProjectControllerTest {
    /**
     * The project's controller used for testing.
     */
    @Autowired
    private ProjectController projectController;

    /**
     * The mocked project's service used for testing.
     */
    @MockBean
    private ProjectService projectService;

    /**
     * {@code getProjects_Found_ExpectOKFound} is a test on
     * {@link ProjectController#getProjects()} to verify if the method will call
     * {@link ProjectService#listProjects()} and return the list of all projects
     * with Http Status Ok(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getProjects_Found_ExpectOKFound() throws Exception {
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        when(projectService.listProjects()).thenReturn(projectDTOs);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestProject.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(projectDTOs)));

        verify(projectService).listProjects();
    }

    /**
     * {@code getCompanyProjects_CompanyNotFound_ExpectNotFound} is a test on
     * {@link ProjectController#getCompanyProjects(String)} to verify if the method
     * will call {@link ProjectService#getCompanyProjects(String)} and return Http
     * Status Not FOund(404) when the company with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getCompanyProjects_CompanyNotFound_ExpectNotFound() throws Exception {
        String companyId = TestCompany.TEST_ID;
        when(projectService.getCompanyProjects(companyId)).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestProject.URL_EXTENSION)
                .param(TestProject.COMPANY_ID_KEY, companyId);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(projectService).getCompanyProjects(companyId);
    }

    /**
     * {@code getCompanyProjects_CompanyNotFound_ExpectNotFound} is a test on
     * {@link ProjectController#getCompanyProjects(String)} to verify if the method
     * will call {@link ProjectService#getCompanyProjects(String)} and return the
     * list of all projects with Http Status Ok(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getCompanyProjects_Found_ExpectOKFound() throws Exception {
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        String companyId = TestCompany.TEST_ID;
        when(projectService.getCompanyProjects(companyId)).thenReturn(projectDTOs);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestProject.URL_EXTENSION)
                .param(TestProject.COMPANY_ID_KEY, companyId);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(projectDTOs)));

        verify(projectService).getCompanyProjects(companyId);
    }

    /**
     * {@code addProject_MissingProjectDTO_ExpectBadRequest} is a test on
     * {@link ProjectController#createProject(ProjectDTO)} to verify if the method
     * will return Http Status Bad Request(400) when the specific project DTO is
     * missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addProject_MissingProjectDTO_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestProject.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code addProject_InvalidProjectDTO_ExpectBadRequest} is a test on
     * {@link ProjectController#createProject(ProjectDTO)} to verify if the method
     * will return Http Status Bad Request(400) when the specific employee is
     * invalid in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addProject_InvalidProjectDTO_ExpectBadRequest() throws Exception {
        ProjectDTO projectDTO = TestProject.createInvalidProjectDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestProject.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(projectDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code addProject_EmployeeNotFound_ExpectNotFound} is a test on
     * {@link ProjectController#createProject(ProjectDTO))} to verify if the method
     * will call {@link ProjectService#addProject(Project)} and return Http Status
     * Not Found(404) when the employee with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addProject_EmployeeNotFound_ExpectNotFound() throws Exception {
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestProject.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(projectDTO)).contentType(MediaType.APPLICATION_JSON);
        when(projectService.addProject(any(ProjectDTO.class))).thenThrow(new ObjectNotExistsException());

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(projectService).addProject(any(ProjectDTO.class));
    }

    /**
     * {@code addProject_New_ExpectCreatedSaved} is a test on
     * {@link ProjectController#createProject(ProjectDTO))} to verify if the method
     * will call {@link ProjectService#addProject(Project)} and return the created
     * specific project with Http Status Created(201) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addProject_New_ExpectCreatedSaved() throws Exception {
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestProject.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(projectDTO)).contentType(MediaType.APPLICATION_JSON);
        when(projectService.addProject(any(ProjectDTO.class))).thenReturn(projectDTO);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(projectDTO)));

        verify(projectService).addProject(any(ProjectDTO.class));
    }

    /**
     * {@code updateProject_MissingProject_ExpectBadRequest} is a test on
     * {@link ProjectController#updateProject(Long, Project)} to verify if the
     * method will return Http Status Bad Request(400) when the specific project is
     * missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateProject_MissingProject_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestProject.URL_EXTENSION + TestProject.TEST_ID);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateProject_InvalidProject_ExpectBadRequest} is a test on
     * {@link ProjectController#updateProject(Long, Project)} to verify if the
     * method will return Http Status Bad Request(400) when the specific project is
     * invalid in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateProject_InvalidProject_ExpectBadRequest() throws Exception {
        ProjectDTO projectDTO = TestProject.createInvalidProjectDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestProject.URL_EXTENSION + projectDTO.getId())
                .content(TestJsonConverter.writeValueAsString(projectDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateProject_NotFound_ExpectNotFound} is a test on
     * {@link ProjectController#updateProject(Long, Project)} to verify if the
     * method will call {@link ProjectService#updateProject(Long, Project)} and
     * return Http Status Not Found(404) when the project/employee with the specific
     * id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateProject_NotFound_ExpectNotFound() throws Exception {
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        when(projectService.updateProject(projectDTO.getId(), projectDTO)).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestProject.URL_EXTENSION + projectDTO.getId())
                .content(TestJsonConverter.writeValueAsString(projectDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(projectService).updateProject(projectDTO.getId(), projectDTO);
    }

    /**
     * {@code updateProject_Updated_ExpectOKUpdated} is a test on
     * {@link ProjectController#updateProject(Long, Project)} to verify if the
     * method will call {@link ProjectService#updateProject(Long, Project)} and
     * return the updated specific project with Http Status OK(200) and content type
     * of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateProject_Updated_ExpectOKUpdated() throws Exception {
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        when(projectService.updateProject(projectDTO.getId(), projectDTO)).thenReturn(projectDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestProject.URL_EXTENSION + projectDTO.getId())
                .content(TestJsonConverter.writeValueAsString(projectDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(projectDTO)));

        verify(projectService).updateProject(projectDTO.getId(), projectDTO);
    }

    /**
     * {@code deleteProject_NotFound_ExpectNotFound} is a test on
     * {@link ProjectController#deleteProject(String)} to verify if the method will
     * call {@link ProjectService#deleteProject(String)} and return Http Status Not
     * Found(404) when the project with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteProject_NotFound_ExpectNotFound() throws Exception {
        doThrow(new ObjectNotExistsException()).when(projectService).deleteProject(TestProject.TEST_ID);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestProject.URL_EXTENSION + TestProject.TEST_ID);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(projectService).deleteProject(TestProject.TEST_ID);
    }

    /**
     * {@code deleteProject_Deleted_ExpectOK} is a test on
     * {@link ProjectController#deleteProject(String)} to verify if the method will
     * call {@link ProjectService#deleteProject(String)} and return Http Status
     * OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteProject_Deleted_ExpectOK() throws Exception {
        doNothing().when(projectService).deleteProject(TestProject.TEST_ID);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestProject.URL_EXTENSION + TestProject.TEST_ID);

        MockMvcBuilders.standaloneSetup(projectController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(projectService).deleteProject(TestProject.TEST_ID);
    }
}
