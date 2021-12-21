package com.kaizen.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import com.kaizen.model.TestCompany;
import com.kaizen.model.TestEmployee;
import com.kaizen.model.TestProject;
import com.kaizen.model.dto.ProjectCompanyDTO;
import com.kaizen.model.dto.ProjectEmployeeDTO;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;
import com.kaizen.model.entity.Project;
import com.kaizen.security.jwt.JwtConfiguration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

/**
 * {@code ProjectRepositoryTest} is a test class to do integration testing from
 * {@link ProjectRepository} using H2 embeded database.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-27
 */
@Import(JwtConfiguration.class) // For jwtConfiguration in KaizenApplication.java
@DataJpaTest
@Transactional
public class ProjectRepositoryTest {
    /**
     * The test entity manager used for testing.
     */
    @Autowired
    private TestEntityManager testEntityManager;

    /**
     * The project's repository used for testing.
     */
    @Autowired
    private ProjectRepository projectRepository;

    /**
     * {@code tearDown} is apply after each tests to clear database.
     */
    @AfterEach
    public void tearDown() {
        testEntityManager.clear();
        testEntityManager.flush();
    }

    /**
     * {@code addProjectEmployee_Updated_Success} is a test on
     * {@link ProjectRepository#addProjectEmployee(Long, String)} to verify if the
     * method will add mapping of a project and employee in the repository.
     */
    @Test
    public void addProjectEmployee_Updated_Success() {
        Company company = TestCompany.createCompany();
        company = testEntityManager.merge(company);
        Employee employee = TestEmployee.createEmployee();
        employee.setCompany(company);
        employee = testEntityManager.merge(employee);
        Project project = TestProject.createProject();
        project.setEmployees(null);
        project = testEntityManager.merge(project);
        testEntityManager.flush();

        projectRepository.addProjectEmployee(project.getId(), employee.getWorkPermitNumber());
        List<ProjectEmployeeDTO> found = projectRepository.getProjectEmployeeDTOs(project.getId());

        assertEquals(employee.getWorkPermitNumber(), found.get(0).getWorkPermitNumber());
    }

    /**
     * {@code updateProject_Updated_Success} is a test on
     * {@link ProjectRepository#updateProject(Long, int, String, java.time.LocalDate, java.time.LocalDate, String, double)}
     * to verify if the method will update the employee in the repository.
     */
    @Test
    public void updateProject_Updated_Success() {
        Project project = TestProject.createProject();
        project.setEmployees(null);
        project = testEntityManager.merge(project);
        testEntityManager.flush();
        project.setBudget("10M");

        projectRepository.updateProject(project.getId(), project.getVersion(), project.getProjectName(),
                project.getStartDate(), project.getCompletionDate(), project.getBudget(), project.getProgress());
        Project updated = testEntityManager.find(Project.class, project.getId());

        assertEquals(project, updated);
    }

    /**
     * {@code deleteProjectEmployees_Deleted_Success} is a test on
     * {@link ProjectRepository#delete(Project)} to verify if the method will delete
     * the mappigng of project and its employeee in the repository.
     */
    @Test
    public void deleteProjectEmployees_Deleted_Success() {
        Company company = TestCompany.createCompany();
        company = testEntityManager.merge(company);
        Employee employee = TestEmployee.createEmployee();
        employee.setCompany(company);
        employee = testEntityManager.merge(employee);
        Project project = TestProject.createProject();
        project.setEmployees(null);
        project = testEntityManager.merge(project);
        testEntityManager.flush();
        projectRepository.addProjectEmployee(project.getId(), employee.getWorkPermitNumber());

        projectRepository.deleteProjectEmployees(project.getId());
        List<ProjectEmployeeDTO> found = projectRepository.getProjectEmployeeDTOs(project.getId());

        assertEquals(found.size(), 0);
    }

    /**
     * {@code getProjectIds_Found_Success} is a test on
     * {@link ProjectRepository#getProjectIds(String)} to verify if the method will
     * return list of project's ids that the company mapped to through its employee
     * in the repository.
     */
    @Test
    public void getProjectIds_Found_Success() {
        Company company = TestCompany.createCompany();
        company = testEntityManager.merge(company);
        Employee employee = TestEmployee.createEmployee();
        employee.setCompany(company);
        employee = testEntityManager.merge(employee);
        Project project = TestProject.createProject();
        project.setEmployees(null);
        project = testEntityManager.merge(project);
        testEntityManager.flush();
        projectRepository.addProjectEmployee(project.getId(), employee.getWorkPermitNumber());

        List<Long> found = projectRepository.getProjectIds(company.getUEN());

        for (Long id : found) {
            assertEquals(project.getId(), id);
        }
    }

    /**
     * {@code getProjectEmployeeDTOs_Found_Success} is a test on
     * {@link ProjectRepository#getProjectEmployeeDTOs(Long)} to verify if the
     * method will return list of project's employee DTOs that the project mapped to
     * in the repository.
     */
    @Test
    public void getProjectEmployeeDTOs_Found_Success() {
        Company company = TestCompany.createCompany();
        company = testEntityManager.merge(company);
        Employee employee = TestEmployee.createEmployee();
        employee.setCompany(company);
        employee = testEntityManager.merge(employee);
        Project project = TestProject.createProject();
        project.setEmployees(null);
        project = testEntityManager.merge(project);
        testEntityManager.flush();
        projectRepository.addProjectEmployee(project.getId(), employee.getWorkPermitNumber());

        List<ProjectEmployeeDTO> found = projectRepository.getProjectEmployeeDTOs(project.getId());

        for (ProjectEmployeeDTO projectEmployeeDTO : found) {
            assertEquals(employee.getWorkPermitNumber(), projectEmployeeDTO.getWorkPermitNumber());
        }
    }

    /**
     * {@code getProjectCompanyDTOs_Found_Success} is a test on
     * {@link ProjectRepository#getProjectCompanyDTOs(Long)} to verify if the method
     * will return list of project's company DTOs that the project mapped to in the
     * repository.
     */
    @Test
    public void getProjectCompanyDTOs_Found_Success() {
        Company company = TestCompany.createCompany();
        company = testEntityManager.merge(company);
        Employee employee = TestEmployee.createEmployee();
        employee.setCompany(company);
        employee = testEntityManager.merge(employee);
        Project project = TestProject.createProject();
        project.setEmployees(null);
        project = testEntityManager.merge(project);
        testEntityManager.flush();
        projectRepository.addProjectEmployee(project.getId(), employee.getWorkPermitNumber());

        List<ProjectCompanyDTO> found = projectRepository.getProjectCompanyDTOs(project.getId());

        for (ProjectCompanyDTO projectCompanyDTO : found) {
            assertEquals(company.getUEN(), projectCompanyDTO.getUen());
        }
    }
}