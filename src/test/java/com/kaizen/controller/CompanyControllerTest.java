package com.kaizen.controller;

import static org.mockito.Mockito.*;

import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.model.*;
import com.kaizen.model.entity.Company;
import com.kaizen.service.company.CompanyService;

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
 * {@code CompanyControllerTest} is a test class to do unit testing on
 * {@link CompanyController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-16
 */
@ContextConfiguration(classes = { CompanyController.class })
@ExtendWith(SpringExtension.class)
class CompanyControllerTest {
    /**
     * The company's controller used for testing.
     */
    @Autowired
    private CompanyController companyController;

    /**
     * The mocked company's service used for testing.
     */
    @MockBean
    private CompanyService companyService;

    /**
     * {@code getCompanies_Found_ExpectOKFound} is a test on
     * {@link CompanyController#getCompanies()} to verify if the method will call
     * {@link CompanyService#listCompanies()} and return the list of all companies
     * with Http Status Ok(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getCompanies_Found_ExpectOKFound() throws Exception {
        List<Company> companies = new ArrayList<>();
        when(companyService.listCompanies()).thenReturn(companies);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestCompany.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(companies)));

        verify(companyService).listCompanies();
    }

    /**
     * {@code getCompany_NotFound_ExpectNotFound} is a test on
     * {@link CompanyController#getCompany(String)} to verify if the method will
     * call {@link CompanyService#getCompany(String)} and return Http Status Not
     * Found(404) when the specific company is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getCompany_NotFound_ExpectNotFound() throws Exception {
        when(companyService.getCompany(TestCompany.TEST_ID)).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestCompany.URL_EXTENSION + TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(TestCompany.TEST_ID);
    }

    /**
     * {@code getCompany_Found_ExpectOKFound} is a test on
     * {@link CompanyController#getCompany(String)} to verify if the method will
     * call {@link CompanyService#getCompany(String)} and return the company with
     * the specific id with Http Status OK(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getCompany_Found_ExpectOKFound() throws Exception {
        Company company = TestCompany.createCompany();
        when(companyService.getCompany(company.getUEN())).thenReturn(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestCompany.URL_EXTENSION + company.getUEN());

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(company)));

        verify(companyService).getCompany(company.getUEN());
    }

    /**
     * {@code getCompanyByName_Found_ExpectOKFound} is a test on
     * {@link CompanyController#getCompanyByName(String)} to verify if the method
     * will call {@link CompanyService#getCompanyByName(String)} and return the
     * company with the specific id with Http Status OK(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getCompanyByName_Found_ExpectOKFound() throws Exception {
        Company company = TestCompany.createCompany();
        when(companyService.getCompanyByName(company.getName())).thenReturn(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestCompany.URL_EXTENSION_SPECIFIC)
                .param(TestCompany.NAME_KEY, company.getName());

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(company)));

        verify(companyService).getCompanyByName(company.getName());
    }

    /**
     * {@code addCompany_Missing_ExpectBadRequest} is a test on
     * {@link CompanyController#createCompany(Company)} to verify if the method will
     * return Http Status Bad Request(400) when the specific company is missing in
     * the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addCompany_Missing_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestCompany.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code addCompany_InvalidCompany_ExpectBadRequest} is a test on
     * {@link CompanyController#createCompany(Company)} to verify if the method will
     * return Http Status Bad Request(400) when the specific company is invalid in
     * the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addCompany_InvalidCompany_ExpectBadRequest() throws Exception {
        Company company = TestCompany.createCompany();
        company.setName(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestCompany.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(company)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code addCompany_SameID_ExpectConflict} is a test on
     * {@link CompanyController#createCompany(Company)} to verify if the method will
     * call {@link CompanyService#addCompany(Company)} and return Http Status
     * Conflict(409) when a company with the same id already exists.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addCompany_SameID_ExpectConflict() throws Exception {
        Company company = TestCompany.createCompany();
        when(companyService.addCompany(company)).thenThrow(new ObjectExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestCompany.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(company)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict());

        verify(companyService).addCompany(company);
    }

    /**
     * {@code addCompany_New_ExpectCreatedSaved} is a test on
     * {@link CompanyController#createCompany(Company)} to verify if the method will
     * call {@link CompanyService#addCompany(Company)} and return the created
     * specific company with Http Status Created(201) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addCompany_New_ExpectCreatedSaved() throws Exception {
        Company company = TestCompany.createCompany();
        when(companyService.addCompany(company)).thenReturn(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestCompany.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(company)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(company)));

        verify(companyService).addCompany(company);
    }

    /**
     * {@code updateCompany_MissingCompany_ExpectBadRequest} is a test on
     * {@link CompanyController#updateCompany(String, Company)} to verify if the
     * method will return Http Status Bad Request(400) when the specific company is
     * missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateCompany_MissingCompany_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestCompany.URL_EXTENSION + TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateCompany_InvalidCompany_ExpectBadRequest} is a test on
     * {@link CompanyController#updateCompany(String, Company)} to verify if the
     * method will return Http Status Bad Request(400) when the specific company is
     * invalid in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateCompany_InvalidCompany_ExpectBadRequest() throws Exception {
        Company company = TestCompany.createCompany();
        company.setName(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestCompany.URL_EXTENSION + company.getUEN())
                .content(TestJsonConverter.writeValueAsString(company)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateCompany_NotFound_ExpectNotFound} is a test on
     * {@link CompanyController#updateCompany(String, Company)} to verify if the
     * method will call {@link CompanyService#updateCompany(String, Company)} and
     * return Http Status Not Found(404) when the company with the specific id is
     * not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateCompany_NotFound_ExpectNotFound() throws Exception {
        Company company = TestCompany.createCompany();
        when(companyService.updateCompany(company.getUEN(), company)).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestCompany.URL_EXTENSION + company.getUEN())
                .content(TestJsonConverter.writeValueAsString(company)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).updateCompany(company.getUEN(), company);
    }

    /**
     * {@code updateCompany_Updated_ExpectOKUpdated} is a test on
     * {@link CompanyController#updateCompany(String, Company)} to verify if the
     * method will call {@link CompanyService#updateCompany(String, Company)} and
     * return the updated specific company with Http Status OK(200) and content type
     * of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateCompany_Updated_ExpectOKUpdated() throws Exception {
        Company company = TestCompany.createCompany();
        when(companyService.updateCompany(company.getUEN(), company)).thenReturn(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestCompany.URL_EXTENSION + company.getUEN())
                .content(TestJsonConverter.writeValueAsString(company)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(company)));

        verify(companyService).updateCompany(company.getUEN(), company);
    }

    /**
     * {@code deleteCompany_NotFound_ExpectNotFound} is a test on
     * {@link CompanyController#deleteCompany(String)} to verify if the method will
     * call {@link CompanyService#deleteCompany(String)} and return Http Status Not
     * Found(404) when the company with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteCompany_NotFound_ExpectNotFound() throws Exception {
        doThrow(new ObjectNotExistsException()).when(companyService).deleteCompany(TestCompany.TEST_ID);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestCompany.URL_EXTENSION + TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).deleteCompany(TestCompany.TEST_ID);
    }

    /**
     * {@code deleteCompany_Deleted_ExpectOK} is a test on
     * {@link CompanyController#deleteCompany(String)} to verify if the method will
     * call {@link CompanyService#deleteCompany(String)} and return Http Status
     * OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteCompany_Deleted_ExpectOK() throws Exception {
        doNothing().when(companyService).deleteCompany(TestCompany.TEST_ID);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestCompany.URL_EXTENSION + TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(companyController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(companyService).deleteCompany(TestCompany.TEST_ID);
    }
}
