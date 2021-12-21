package com.kaizen.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import com.kaizen.model.TestArt;
import com.kaizen.model.TestCompany;
import com.kaizen.model.TestEmployee;
import com.kaizen.model.entity.Art;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;
import com.kaizen.security.jwt.JwtConfiguration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

/**
 * {@code ArtRepositoryTest} is a test class to do integration testing from
 * {@link ArtRepository} using H2 embeded database.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@Import(JwtConfiguration.class) // For jwtConfiguration in KaizenApplication.java
@DataJpaTest
@Transactional
public class ArtRepositoryTest {
    /**
     * The test entity manager used for testing.
     */
    @Autowired
    private TestEntityManager testEntityManager;

    /**
     * The art's repository used for testing.
     */
    @Autowired
    private ArtRepository artRepository;

    /**
     * {@code tearDown} is apply after each tests to clear database.
     */
    @AfterEach
    public void tearDown() {
        testEntityManager.clear();
        testEntityManager.flush();
    }

    /**
     * {@code findLatestResultsforAllEmployee_Found_ReturnFound} is a test on
     * {@link ArtRepository#findLatestResultsforAllEmployee()} to verify if the
     * method will find the latest results for all employee in the repository.
     */
    @Test
    public void findLatestResultsforAllEmployee_Found_ReturnFound() {
        Art artOld = TestArt.createArt();
        Art artNew = TestArt.createArt();
        artNew.setDateOfTest(LocalDate.now());
        Company company = TestCompany.createCompany();
        Employee employee = TestEmployee.createEmployee();
        company = testEntityManager.merge(company);
        employee = testEntityManager.merge(employee);
        artOld = testEntityManager.merge(artOld);
        artOld = testEntityManager.merge(artOld);
        artNew = testEntityManager.merge(artNew);
        testEntityManager.flush();

        List<Art> found = artRepository.findLatestResultsforAllEmployee();

        assertEquals(1, found.size());
        assertEquals(artNew.getId(), found.get(0).getId());
    }
}