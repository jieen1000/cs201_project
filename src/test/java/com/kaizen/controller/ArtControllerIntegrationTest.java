package com.kaizen.controller;

import java.util.ArrayList;
import java.util.List;

import com.kaizen.model.*;
import com.kaizen.model.dto.ArtDTO;
import com.kaizen.model.entity.*;
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
 * {@code ArtControllerIntegrationTest} is a test class to do integration
 * testing from {@link ArtController} using H2 embeded database.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ArtControllerIntegrationTest {
    /**
     * The art's controller used for testing.
     */
    @Autowired
    private ArtController artController;

    /**
     * The art's repository used for testing.
     */
    @Autowired
    private ArtRepository artRepository;

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
        artRepository.deleteAll();
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    /**
     * {@code getArts_MissingCompany_ExpectNotFound} is a test on
     * {@link ArtController#getArts()} to verify if the method will Http Status Not
     * Found(404) when the company with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getArts_MissingCompany_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestArt.URL_EXTENSION)
                .param(TestArt.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code getArts_Found_ExpectOKFound} is a test on
     * {@link ArtController#getlatestArts()} to verify if the method will return the
     * list of all ART DTOs of a company with Http Status Ok(200) and content type
     * of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getArts_Found_ExpectOKFound() throws Exception {
        companyRepository.save(TestCompany.createCompany());
        List<ArtDTO> artDTOs = new ArrayList<>();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestArt.URL_EXTENSION)
                .param(TestArt.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(artDTOs)));
    }

    /**
     * {@code getlatestArts_Found_ExpectOKFound} is a test on
     * {@link ArtController#getlatestArts()} to verify if the method will return the
     * list of all ART DTOs of a company with Http Status Ok(200) and content type
     * of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getlatestArts_Found_ExpectOKFound() throws Exception {
        companyRepository.save(TestCompany.createCompany());
        List<ArtDTO> artDTOs = new ArrayList<>();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestArt.URL_EXTENSION_LATEST)
                .param(TestArt.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(artDTOs)));
    }

    /**
     * {@code createArt_MissingEmployee_ExpectNotFound} is a test on
     * {@link ArtController#createArt(String, ArtDTO)} to verify if the method will
     * return Http Status Not Found(404) when the employee with the specific id is
     * not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createArt_MissingEmployee_ExpectNotFound() throws Exception {
        companyRepository.save(TestCompany.createCompany());
        ArtDTO artDTO = TestArt.createArtDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestArt.URL_EXTENSION)
                .param(TestArt.EMP_ID_KEY, TestEmployee.TEST_ID).param(TestArt.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(artDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code createArt_New_ExpectCreatedSaved} is a test on
     * {@link ArtController#createArt(String, ArtDTO)} to verify if the method will
     * return the created specific ART DTO with Http Status Created(201) and content
     * type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createArt_New_ExpectCreatedSaved() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        employeeRepository.save(employee);
        ArtDTO artDTO = TestArt.createArtDTO();
        artDTO.setEmployeeWP(employee.getWorkPermitNumber());
        artDTO.setEmployeeName(employee.getName());
        artDTO.setCompany(company.getUEN());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestArt.URL_EXTENSION)
                .param(TestArt.EMP_ID_KEY, employee.getWorkPermitNumber()).param(TestArt.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(artDTO)).contentType(MediaType.APPLICATION_JSON);

        ResultActions ra = MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder);
        List<Art> arts = artRepository.findAll();
        artDTO.setId(arts.get(0).getId());

        ra.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(artDTO)));
    }

    /**
     * {@code deleteArt_NotFound_ExpectNotFound} is a test on
     * {@link ArtController#deleteArt(String)} to verify if the method will return
     * Http Status Not Found(404) when the ART with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteArt_NotFound_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestArt.URL_EXTENSION + TestArt.TEST_ID);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code deleteArt_Deleted_ExpectOK} is a test on
     * {@link ArtController#deleteArt(String)} to verify if the method will return
     * Http Status OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteArt_Deleted_ExpectOK() throws Exception {
        Art art = TestArt.createArt();
        Company company = art.getCompany();
        company = companyRepository.save(company);
        Employee employee = art.getEmployee();
        employee = employeeRepository.save(employee);
        art = artRepository.save(art);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestArt.URL_EXTENSION + art.getId());

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}