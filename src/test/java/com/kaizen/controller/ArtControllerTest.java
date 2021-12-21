package com.kaizen.controller;

import static org.mockito.Mockito.*;

import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.*;
import com.kaizen.model.dto.ArtDTO;
import com.kaizen.model.entity.*;
import com.kaizen.service.art.ArtService;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.employee.EmployeeService;

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
 * {@code ArtControllerTest} is a test class to do unit testing on
 * {@link ArtController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@ContextConfiguration(classes = { ArtController.class })
@ExtendWith(SpringExtension.class)
class ArtControllerTest {
    /**
     * The ART's controller used for testing.
     */
    @Autowired
    private ArtController artController;

    /**
     * The mocked ART's service used for testing.
     */
    @MockBean
    private ArtService artService;

    /**
     * The mocked employee's service used for testing.
     */
    @MockBean
    private EmployeeService employeeService;

    /**
     * The mocked company's service used for testing.
     */
    @MockBean
    private CompanyService companyService;

    /**
     * {@code getArts_MissingCompId_ExpectBadRequest} is a test on
     * {@link ArtController#getArts(String)} to verify if the method will return
     * Http Status Bad Request(400) when the specific company's id is missing in the
     * call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getArts_MissingCompId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestArt.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code getArts_MissingCompany_ExpectNotFound} is a test on
     * {@link ArtController#getArts(String)} to verify if the method will call
     * {@link ArtService#listArtsByCompany(String)} and return Http Status Not
     * Found(404) when the company with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getArts_MissingCompany_ExpectNotFound() throws Exception {
        when(artService.listArtsByCompany(any(String.class))).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestArt.URL_EXTENSION)
                .param(TestArt.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(artService).listArtsByCompany(any(String.class));
    }

    /**
     * {@code getArts_Found_ExpectOKFound} is a test on
     * {@link ArtController#getArts(String)} to verify if the method will call
     * {@link ArtService#listArtsByCompany(String)} and return the list of all ART
     * DTOs of a company with Http Status Ok(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getArts_Found_ExpectOKFound() throws Exception {
        List<Art> arts = new ArrayList<>();
        arts.add(TestArt.createArt());
        List<ArtDTO> artDTOs = new ArrayList<>();
        artDTOs.add(TestArt.createArtDTO());
        when(artService.listArtsByCompany(any(String.class))).thenReturn(arts);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestArt.URL_EXTENSION)
                .param(TestArt.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(artDTOs)));

        verify(artService).listArtsByCompany(any(String.class));
    }

    /**
     * {@code getlatestArts_MissingCompId_ExpectBadRequest} is a test on
     * {@link ArtController#getlatestArts(String)} to verify if the method will
     * return Http Status Bad Request(400) when the specific company's id is missing
     * in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getlatestArts_MissingCompId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestArt.URL_EXTENSION_LATEST);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code getlatestArts_Found_ExpectOKFound} is a test on
     * {@link ArtController#getlatestArts(String)} to verify if the method will call
     * {@link ArtService#listLatestArts()} and return the list of all ART DTOs of a
     * company with Http Status Ok(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getlatestArts_Found_ExpectOKFound() throws Exception {
        String companyId = TestCompany.TEST_ID + "1";
        List<Art> arts = new ArrayList<>();
        arts.add(TestArt.createArt());
        arts.add(TestArt.createArt());
        arts.get(1).getCompany().setUEN(companyId);
        List<ArtDTO> artDTOs = new ArrayList<>();
        artDTOs.add(TestArt.createArtDTO());
        when(artService.listLatestArts()).thenReturn(arts);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestArt.URL_EXTENSION_LATEST)
                .param(TestArt.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(artDTOs)));

        verify(artService).listLatestArts();
    }

    /**
     * {@code createArt_MissingArtDTO_ExpectBadRequest} is a test on
     * {@link ArtController#createArt(String, String, ArtDTO)} to verify if the
     * method will return Http Status Bad Request(400) when the specific ART DTO is
     * missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createArt_MissingArtDTO_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestArt.URL_EXTENSION)
                .param(TestArt.EMP_ID_KEY, TestEmployee.TEST_ID).param(TestArt.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code createArt_MissingEmpId_ExpectBadRequest} is a test on
     * {@link ArtController#createArt(String, String, ArtDTO)} to verify if the
     * method will return Http Status Bad Request(400) when the specific employee's
     * id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createArt_MissingEmpId_ExpectBadRequest() throws Exception {
        ArtDTO artDTO = TestArt.createArtDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestArt.URL_EXTENSION)
                .param(TestArt.COMP_ID_KEY, TestCompany.TEST_ID).content(TestJsonConverter.writeValueAsString(artDTO))
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code createArt_MissingCompId_ExpectBadRequest} is a test on
     * {@link ArtController#createArt(String, String, ArtDTO)} to verify if the
     * method will return Http Status Bad Request(400) when the specific employee's
     * id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createArt_MissingCompId_ExpectBadRequest() throws Exception {
        ArtDTO artDTO = TestArt.createArtDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestArt.URL_EXTENSION)
                .param(TestArt.EMP_ID_KEY, TestEmployee.TEST_ID).content(TestJsonConverter.writeValueAsString(artDTO))
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code createArt_InvalidArtDTO_ExpectBadRequest} is a test on
     * {@link ArtController#createArt(String, String, ArtDTO)} to verify if the
     * method will return Http Status Bad Request(400) when the specific employee is
     * invalid in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createArt_InvalidArtDTO_ExpectBadRequest() throws Exception {
        ArtDTO artDTO = TestArt.createArtDTO();
        artDTO.setDateOfTest(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestArt.URL_EXTENSION)
                .param(TestArt.EMP_ID_KEY, TestEmployee.TEST_ID).param(TestArt.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(artDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code createArt_MissingEmployee_ExpectNotFound} is a test on
     * {@link ArtController#createArt(String, String, ArtDTO)} to verify if the
     * method will call {@link EmployeeService#getEmployee(String)} and return Http
     * Status Not Found(404) when the employee with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createArt_MissingEmployee_ExpectNotFound() throws Exception {
        ArtDTO artDTO = TestArt.createArtDTO();
        when(employeeService.getEmployee(TestEmployee.TEST_ID)).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestArt.URL_EXTENSION)
                .param(TestArt.EMP_ID_KEY, TestEmployee.TEST_ID).param(TestArt.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(artDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(employeeService).getEmployee(TestEmployee.TEST_ID);
    }

    /**
     * {@code createArt_MissingCompany_ExpectNotFound} is a test on
     * {@link ArtController#createArt(String, String, ArtDTO)} to verify if the
     * method will call {@link EmployeeService#getEmployee(String)} and
     * {@link CompanyService#getCompany(String)} and return Http Status Not
     * Found(404) when the company with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createArt_MissingCompany_ExpectNotFound() throws Exception {
        ArtDTO artDTO = TestArt.createArtDTO();
        when(employeeService.getEmployee(TestEmployee.TEST_ID)).thenReturn(TestEmployee.createEmployee());
        when(companyService.getCompany(TestCompany.TEST_ID)).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestArt.URL_EXTENSION)
                .param(TestArt.EMP_ID_KEY, TestEmployee.TEST_ID).param(TestArt.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(artDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(employeeService).getEmployee(TestEmployee.TEST_ID);
        verify(companyService).getCompany(TestCompany.TEST_ID);
    }

    /**
     * {@code createArt_New_ExpectCreatedSaved} is a test on
     * {@link ArtController#createArt(String, String, ArtDTO))} to verify if the
     * method will call {@link EmployeeService#getEmployee(String)} and
     * {@link CompanyService#getCompany(String)} and
     * {@link ArtService#createArt(Art)} and return the created specific ART with
     * Http Status Created(201) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createArt_New_ExpectCreatedSaved() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        ArtDTO artDTO = TestArt.createArtDTO();
        artDTO.setEmployeeWP(employee.getWorkPermitNumber());
        artDTO.setEmployeeName(employee.getName());
        artDTO.setCompany(company.getUEN());
        when(employeeService.getEmployee(employee.getWorkPermitNumber())).thenReturn(employee);
        when(companyService.getCompany(company.getUEN())).thenReturn(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestArt.URL_EXTENSION)
                .param(TestArt.EMP_ID_KEY, employee.getWorkPermitNumber()).param(TestArt.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(artDTO)).contentType(MediaType.APPLICATION_JSON);
        Art art = TestArt.createArt();
        art.setEmployee(employee);
        art.setCompany(company);
        when(artService.addArt(any(Art.class))).thenReturn(art);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(artDTO)));

        verify(employeeService).getEmployee(employee.getWorkPermitNumber());
        verify(companyService).getCompany(TestCompany.TEST_ID);
        verify(artService).addArt(any(Art.class));
    }

    /**
     * {@code deleteArt_NotFound_ExpectNotFound} is a test on
     * {@link ArtController#deleteArt(String)} to verify if the method will call
     * {@link ArtService#deleteArt(String)} and return Http Status Not Found(404)
     * when the ART with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteArt_NotFound_ExpectNotFound() throws Exception {
        doThrow(new ObjectNotExistsException()).when(artService).deleteArt(TestArt.TEST_ID);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestArt.URL_EXTENSION + TestArt.TEST_ID);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(artService).deleteArt(TestArt.TEST_ID);
    }

    /**
     * {@code deleteArt_Deleted_ExpectOK} is a test on
     * {@link ArtController#deleteArt(String)} to verify if the method will call
     * {@link ArtService#deleteArt(String)} and return Http Status OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteArt_Deleted_ExpectOK() throws Exception {
        doNothing().when(artService).deleteArt(TestArt.TEST_ID);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestArt.URL_EXTENSION + TestArt.TEST_ID);

        MockMvcBuilders.standaloneSetup(artController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(artService).deleteArt(TestArt.TEST_ID);
    }
}
