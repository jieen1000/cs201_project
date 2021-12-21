package com.kaizen.controller;

import java.util.ArrayList;
import java.util.List;

import com.kaizen.model.*;
import com.kaizen.model.dto.EmployeeSkillDTO;
import com.kaizen.model.entity.*;
import com.kaizen.repository.*;

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
 * {@code EmployeeSkillControllerIntegrationTest} is a test class to do
 * integration testing from {@link EmployeeSkillController} using H2 embeded
 * database.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeSkillControllerIntegrationTest {
    /**
     * The employee's skill's controller used for testing.
     */
    @Autowired
    private EmployeeSkillController employeeSkillController;

    /**
     * The employee's skill's repository used for testing.
     */
    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    /**
     * The employee's repository used for testing.
     */
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * The skill's repository used for testing.
     */
    @Autowired
    private SkillRepository skillRepository;

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
        employeeSkillRepository.deleteAll();
        employeeRepository.deleteAll();
        skillRepository.deleteAll();
        companyRepository.deleteAll();
    }

    /**
     * {@code getAllEmployeeSkillsNotFromCompany_Found_ExpectOKFound} is a test on
     * {@link EmployeeSkillController#getAllEmployeeSkillsNotFromCompany(String)} to
     * verify if the method will return the list of all employee's skills not of a
     * company with Http Status Ok(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getAllEmployeeSkillsNotFromCompany_Found_ExpectOKFound() throws Exception {
        List<EmployeeSkillDTO> employeeSkillDTOs = new ArrayList<>();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION_ALL)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController)
                .setMessageConverters(TestJsonConverter.messageConverter).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .string(TestJsonConverter.writeValueAsString(employeeSkillDTOs)));
    }

    /**
     * {@code getEmployeeSkillsByCompany_MissingCompany_ExpectNotFound} is a test on
     * {@link EmployeeSkillController#getEmployeeSkillsByCompany(String)} to verify
     * if the method will return Http Status Not Found(404) when the company with
     * the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsByCompany_MissingCompany_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController)
                .setMessageConverters(TestJsonConverter.messageConverter).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code getEmployeeSkillsByCompany_Found_ExpectOKFound} is a test on
     * {@link EmployeeSkillController#getEmployeeSkillsByCompany(String)} to verify
     * if the method will return the list of all employee's skills of a company with
     * Http Status Ok(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsByCompany_Found_ExpectOKFound() throws Exception {
        List<EmployeeSkillDTO> employeeSkillDTOs = new ArrayList<>();
        companyRepository.save(TestCompany.createCompany());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController)
                .setMessageConverters(TestJsonConverter.messageConverter).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .string(TestJsonConverter.writeValueAsString(employeeSkillDTOs)));
    }

    /**
     * {@code getEmployeeSkillsByEmployee_Found_ExpectOKFound} is a test on
     * {@link EmployeeSkillController#getEmployeeSkillsByEmployee(String)} to verify
     * if the method will return the list of all employee's skills of the employee
     * with specific employee's id with Http Status Ok(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsByEmployee_Found_ExpectOKFound() throws Exception {
        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController)
                .setMessageConverters(TestJsonConverter.messageConverter).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
                        MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employeeSkills)));
    }

    /**
     * {@code getEmployeeSkillsBySkill_Found_ExpectOKFound} is a test on
     * {@link EmployeeSkillController#getEmployeeSkillsBySkill(String, String)} to
     * verify if the method will return the list of all employee's skills of the
     * skill with specific skill's id with Http Status Ok(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsBySkill_Found_ExpectOKFound() throws Exception {
        List<EmployeeSkillDTO> employeeSkillDTOs = new ArrayList<>();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController)
                .setMessageConverters(TestJsonConverter.messageConverter).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .string(TestJsonConverter.writeValueAsString(employeeSkillDTOs)));
    }

    /**
     * {@code getEmployeeSkillsByEmployeeAndSkill_NotFound_ExpectNotFound} is a test
     * on
     * {@link EmployeeSkillController#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * to verify if the method will return Http Status Not Found(404) when the
     * employee's skill with the specific employee's id and skill's id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsByEmployeeAndSkill_NotFound_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code getEmployeeSkillsByEmployeeAndSkill_NotFound_ExpectNotFound} is a test
     * on
     * {@link EmployeeSkillController#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * to verify if the method will return the employee's skill with the specific
     * employee's id and skill's id with Http Status OK(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployeeSkillsByEmployeeAndSkill_Found_ExpectOKFound() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        employeeRepository.save(employee);
        Skill skill = TestSkill.createSkill();
        skillRepository.save(skill);
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);
        employeeSkillRepository.save(employeeSkill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController)
                .setMessageConverters(TestJsonConverter.messageConverter).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employeeSkill)));
    }

    /**
     * {@code createEmployeeSkill_SameID_ExpectConflict} is a test on
     * {@link EmployeeSkillController#createEmployeeSkill(String, EmployeeSkill)} to
     * verify if the method will return Http Status Conflict(409) when a employee's
     * skill with the same id already exists.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployeeSkill_SameID_ExpectConflict() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        employeeRepository.save(employee);
        Skill skill = TestSkill.createSkill();
        skillRepository.save(skill);
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);
        employeeSkillRepository.save(employeeSkill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    /**
     * {@code createEmployeeSkill_MissingCompany_ExpectNotFound} is a test on
     * {@link EmployeeSkillController#createEmployeeSkill(String, EmployeeSkill)} to
     * verify if the method will return Http Status Not Found(404) when the company
     * with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployeeSkill_MissingCompany_ExpectNotFound() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        employeeRepository.save(employee);
        Skill skill = TestSkill.createSkill();
        skillRepository.save(skill);
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID + "1")
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code createEmployeeSkill_New_ExpectCreatedSaved} is a test on
     * {@link EmployeeSkillController#createEmployeeSkill(String, EmployeeSkill)} to
     * verify if the method will return the created specific employee's skill with
     * Http Status Created(201) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployeeSkill_New_ExpectCreatedSaved() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        employeeRepository.save(employee);
        Skill skill = TestSkill.createSkill();
        skillRepository.save(skill);
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);
        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);

        MockMvcBuilders.standaloneSetup(employeeSkillController)
                .setMessageConverters(TestJsonConverter.messageConverter).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employeeSkill)));
    }

    /**
     * {@code updateEmployeeSkill_MissingCompany_ExpectNotFound} is a test on
     * {@link EmployeeSkillController#updateEmployeeSkill(String, String, String, EmployeeSkill)}
     * to verify if the method will return Http Status Not Found(404) when the
     * company with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployeeSkill_MissingCompany_ExpectNotFound() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        Skill skill = TestSkill.createSkill();
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code updateEmployeeSkill_NotFound_ExpectNotFound} is a test on
     * {@link EmployeeSkillController#updateEmployeeSkill(String, String, String, EmployeeSkill)}
     * to verify if the method will return Http Status Not Found(404) when the
     * employee's skill with the specific employee's id and skill's id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployeeSkill_NotFound_ExpectNotFound() throws Exception {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        Company company = companyRepository.save(TestCompany.createCompany());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID)
                .param(TestEmployeeSkill.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code updateEmployeeSkill_Updated_ExpectOKUpdated} is a test on
     * {@link EmployeeSkillController#updateEmployeeSkill(String, String, String, EmployeeSkill)}
     * to verify if the method will link
     * EmployeeSkillService#updateEmployeeSkill(EmployeeSkillKey, EmployeeSkill)}
     * and return the updated specific employee's skill with Http Status OK(200) and
     * content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployeeSkill_Updated_ExpectOKUpdated() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        employeeRepository.save(employee);
        Skill skill = TestSkill.createSkill();
        skillRepository.save(skill);
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID)
                .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employeeSkill)).contentType(MediaType.APPLICATION_JSON);
        double cost = employeeSkill.getCost();
        employeeSkill.setCost(cost + 1);
        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);
        employeeSkillRepository.save(employeeSkill);
        employeeSkill.setCost(cost);

        MockMvcBuilders.standaloneSetup(employeeSkillController)
                .setMessageConverters(TestJsonConverter.messageConverter).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employeeSkill)));
    }

    /**
     * {@code deleteEmployeeSkill_NotFound_ExpectNotFound} is a test on
     * {@link EmployeeSkillController#deleteEmployeeSkill(String, String)} to verify
     * if the method will return Http Status Not Found(404) when the employee's
     * skill with the specific employee's id and skill's id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteEmployeeSkill_NotFound_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, TestEmployee.TEST_ID)
                .param(TestEmployeeSkill.SKILL_ID_KEY, TestSkill.TEST_ID);
        ;

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code deleteCompany_Deleted_ExpectOK} is a test on
     * {@link EmployeeSkillController#deleteEmployeeSkill(String, String)} to verify
     * if the method will return Http Status OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteEmployeeSkill_Deleted_ExpectOK() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        employeeRepository.save(employee);
        Skill skill = TestSkill.createSkill();
        skillRepository.save(skill);
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        employeeSkill.setEmployee(employee);
        employeeSkill.setSkill(skill);
        employeeSkillRepository.save(employeeSkill);
        EmployeeSkillKey key = employeeSkill.getId();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TestEmployeeSkill.URL_EXTENSION)
                .param(TestEmployeeSkill.EMP_ID_KEY, key.getEmployee())
                .param(TestEmployeeSkill.SKILL_ID_KEY, key.getSkill());
        ;

        MockMvcBuilders.standaloneSetup(employeeSkillController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * {@code collate_Found_ExpectOKFound} is a test on
     * {@link EmployeeSkillController#collate(String)} to verify if the method will
     * return with Http Status Ok(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void collate_Found_ExpectOKFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestEmployeeSkill.URL_EXTENSION_COLLATE)
                        .param(TestEmployeeSkill.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeSkillController)
                .setMessageConverters(TestJsonConverter.messageConverter).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}