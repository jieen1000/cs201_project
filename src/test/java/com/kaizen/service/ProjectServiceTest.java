package com.kaizen.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.kaizen.exceptions.*;
import com.kaizen.model.TestCompany;
import com.kaizen.model.TestEmployee;
import com.kaizen.model.TestProject;
import com.kaizen.model.dto.ProjectCompanyDTO;
import com.kaizen.model.dto.ProjectDTO;
import com.kaizen.model.dto.ProjectEmployeeDTO;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Project;
import com.kaizen.repository.ProjectRepository;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.employee.EmployeeService;
import com.kaizen.service.project.ProjectService;
import com.kaizen.service.project.ProjectServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code ProjectServiceTest} is a test class to do unit testing on
 * {@link ProjectService} using {@link ProjectServiceImpl}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@ContextConfiguration(classes = { ProjectServiceImpl.class })
@ExtendWith(SpringExtension.class)
public class ProjectServiceTest {
    /**
     * The mocked project's repository used for testing.
     */
    @MockBean
    private ProjectRepository projectRepository;

    /**
     * The mocked company's service used for testing.
     */
    @MockBean
    private CompanyService companyService;

    /**
     * The mocked employee's service used for testing.
     */
    @MockBean
    private EmployeeService employeeService;

    /**
     * The project's service used for testing.
     */
    @Autowired
    private ProjectService projectService;

    /**
     * {@code getCompanyProjects_FoundNull_ThrowNullValueException} is a test on
     * {@link ProjectService#getCompanyProjects(String)} to verify if the method
     * will call {@link CompanyService#getCompany(String)} and throw
     * {@link ObjectNotExistsException} when the project is null in the
     * repository.
     */
    @Test
    void listProjects_FoundNull_ThrowNullValueException() {
        List<Project> projects = new ArrayList<>();
        projects.add(null);
        when(projectRepository.findAll()).thenReturn(projects);

        assertThrows(NullValueException.class, () -> {
            projectService.listProjects();
        });

        verify(projectRepository).findAll();
    }

    /**
     * {@code listProjects_Found_ReturnFound} is a test on
     * {@link ProjectService#listCompanies()} to verify if the method will call
     * {@link ProjectRepository#findAll()},
     * {@link ProjectRepository#getProjectEmployeeDTOs(Long)} and
     * {@link ProjectRepository#getProjectCompanyDTOs(Long)} and return the list of
     * all projects.
     */
    @Test
    void listProjects_Found_ReturnFound() {
        List<Project> projects = new ArrayList<>();
        projects.add(TestProject.createProject());
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        projectDTOs.add(TestProject.createProjectDTO());
        when(projectRepository.findAll()).thenReturn(projects);
        when(projectRepository.getProjectEmployeeDTOs(any(Long.class))).thenReturn(new ArrayList<ProjectEmployeeDTO>(projectDTOs.get(0).getEmployeeDTOs()));
        when(projectRepository.getProjectCompanyDTOs(any(Long.class))).thenReturn(
                new ArrayList<ProjectCompanyDTO>(projectDTOs.get(0).getCompanyDTOs()));

        List<ProjectDTO> foundProjectDTOs = projectService.listProjects();

        assertEquals(projectDTOs, foundProjectDTOs);
        verify(projectRepository).findAll();
        verify(projectRepository).getProjectEmployeeDTOs(any(Long.class));
        verify(projectRepository).getProjectCompanyDTOs(any(Long.class));
    }

    /**
     * {@code getCompanyProjects_Null_ThrowNullValueException} is a test on
     * {@link ProjectService#getCompanyProjects(String)} to verify if the method
     * will call {@link CompanyService#getCompany(String)} and throw
     * {@link NullValueException} when the specific company is null.
     */
    @Test
    void getCompanyProjects_Null_ThrowNullValueException() {
        when(companyService.getCompany(null)).thenThrow(new NullValueException());

        assertThrows(NullValueException.class, () -> {
            projectService.getCompanyProjects(null);
        });

        verify(companyService).getCompany(null);
    }

