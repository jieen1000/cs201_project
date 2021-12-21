package com.kaizen.controller;

import static org.mockito.Mockito.*;

import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.*;
import com.kaizen.model.dto.EmployeeSkillDTO;
import com.kaizen.model.entity.*;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.employee.EmployeeService;
import com.kaizen.service.employeeSkill.EmployeeSkillService;
import com.kaizen.service.image.ImageService;
import com.kaizen.service.skill.SkillService;

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
 * {@code EmployeeSkillControllerTest} is a test class to do unit testing on
 * {@link EmployeeSkillController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@ContextConfiguration(classes = { EmployeeSkillController.class })
@ExtendWith(SpringExtension.class)
class EmployeeSkillControllerTest {
    /**
     * The employee's skill's controller used for testing.
     */
    @Autowired
    private EmployeeSkillController employeeSkillController;

    /**
     * The mocked employee's skill's service used for testing.
     */
    @MockBean
    private EmployeeSkillService employeeSkillService;

    /**
     * The mocked company's service used for testing.
     */
    @MockBean
    private CompanyService companyService;

    /**
     * The mocked image's service used for testing.
     */
    @MockBean
    private ImageService imageService;

    /**
     * The mocked employee's service used for testing.
     */
    @MockBean
    private EmployeeService employeeService;

    /**
     * The mocked company's service used for testing.
     */
    @MockBean
    private SkillService skillService;

    /**
     * {@code getAllEmployeeSkillsNotFromCompany_MissingCompId_ExpectBadRequest} is
     * a test on
     * {@link EmployeeSkillController#getAllEmployeeSkillsNotFromCompany(String)} to
     * verify if the method will return Http Status Bad Request(400) when the
     * specific company's id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getAllEmployeeSkillsNotFromCompany_MissingCompId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION_ALL);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code getAllEmployeeSkillsNotFromCompany_Found_ExpectOKFound} is a test on
     * {@link EmployeeSkillController#getAllEmployeeSkillsNotFromCompany(String)} to
     * verify if the method will call
     * {@link EmployeeSkillService#listEmployeeSkills()},
     * {@link ImageService#getProfileImageURL(Employee)} and return the list of all
     * employee's skills that not of specific companywith Http Status Ok(200) and
     * content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getAllEmployeeSkillsNotFromCompany_Found_ExpectOKFound() throws Exception {
        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        employeeSkills.add(TestEmployeeSkill.createEmployeeSkill());
        employeeSkills.add(TestEmployeeSkill.createEmployeeSkill());
        String companyId = TestCompany.TEST_ID + "1";
        employeeSkills.get(1).getCompany().setUEN(companyId);
        List<EmployeeSkillDTO> employeeSkillDTOs = new ArrayList<>();
        employeeSkillDTOs.add(TestEmployeeSkill.createEmployeeSkillDTO());
        when(employeeSkillService.listEmployeeSkills()).thenReturn(employeeSkills);
        when(imageService.getProfileImageURL(any(Employee.class))).thenReturn(TestImage.DEFAULT_IMAGE_URL);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION_ALL)
                .param(TestEmployeeSkill.COMP_ID_KEY, companyId);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .string(TestJsonConverter.writeValueAsString(employeeSkillDTOs)));

        verify(employeeSkillService).listEmployeeSkills();
        verify(imageService).getProfileImageURL(any(Employee.class));
    }

    /**
     * {@code getEmployeeSkillsByCompany_MissingCompId_ExpectBadRequest} is a test
     * on {@link EmployeeSkillController#getEmployeeSkillsByCompany(String)} to
     * verify if the method will return Http Status Bad Request(400) when the
     * specific company's id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsByCompany_MissingCompId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code getEmployeeSkillsByCompany_Found_ExpectOKFound} is a test on
     * {@link EmployeeSkillController#getEmployeeSkillsByCompany(String)} to verify
     * if the method will call {@link EmployeeSkillService#listEmployeeSkills()},
     * {@link ImageService#getProfileImageURL(Employee)} and return the list of all
     * employee's skills with Http Status Ok(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsByCompany_Found_ExpectOKFound() throws Exception {
        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        employeeSkills.add(TestEmployeeSkill.createEmployeeSkill());
        List<EmployeeSkillDTO> employeeSkillDTOs = new ArrayList<>();
        employeeSkillDTOs.add(TestEmployeeSkill.createEmployeeSkillDTO());
        when(employeeSkillService.listEmployeeSkillsByCompany(any(String.class))).thenReturn(employeeSkills);
        when(imageService.getProfileImageURL(any(Employee.class))).thenReturn(TestImage.DEFAULT_IMAGE_URL);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .string(TestJsonConverter.writeValueAsString(employeeSkillDTOs)));

        verify(employeeSkillService).listEmployeeSkillsByCompany(any(String.class));
        verify(imageService).getProfileImageURL(any(Employee.class));
    }

    /**
     * {@code getEmployeeSkillsByEmployee_MissingEmpId_ExpectBadRequest} is a test
     * on {@link EmployeeSkillController#getEmployeeSkillsByEmployee(String)} to
     * verify if the method will return Http Status Bad Request(400) when the
     * specific employee's id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsByEmployee_MissingEmpId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code getEmployeeSkillsByEmployee_Found_ExpectOKFound} is a test on
     * {@link EmployeeSkillController#getEmployeeSkillsByEmployee(String)} to verify
     * if the method will call
     * {@link EmployeeSkillService#getEmployeeSkillsByEmployee(String)} and return
     * the list of all employee's skills of the employee with specific employee's id
     * with Http Status Ok(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsByEmployee_Found_ExpectOKFound() throws Exception {
        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        when(employeeSkillService.getEmployeeSkillsByEmployee(TestEmployee.TEST_ID)).thenReturn(employeeSkills);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
                        MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employeeSkills)));

        verify(employeeSkillService).getEmployeeSkillsByEmployee(TestEmployee.TEST_ID);
    }

    /**
     * {@code getEmployeeSkillsBySkill_MissingCompId_ExpectBadRequest} is a test on
     * {@link EmployeeSkillController#getEmployeeSkillsBySkill(String, String)} to
     * verify if the method will return Http Status Bad Request(400) when the
     * specific company's id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsBySkill_MissingCompId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code getEmployeeSkillsBySkill_Found_ExpectOKFound} is a test on
     * {@link EmployeeSkillController#getEmployeeSkillsBySkill(String, String)} to
     * verify if the method will call
     * {@link EmployeeSkillService#getEmployeeSkillsBySkill(String)} and
     * {@link ImageService#getProfileImageURL(Employee)}and return the list of all
     * employee's skill dtos of the skill of a specific company with specific
     * skill's id with Http Status Ok(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsBySkill_Found_ExpectOKFound() throws Exception {
        String companyId = TestCompany.TEST_ID + "1";
        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        employeeSkills.add(TestEmployeeSkill.createEmployeeSkill());
        employeeSkills.add(TestEmployeeSkill.createEmployeeSkill());
        employeeSkills.get(1).getCompany().setUEN(companyId);
        List<EmployeeSkillDTO> employeeSkillDTOs = new ArrayList<>();
        employeeSkillDTOs.add(TestEmployeeSkill.createEmployeeSkillDTO());
        when(employeeSkillService.getEmployeeSkillsBySkill(TestSkill.TEST_ID)).thenReturn(employeeSkills);
        when(imageService.getProfileImageURL(any(Employee.class))).thenReturn(TestImage.DEFAULT_IMAGE_URL);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.SKILL_ID_KEY, employeeSkills.get(1).getSkill().getSkill())
                .param(TestEmployeeSkill.COMP_ID_KEY, companyId);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .string(TestJsonConverter.writeValueAsString(employeeSkillDTOs)));

        verify(employeeSkillService).getEmployeeSkillsBySkill(TestSkill.TEST_ID);
        verify(imageService).getProfileImageURL(any(Employee.class));
    }

    /**
     * {@code getEmployeeSkillsByEmployeeAndSkill_NotFound_ExpectNotFound} is a test
     * on
     * {@link EmployeeSkillController#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * to verify if the method will call
     * {@link EmployeeSkillService#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * and return Http Status Not Found(404) when the employee's skill with the
     * specific employee's id and skill's id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsByEmployeeAndSkill_NotFound_ExpectNotFound() throws Exception {
        when(employeeSkillService.getEmployeeSkillByEmployeeAndSkill(TestEmployee.TEST_ID, TestSkill.TEST_ID))
                .thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(employeeSkillService).getEmployeeSkillByEmployeeAndSkill(TestEmployee.TEST_ID, TestSkill.TEST_ID);
    }

    /**
     * {@code getEmployeeSkillsByEmployeeAndSkill_NotFound_ExpectNotFound} is a test
     * on
     * {@link EmployeeSkillController#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * to verify if the method will call
     * {@link EmployeeSkillService#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * and return the employee's skill with the specific employee's id and skill's
     * id with Http Status OK(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsByEmployeeAndSkill_Found_ExpectOKFound() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        EmployeeSkillKey key = employeeSkill.getId();
        when(employeeSkillService.getEmployeeSkillByEmployeeAndSkill(key.getEmployee(), key.getSkill()))
                .thenReturn(employeeSkill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employeeSkill)));

        verify(employeeSkillService).getEmployeeSkillByEmployeeAndSkill(key.getEmployee(), key.getSkill());
    }

    /**
     * {@code createEmployeeSkill_Missing_ExpectBadRequest} is a test on
     * {@link EmployeeSkillController#createEmployeeSkill(String, EmployeeSkill)} to
     * verify if the method will return Http Status Bad Request(400) when the
     * specific company's id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployeeSkill_MissingCompId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployeeSkill.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(TestEmployeeSkill.createEmployeeSkill()))
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code createEmployeeSkill_Missing_ExpectBadRequest} is a test on
     * {@link EmployeeSkillController#createEmployeeSkill(String, EmployeeSkill)} to
     * verify if the method will return Http Status Bad Request(400) when the
     * specific employee's skill is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployeeSkill_MissingEmployeeSkill_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code createEmployeeSkill_InvalidEmployeeSkill_ExpectBadRequest} is a test
     * on {@link EmployeeSkillController#createEmployeeSkill(String, EmployeeSkill)}
     * to verify if the method will return Http Status Bad Request(400) when the
     * specific employee's skill is invalid in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployeeSkill_InvalidEmployeeSkill_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(TestEmployeeSkill.createInvalidEmployeeSkill()))
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code createEmployeeSkill_MissingEmployee_ExpectNotFound} is a test on
     * {@link EmployeeSkillController#createEmployeeSkill(String, EmployeeSkill)} to
     * verify if the method will call {@link EmployeeService#getEmployee(String)}
     * and return Http Status Not Found(404) when the employee with the specific
     * employee's id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployeeSkill_MissingEmployee_ExpectNotFound() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        when(employeeService.getEmployee(any(String.class))).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(employeeService).getEmployee(any(String.class));
    }

    /**
     * {@code createEmployeeSkill_MissingSkill_ExpectNotFound} is a test on
     * {@link EmployeeSkillController#createEmployeeSkill(String, EmployeeSkill)} to
     * verify if the method will call {@link EmployeeService#getEmployee(String)}
     * and {@link SkillService#getSkill(String)} and return Http Status Not
     * Found(404) when the skill with the specific skill's id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployeeSkill_MissingSkill_ExpectNotFound() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        when(employeeService.getEmployee(any(String.class))).thenReturn(employeeSkill.getEmployee());
        when(skillService.getSkill(any(String.class))).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(employeeService).getEmployee(any(String.class));
        verify(skillService).getSkill(any(String.class));
    }

    /**
     * {@code createEmployeeSkill_MissingCompany_ExpectNotFound} is a test on
     * {@link EmployeeSkillController#createEmployeeSkill(String, EmployeeSkill)} to
     * verify if the method will call {@link EmployeeService#getEmployee(String)},
     * {@link SkillService#getSkill(String)} and
     * {@link CompanyService#getCompany(String)} and return Http Status Not
     * Found(404) when the company with the specific comapny's id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployeeSkill_MissingCompany_ExpectNotFound() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        when(employeeService.getEmployee(any(String.class))).thenReturn(employeeSkill.getEmployee());
        when(skillService.getSkill(any(String.class))).thenReturn(employeeSkill.getSkill());
        when(companyService.getCompany(any(String.class))).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(employeeService).getEmployee(any(String.class));
        verify(skillService).getSkill(any(String.class));
        verify(companyService).getCompany(any(String.class));
    }

    /**
     * {@code createEmployeeSkill_SameID_ExpectConflict} is a test on
     * {@link EmployeeSkillController#createEmployeeSkill(String, EmployeeSkill)} to
     * verify if the method will call {@link EmployeeService#getEmployee(String)},
     * {@link SkillService#getSkill(String)},
     * {@link CompanyService#getCompany(String)} and
     * {@link EmployeeSkillService#addEmployeeSkill(EmployeeSkill)} and return Http
     * Status Conflict(409) when an employee's skill with the same id already
     * exists.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployeeSkill_SameID_ExpectConflict() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        when(employeeService.getEmployee(any(String.class))).thenReturn(employeeSkill.getEmployee());
        when(skillService.getSkill(any(String.class))).thenReturn(employeeSkill.getSkill());
        when(companyService.getCompany(any(String.class))).thenReturn(employeeSkill.getCompany());
        when(employeeSkillService.addEmployeeSkill(employeeSkill)).thenThrow(new ObjectExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict());

        verify(employeeService).getEmployee(any(String.class));
        verify(skillService).getSkill(any(String.class));
        verify(companyService).getCompany(any(String.class));
        verify(employeeSkillService).addEmployeeSkill(employeeSkill);
    }

    /**
     * {@code createEmployeeSkill_New_ExpectCreatedSaved} is a test on
     * {@link EmployeeSkillController#createEmployeeSkill(String, EmployeeSkill)} to
     * verify if the method will call
     * {@link EmployeeSkillService#addEmployeeSkill(EmployeeSkill)} and return the
     * created specific employee's skill with Http Status Created(201) and content
     * type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployeeSkill_New_ExpectCreatedSaved() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        when(employeeService.getEmployee(any(String.class))).thenReturn(employeeSkill.getEmployee());
        when(skillService.getSkill(any(String.class))).thenReturn(employeeSkill.getSkill());
        when(companyService.getCompany(any(String.class))).thenReturn(employeeSkill.getCompany());
        when(employeeSkillService.addEmployeeSkill(employeeSkill)).thenReturn(employeeSkill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employeeSkill)));

        verify(employeeService).getEmployee(any(String.class));
        verify(skillService).getSkill(any(String.class));
        verify(companyService).getCompany(any(String.class));
        verify(employeeSkillService).addEmployeeSkill(employeeSkill);
    }

    /**
     * {@code updateEmployeeSkill_MissingEmpId_ExpectBadRequest} is a test on
     * {@link EmployeeSkillController#updateEmployeeSkill(String, String, String, EmployeeSkill)}
     * to verify if the method will return Http Status Bad Request(400) when the
     * specific employee's id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployeeSkill_MissingEmpId_ExpectBadRequest() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateEmployeeSkill_MissingSkillId_ExpectBadRequest} is a test on
     * {@link EmployeeSkillController#updateEmployeeSkill(String, String, String, EmployeeSkill)}
     * to verify if the method will return Http Status Bad Request(400) when the
     * specific skill's id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployeeSkill_MissingSkillId_ExpectBadRequest() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateEmployeeSkill_MissingEmpId_ExpectBadRequest} is a test on
     * {@link EmployeeSkillController#updateEmployeeSkill(String, String, String, EmployeeSkill)}
     * to verify if the method will return Http Status Bad Request(400) when the
     * specific company's id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployeeSkill_MissingCompId_ExpectBadRequest() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateEmployeeSkill_MissingEmployeeSkill_ExpectBadRequest} is a test
     * on
     * {@link EmployeeSkillController#updateEmployeeSkill(String, String, String, EmployeeSkill)}
     * to verify if the method will return Http Status Bad Request(400) when the
     * specific employee's skill is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployeeSkill_MissingEmployeeSkill_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateEmployeeSkill_InvalidEmployeeSkill_ExpectBadRequest} is a test
     * on
     * {@link EmployeeSkillController#updateEmployeeSkill(String, String, String, EmployeeSkill)}
     * to verify if the method will return Http Status Bad Request(400) when the
     * specific employee's skill is invalid in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployeeSkill_InvalidEmployeeSkill_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID).contentType(MediaType.APPLICATION_JSON)
                .content(TestJsonConverter.writeValueAsString(TestEmployeeSkill.createInvalidEmployeeSkill()));

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateEmployeeSkill_NotFound_ExpectNotFound} is a test on
     * {@link EmployeeSkillController#updateEmployeeSkill(String, String, String, EmployeeSkill)}
     * to verify if the method will call {@link CompanyService#getCompany(String)}
     * and return Http Status Not Found(404) when the company with the specific
     * company's id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployeeSkill_MissingCompany_ExpectNotFound() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        when(companyService.getCompany(any(String.class))).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID).contentType(MediaType.APPLICATION_JSON)
                .content(TestJsonConverter.writeValueAsString(employeeSkill));

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(any(String.class));
    }

    /**
     * {@code updateEmployeeSkill_MissingEmployeeSkill_ExpectNotFound} is a test on
     * {@link EmployeeSkillController#updateEmployeeSkill(String, String, String, EmployeeSkill)}
     * to verify if the method will call {@link CompanyService#getCompany(String)}
     * and
     * {@link EmployeeSkillService#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * and return Http Status Not Found(404) when the employee's skill with the
     * specific employee's id and skill's id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployeeSkill_MissingEmployeeSkill_ExpectNotFound() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        EmployeeSkillKey key = employeeSkill.getId();
        when(companyService.getCompany(any(String.class))).thenReturn(TestCompany.createCompany());
        when(employeeSkillService.getEmployeeSkillByEmployeeAndSkill(key.getEmployee(), key.getSkill()))
                .thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID).contentType(MediaType.APPLICATION_JSON)
                .content(TestJsonConverter.writeValueAsString(employeeSkill));

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(any(String.class));
        verify(employeeSkillService).getEmployeeSkillByEmployeeAndSkill(key.getEmployee(), key.getSkill());
    }

    /**
     * {@code updateEmployeeSkill_Updated_ExpectOKUpdated} is a test on
     * {@link EmployeeSkillController#updateEmployeeSkill(String, String, String, EmployeeSkill)}
     * to verify if the method will call {@link CompanyService#getCompany(String)},
     * {@link EmployeeSkillService#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * and
     * {@link EmployeeSkillService#updateEmployeeSkill(EmployeeSkillKey, EmployeeSkill)}
     * and return the updated specific employee's skill with Http Status OK(200) and
     * content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployeeSkill_Updated_ExpectOKUpdated() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        employeeSkill.setEmployee(null);
        employeeSkill.setSkill(null);
        EmployeeSkillKey key = employeeSkill.getId();
        when(companyService.getCompany(any(String.class))).thenReturn(TestCompany.createCompany());
        when(employeeSkillService.getEmployeeSkillByEmployeeAndSkill(key.getEmployee(), key.getSkill()))
                .thenReturn(employeeSkill);
        when(employeeSkillService.updateEmployeeSkill(employeeSkill.getId(), employeeSkill)).thenReturn(employeeSkill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID).contentType(MediaType.APPLICATION_JSON)
                .content(TestJsonConverter.writeValueAsString(employeeSkill));

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employeeSkill)));

        verify(companyService).getCompany(any(String.class));
        verify(employeeSkillService).getEmployeeSkillByEmployeeAndSkill(key.getEmployee(), key.getSkill());
        verify(employeeSkillService).updateEmployeeSkill(employeeSkill.getId(), employeeSkill);
    }

    /**
     * {@code deleteEmployeeSkill_MissingEmpId_ExpectBadRequest} is a test on
     * {@link EmployeeSkillController#deleteEmployeeSkill(String, String)} to verify
     * if the method will return Http Status Bad Request(400) when the specific
     * employee's id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteEmployeeSkill_MissingEmpId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code deleteEmployeeSkill_MissingSkillId_ExpectBadRequest} is a test on
     * {@link EmployeeSkillController#deleteEmployeeSkill(String, String)} to verify
     * if the method will return Http Status Bad Request(400) when the specific
     * skill's id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteEmployeeSkill_MissingSkillId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code deleteEmployeeSkill_MissingEmployeeSkill_ExpectNotFound} is a test on
     * {@link EmployeeSkillController#deleteEmployeeSkill(String, String)} to verify
     * if the method will call
     * {@link EmployeeSkillService#deleteEmployeeSkill(EmployeeSkillKey)} and return
     * Http Status Not Found(404) when the employee's skill with the specific
     * employee's id and skill's id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteEmployeeSkill_MissingEmployeeSkill_ExpectNotFound() throws Exception {
        when(employeeSkillService.getEmployeeSkillByEmployeeAndSkill(TestEmployee.TEST_ID, TestSkill.TEST_ID))
                .thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(employeeSkillService).getEmployeeSkillByEmployeeAndSkill(TestEmployee.TEST_ID, TestSkill.TEST_ID);
    }

    /**
     * {@code deleteCompany_Deleted_ExpectOK} is a test on
     * {@link EmployeeSkillController#deleteEmployeeSkill(String, String)} to verify
     * if the method will call
     * {@link EmployeeSkillService#deleteEmployeeSkill(EmployeeSkill)} and return
     * Http Status OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteEmployeeSkill_Deleted_ExpectOK() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        EmployeeSkillKey key = employeeSkill.getId();
        when(employeeSkillService.getEmployeeSkillByEmployeeAndSkill(key.getEmployee(), key.getSkill()))
                .thenReturn(employeeSkill);
        doNothing().when(employeeSkillService).deleteEmployeeSkill(employeeSkill.getId());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(employeeSkillService).getEmployeeSkillByEmployeeAndSkill(key.getEmployee(), key.getSkill());
        verify(employeeSkillService).deleteEmployeeSkill(employeeSkill.getId());
    }

    /**
     * {@code collate_MissingCompId_ExpectBadRequest} is a test on
     * {@link EmployeeSkillController#collate(String)} to verify if the method will return
     * Http Status Bad Request(400) when the specific company's id is missing in the
     * call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void collate_MissingCompId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestEmployeeSkill.URL_EXTENSION_COLLATE);

        MockMvcBuilders.standaloneSetup(employeeSkillController)
                .setMessageConverters(TestJsonConverter.messageConverter).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code collate_FoundEmpty_ExpectOK} is a test on
     * {@link EmployeeSkillController#collate(String)} to verify if the method will
     * return with Http Status Ok(200) and list of hashmap.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void collate_FoundEmpty_ExpectOK() throws Exception {
        when(employeeSkillService.collate(TestCompany.TEST_ID)).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestEmployeeSkill.URL_EXTENSION_COLLATE).param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController)
                .setMessageConverters(TestJsonConverter.messageConverter).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(employeeSkillService).collate(TestCompany.TEST_ID);
    }

    /**
     * {@code collate_FoundOne_ExpectOKFound} is a test on
     * {@link EmployeeSkillController#collate(String)} to verify if the method will
     * return with Http Status Ok(200) and list of hashmap.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void collate_FoundOne_ExpectOKFound() throws Exception {
        List<List<String>> listOfList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("Name");
        list.add("Pax");
        list.add("Min");
        listOfList.add(list);
        List<HashMap<String, String>> result = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", list.get(0));
        map.put("pax", list.get(1));
        map.put("min", list.get(2));
        result.add(map);
        when(employeeSkillService.collate(TestCompany.TEST_ID)).thenReturn(listOfList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestEmployeeSkill.URL_EXTENSION_COLLATE).param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController)
                .setMessageConverters(TestJsonConverter.messageConverter).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(result)));

        verify(employeeSkillService).collate(TestCompany.TEST_ID);
    }
}
