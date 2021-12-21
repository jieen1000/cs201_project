package com.kaizen.controller;

import java.util.ArrayList;
import java.util.List;

import com.kaizen.model.*;
import com.kaizen.model.entity.Company;
import com.kaizen.repository.CompanyRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * {@code CompanyControllerIntegrationTest} is a test class to do integration
 * testing from {@link CompanyController} using H2 embeded database.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CompanyControllerIntegrationTest {
    /**
     * The company's controller used for testing.
     */
    @Autowired
    private CompanyController companyController;

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
        companyRepository.deleteAll();
    }

    /**
     * {@code getCompanies_Found_ExpectOKFound} is a test on
     * {@link CompanyController#getCompanies()} to verify if the method will return
     * the list of all companies with Http Status Ok(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getCompanies_Found_ExpectOKFound() throws Exception {
        List<Company> companies = new ArrayList<>();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestCompany.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(companyController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(companies)));
    }

    /**
     * {@code getCompany_NotFound_ExpectNotFound} is a test on
     * {@link CompanyController#getCompany(String)} to verify if the method will
     * return Http Status Not Found(404) when the specific company is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getCompany_NotFound_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestCompany.URL_EXTENSION + TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code getCompany_Found_ExpectOKFound} is a test on
     * {@link CompanyController#getCompany(String)} to verify if the method will
     * return the company with the specific id with Http Status OK(200) and content
     * type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getCompany_Found_ExpectOKFound() throws Exception {
        Company company = TestCompany.createCompany();
        companyRepository.save(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestCompany.URL_EXTENSION + company.getUEN());

        MockMvcBuilders.standaloneSetup(companyController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(company)));
    }

    /**
     * {@code getCompanyByName_Found_ExpectOKFound} is a test on
     * {@link CompanyController#getCompanyByName(String)} to verify if the method
     * will return the company with the specific name with Http Status OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getCompanyByName_Found_ExpectOKFound() throws Exception {
        Company company = TestCompany.createCompany();
        companyRepository.save(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestCompany.URL_EXTENSION_SPECIFIC)
                .param(TestCompany.NAME_KEY, company.getName());

        MockMvcBuilders.standaloneSetup(companyController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(company)));
    }

    /**
     * {@code addCompany_SameID_ExpectConflict} is a test on
     * {@link CompanyController#createCompany(Company)} to verify if the method will
     * return Http Status Conflict(409) when a company with the same id already
     * exists.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addCompany_SameID_ExpectConflict() throws Exception {
        Company company = TestCompany.createCompany();
        companyRepository.save(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestCompany.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(company)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    /**
     * {@code addCompany_New_ExpectCreatedSaved} is a test on
     * {@link CompanyController#createCompany(Company)} to verify if the method will
     * return the created specific company with Http Status Created(201) and content
     * type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addCompany_New_ExpectCreatedSaved() throws Exception {
        Company company = TestCompany.createCompany();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestCompany.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(company)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(companyController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(company)));
    }

    /**
     * {@code updateCompany_NotFound_ExpectNotFound} is a test on
     * {@link CompanyController#updateCompany(String, Company)} to verify if the
     * method will return Http Status Not Found(404) when the company with the
     * specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateCompany_NotFound_ExpectNotFound() throws Exception {
        Company company = TestCompany.createCompany();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestCompany.URL_EXTENSION + company.getUEN())
                .content(TestJsonConverter.writeValueAsString(company)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code updateCompany_Updated_ExpectOKUpdated} is a test on
     * {@link CompanyController#updateCompany(String, Company)} to verify if the
     * method will return the updated specific company with Http Status OK(200) and
     * content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateCompany_Updated_ExpectOKUpdated() throws Exception {
        Company company = TestCompany.createCompany();
        String name = company.getName();
        company.setName("Blah blah");
        companyRepository.save(company);
        company.setName(name);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestCompany.URL_EXTENSION + company.getUEN())
                .content(TestJsonConverter.writeValueAsString(company)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(companyController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(company)));
    }

    /**
     * {@code deleteCompany_NotFound_ExpectNotFound} is a test on
     * {@link CompanyController#deleteCompany(String)} to verify if the method will
     * return Http Status Not Found(404) when the company with the specific id is
     * not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteCompany_NotFound_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestCompany.URL_EXTENSION + TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code deleteCompany_Deleted_ExpectOK} is a test on
     * {@link CompanyController#deleteCompany(String)} to verify if the method will
     * return Http Status OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteCompany_Deleted_ExpectOK() throws Exception {
        Company company = TestCompany.createCompany();
        companyRepository.save(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestCompany.URL_EXTENSION + company.getUEN());

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}