    /**
     * {@code getCompanyProjects_Null_ThrowNullValueException} is a test on
     * {@link ProjectService#getCompanyProjects(String)} to verify if the method
     * will call {@link CompanyService#getCompany(String)} and throw
     * {@link ObjectNotExistsException} when the company is missing in the
     * repository.
     */
    @Test
    void getCompanyProjects_CompanyNotFound_ThrowObjectNotExistsException() {
        when(companyService.getCompany(null)).thenThrow(new ObjectNotExistsException());

        assertThrows(ObjectNotExistsException.class, () -> {
            projectService.getCompanyProjects(null);
        });

        verify(companyService).getCompany(null);
    }

    /**
     * {@code getCompanyProjects_FoundNull_ThrowNullValueException} is a test on
     * {@link ProjectService#getCompanyProjects(String)} to verify if the method
     * will call {@link CompanyService#getCompany(String)},
     * {@link ProjectRepository#getProjectIds(String)} and
     * {@link ProjectRepository#getById(Long)} and throw
     * {@link ObjectNotExistsException} when the project is null in the
     * repository.
     */
    @Test
    void getCompanyProjects_FoundNull_ThrowNullValueException() {
        Company company = TestCompany.createCompany();
        String companyId = company.getUEN();
        List<Long> projectIds = new ArrayList<>();
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        projectIds.add(projectDTO.getId());
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        projectDTOs.add(projectDTO);
        when(companyService.getCompany(companyId)).thenReturn(company);
        when(projectRepository.getProjectIds(companyId)).thenReturn(projectIds);
        when(projectRepository.getById(projectDTO.getId())).thenReturn(null);

        assertThrows(NullValueException.class, () -> {
            projectService.getCompanyProjects(companyId);
        });

        verify(companyService).getCompany(companyId);
        verify(projectRepository).getProjectIds(companyId);
        verify(projectRepository).getById(projectDTO.getId());
    }

    /**
     * {@code getCompanyProjects_Found_ReturnFound} is a test on
     * {@link ProjectService#getCompanyProjects(String)} to verify if the method
     * will call {@link CompanyService#getCompany(String)},
     * {@link ProjectRepository#getProjectIds(String)},
     * {@link ProjectRepository#getById(Long)},
     * {@link ProjectRepository#getProjectCompanyDTOs(Long)},
     * {@link ProjectRepository#getProjectEmployeeDTOs(Long)} and return the list of
     * all projects.
     */
    @Test
    void getCompanyProjects_Found_ReturnFound() {
        Company company = TestCompany.createCompany();
        String companyId = company.getUEN();
        List<Long> projectIds = new ArrayList<>();
        Project project = TestProject.createProject();
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        projectIds.add(projectDTO.getId());
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        projectDTOs.add(projectDTO);
        when(companyService.getCompany(companyId)).thenReturn(company);
        when(projectRepository.getProjectIds(companyId)).thenReturn(projectIds);
        when(projectRepository.getById(projectDTO.getId())).thenReturn(project);
        when(projectRepository.getProjectCompanyDTOs(project.getId()))
                .thenReturn(TestProject.createProjectCompanyDTOs());
        when(projectRepository.getProjectEmployeeDTOs(project.getId()))
                .thenReturn(TestProject.createProjectEmmployeeDTOs());

        List<ProjectDTO> foundProjectDTOs = projectService.getCompanyProjects(companyId);

        assertEquals(projectDTOs, foundProjectDTOs);
        verify(companyService).getCompany(companyId);
        verify(projectRepository).getProjectIds(companyId);
        verify(projectRepository).getById(projectDTO.getId());
        verify(projectRepository).getProjectCompanyDTOs(project.getId());
        verify(projectRepository).getProjectEmployeeDTOs(project.getId());
    }

