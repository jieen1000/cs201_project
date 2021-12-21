package com.kaizen.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import com.kaizen.model.TestCompany;
import com.kaizen.model.TestEmployee;
import com.kaizen.model.TestEmployeeSkill;
import com.kaizen.model.TestSkill;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;
import com.kaizen.model.entity.EmployeeSkill;
import com.kaizen.model.entity.Skill;
import com.kaizen.security.jwt.JwtConfiguration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

/**
 * {@code EmployeeSkillRepositoryTest} is a test class to do integration testing from
 * {@link EmployeeSkillRepository} using H2 embeded database.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@Import(JwtConfiguration.class) // For jwtConfiguration in KaizenApplication.java
@DataJpaTest
@Transactional
public class EmployeeSkillRepositoryTest {
    /**
     * The test entity manager used for testing.
     */
    @Autowired
    private TestEntityManager testEntityManager;

    /**
     * The employeeSkill's repository used for testing.
     */
    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    /**
     * {@code tearDown} is apply after each tests to clear database.
     */
    @AfterEach
    public void tearDown() {
        testEntityManager.clear();
        testEntityManager.flush();
    }

    /**
     * {@code findEmployeeSkillCountAndMinCost_Found_ReturnFound} is a test on
     * {@link EmployeeSkillRepository#findLatestResultsforAllEmployee()} to verify
     * if the method will find the latest results for all employee in the
     * repository.
     */
    @Test
    public void findEmployeeSkillCountAndMinCost_Found_ReturnFound() {
        Company company = TestCompany.createCompany();
        company = testEntityManager.merge(company);
        Employee employee = TestEmployee.createEmployee();
        employee = testEntityManager.merge(employee);
        Skill skill = TestSkill.createSkill();
        skill = testEntityManager.merge(skill);
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        employeeSkill.setCost(1.0);
        EmployeeSkill employeeSkillMin = TestEmployeeSkill.createEmployeeSkill();
        employeeSkill.setCost(0.5);
        employeeSkill = testEntityManager.merge(employeeSkill);
        employeeSkillMin = testEntityManager.merge(employeeSkillMin);
        testEntityManager.flush();

        List<List<String>> found = employeeSkillRepository.findEmployeeSkillCountAndMinCost(company.getUEN().substring(1));

        assertEquals(1, found.size());
        assertEquals(3, found.get(0).size());
        assertEquals(employeeSkillMin.getSkill().getSkill(), found.get(0).get(0));
        assertEquals("1", found.get(0).get(1));
        assertEquals(employeeSkillMin.getCost(), Double.parseDouble(found.get(0).get(2)));
    }
}