    /**
     * {@code addProject_Null_ThrowNullValueException} is a test on
     * {@link ProjectService#addProject(Project)} to verify if the method will throw
     * {@link NullValueException} when the specific project is null.
     */
    @Test
    void addProject_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            projectService.addProject(null);
        });
    }

    /**
     * {@code addProject_EmployeeNotFound_ThrowObjectNotExistsException} is a test
     * on {@link ProjectService#addProject(Project)} to verify if the method will
     * call {@link EmployeeService#getEmployee(String)} and throw
     * {@link ObjectNotExistsException} when the employee is missing in the
     * repository.
     */
    @Test
    void addProject_EmployeeNotFound_ThrowObjectNotExistsException() {
        when(employeeService.getEmployee(any(String.class))).thenThrow(new ObjectNotExistsException());

        assertThrows(ObjectNotExistsException.class, () -> {
            projectService.addProject(TestProject.createProjectDTO());
        });

        verify(employeeService).getEmployee(any(String.class));
    }

    /**
     * {@code addProject_New_ReturnSaved} is a test on
     * {@link ProjectService#addProject(Project)} to verify if the method will call
     * {@link EmployeeService#getEmployee(String)},{@link ProjectRepository#save(Project)},
     * {@link ProjectRepository#findById(Long)},
     * {@link ProjectRepository#getProjectCompanyDTOs(Long)},
     * {@link ProjectRepository#getProjectEmployeeDTOs(Long)} and save and return
     * the specific project.
     */
    @Test
    void addProject_New_ReturnSaved() {
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        Project project = TestProject.createProject();
        when(employeeService.getEmployee(any(String.class))).thenReturn(TestEmployee.createEmployee());
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(projectRepository.getProjectCompanyDTOs(project.getId()))
                .thenReturn(TestProject.createProjectCompanyDTOs());
        when(projectRepository.getProjectEmployeeDTOs(project.getId()))
                .thenReturn(TestProject.createProjectEmmployeeDTOs());

        ProjectDTO savedProjectDTO = projectService.addProject(projectDTO);

        assertEquals(projectDTO, savedProjectDTO);
        verify(employeeService).getEmployee(any(String.class));
        verify(projectRepository).save(any(Project.class));
        verify(projectRepository).findById(project.getId());
        verify(projectRepository).getProjectCompanyDTOs(project.getId());
        verify(projectRepository).getProjectEmployeeDTOs(project.getId());
    }

    /**
     * {@code updateProject_NullId_ThrowNullValueException} is a test on
     * {@link ProjectService#updateProject(Long, Project)} to verify if the method
     * will throw {@link NullValueException} when the specific id is null.
     */
    @Test
    void updateProject_NullId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            projectService.updateProject(null, TestProject.createProjectDTO());
        });
    }

    /**
     * {@code updateProject_NullUpdatedProject_ThrowNullValueException} is a test on
     * {@link ProjectService#updateProject(Long, Project)} to verify if the method
     * will throw {@link NullValueException} when the specific project DTO is null .
     */
    @Test
    void updateProject_NullUpdatedProjectDTO_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            projectService.updateProject(TestProject.TEST_ID, null);
        });
    }

    /**
     * {@code updateProject_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link ProjectService#updateProject(Long, Project)} to verify if the method
     * will call {@link ProjectRepository#findById(Long)} and throw
     * {@link ObjectNotExistsException} when the project with the specific id does
     * not exists.
     */
    @Test
    void updateProject_NotFound_ThrowObjectNotExistsException() {
        when(projectRepository.findById(TestProject.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            projectService.updateProject(TestProject.TEST_ID, TestProject.createProjectDTO());
        });

        verify(projectRepository).findById(TestProject.TEST_ID);
    }

    /**
     * {@code updateProject_EmployeeNotFound_ThrowObjectNotExistsException} is a
     * test on {@link ProjectService#updateProject(Project)} to verify if the method
     * will call {@link ProjectRepository#findById(Long)} and
     * {@link EmployeeService#getEmployee(String)} and throw
     * {@link ObjectNotExistsException} when the employee is missing in the
     * repository.
     */
    @Test
    void updateProject_EmployeeNotFound_ThrowObjectNotExistsException() {
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        when(projectRepository.findById(TestProject.TEST_ID)).thenReturn(Optional.of(TestProject.createProject()));
        when(employeeService.getEmployee(any(String.class))).thenThrow(new ObjectNotExistsException());

        assertThrows(ObjectNotExistsException.class, () -> {
            projectService.updateProject(projectDTO.getId(), projectDTO);
        });

        verify(projectRepository).findById(TestProject.TEST_ID);
        verify(employeeService).getEmployee(any(String.class));
    }

    /**
     * {@code updateProject_Updated_ReturnUpdated} is a test on
     * {@link ProjectService#updateProject(Long, Project)} to verify if the method
     * will call {@link ProjectRepository#findById(Long)},
     * {@link EmployeeService#getEmployee(String)},
     * {@link ProjectRepository#deleteProjectEmployees(Long)},
     * {@link ProjectRepository#save(Project)},
     * {@link ProjectRepository#getProjectCompanyDTOs(Long)} and
     * {@link ProjectRepository#getProjectEmployeeDTOs(Long)} and save and return
     * the specific project.
     */
    @Test
    void updateProject_Updated_ReturnUpdated() {
        Project project = TestProject.createProject();
        ProjectDTO projectDTO = TestProject.createProjectDTO();
        when(projectRepository.findById(project.getId())).thenReturn(Optional.of(project));
        when(employeeService.getEmployee(any(String.class))).thenReturn(TestEmployee.createEmployee());
        doNothing().when(projectRepository).deleteProjectEmployees(project.getId());
        doNothing().when(projectRepository).updateProject(project.getId(), project.getVersion(),
                project.getProjectName(), project.getStartDate(), project.getCompletionDate(), project.getBudget(),
                project.getProgress());
        when(projectRepository.getProjectCompanyDTOs(project.getId()))
                .thenReturn(TestProject.createProjectCompanyDTOs());
        when(projectRepository.getProjectEmployeeDTOs(project.getId()))
                .thenReturn(TestProject.createProjectEmmployeeDTOs());

        ProjectDTO updatedProjectDTO = projectService.updateProject(project.getId(), projectDTO);

        assertEquals(projectDTO, updatedProjectDTO);
        verify(projectRepository, times(2)).findById(project.getId());
        verify(employeeService).getEmployee(any(String.class));
        verify(projectRepository).deleteProjectEmployees(TestProject.TEST_ID);
        verify(projectRepository).updateProject(project.getId(), project.getVersion(), project.getProjectName(),
                project.getStartDate(), project.getCompletionDate(), project.getBudget(), project.getProgress());
        verify(projectRepository).getProjectCompanyDTOs(project.getId());
        verify(projectRepository).getProjectEmployeeDTOs(project.getId());
    }

    /**
     * {@code deleteProject_NullId_ThrowNullValueException} is a test on
     * {@link ProjectService#deleteProject(String)} to verify if the method will
     * throw {@link NullValueException} when the specific id is null.
     */
    @Test
    void deleteProject_NullId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            projectService.deleteProject(null);
        });
    }

    /**
     * {@code deleteProject_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link ProjectService#deleteProject(String)} to verify if the method will
     * call {@link ProjectRepository#findById(Long)} and throw
     * {@link ObjectNotExistsException} when the project with the specific id does
     * not exists.
     */
    @Test
    void deleteProject_NotFound_ThrowObjectNotExistsException() {
        when(projectRepository.findById(TestProject.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            projectService.deleteProject(TestProject.TEST_ID);
        });

        verify(projectRepository).findById(TestProject.TEST_ID);
    }

    /**
     * {@code deleteProject_Deleted} is a test on
     * {@link ProjectService#deleteProject(String)} to verify if the method will
     * call {@link ProjectRepository#findById(Long)},
     * {@link ProjectRepository#deleteProjectEmployees(Long)} and
     * {@link ProjectRepository#deleteById(String)} and delete the project with
     * specific id.
     */
    @Test
    void deleteProject_Deleted() {
        when(projectRepository.findById(TestProject.TEST_ID)).thenReturn(Optional.of(TestProject.createProject()));
        doNothing().when(projectRepository).deleteProjectEmployees(TestProject.TEST_ID);
        doNothing().when(projectRepository).deleteById(TestProject.TEST_ID);

        projectService.deleteProject(TestProject.TEST_ID);

        verify(projectRepository).findById(TestProject.TEST_ID);
        verify(projectRepository).deleteProjectEmployees(TestProject.TEST_ID);
        verify(projectRepository).deleteById(TestProject.TEST_ID);
    }
}
