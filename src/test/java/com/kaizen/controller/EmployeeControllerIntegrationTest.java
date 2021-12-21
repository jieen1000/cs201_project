package com.kaizen.controller;

import java.util.ArrayList;
import java.util.List;

import com.kaizen.model.*;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;
import com.kaizen.repository.CompanyRepository;
import com.kaizen.repository.EmployeeRepository;

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
 * {@code EmployeeControllerIntegrationTest} is a test class to do integration
 * testing from {@link EmployeeController} using H2 embeded database.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-06
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeControllerIntegrationTest {
    /**
     * The employee's controller used for testing.
     */
    @Autowired
    private EmployeeController employeeController;

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
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    /**
     * {@code getEmployees_MissingCompany_ExpectNotFound} is a test on
     * {@link EmployeeController#getEmployees(String)} to verify if the method will
     * return Http Status Not Found(404) when the company with the specific id is
     * not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployees_MissingCompany_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployee.URL_EXTENSION)
                .param(TestEmployee.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code getEmployees_Found_ExpectOKFound} is a test on
     * {@link EmployeeController#getEmployees(String)} to verify if the method will
     * return the list of all employee DTOs of a company with Http Status Ok(200)
     * and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployees_Found_ExpectOKFound() throws Exception {
        companyRepository.save(TestCompany.createCompany());
        List<Employee> employees = new ArrayList<>();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployee.URL_EXTENSION)
                .param(TestEmployee.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employees)));
    }

    /**
     * {@code getEmployee_NotFound_ExpectNotFound} is a test on
     * {@link EmployeeController#getEmployee(String)} to verify if the method will
     * return Http Status Not Found(404) when the specific employee is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployee_NotFound_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestEmployee.URL_EXTENSION_SPECIFIC).param(TestEmployee.ID_KEY, TestEmployee.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code getEmployee_Found_ExpectOKFound} is a test on
     * {@link EmployeeController#getEmployee(String)} to verify if the method will
     * return the employee with the specific id with Http Status OK(200) and content
     * type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployee_Found_ExpectOKFound() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        employeeRepository.save(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestEmployee.URL_EXTENSION_SPECIFIC)
                .param(TestEmployee.ID_KEY, employee.getWorkPermitNumber());

        MockMvcBuilders.standaloneSetup(employeeController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employee)));
    }

    /**
     * {@code createEmployee_MissingCompany_ExpectNotFound} is a test on
     * {@link EmployeeController#createEmployee(String, Employee)} to verify if the
     * method will return Http Status Not Found(404) when the company with the
     * specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployee_MissingCompany_ExpectNotFound() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployee.URL_EXTENSION)
                .param(TestEmployee.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code createEmployee_SameID_ExpectConflict} is a test on
     * {@link EmployeeController#createEmployee(String, Employee)} to verify if the
     * method will return Http Status Conflict(409) when a company with the same id
     * already exists.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployee_SameID_ExpectConflict() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        employeeRepository.save(employee);
        employee.setCompany(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployee.URL_EXTENSION)
                .param(TestEmployee.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    /**
     * {@code createEmployee_New_ExpectCreatedSaved} is a test on
     * {@link EmployeeController#createEmployee(Employee)} to verify if the method
     * will return the created specific employee with Http Status Created(201) and
     * content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createEmployee_New_ExpectCreatedSaved() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        companyRepository.save(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployee.URL_EXTENSION)
                .param(TestEmployee.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employee)));
    }

    /**
     * {@code updateEmployee_MissingCompany_ExpectNotFound} is a test on
     * {@link EmployeeController#updateEmployee(String, String, Employee)} to verify
     * if the method will return Http Status Not Found(404) when the company with
     * the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployee_MissingCompany_ExpectNotFound() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestEmployee.URL_EXTENSION + employee.getWorkPermitNumber())
                .param(TestEmployee.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code updateEmployee_NotFound_ExpectNotFound} is a test on
     * {@link EmployeeController#updateEmployee(String, String, Employee)} to verify
     * if the method will return Http Status Not Found(404) when the employee with
     * the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployee_NotFound_ExpectNotFound() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        companyRepository.save(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestEmployee.URL_EXTENSION + employee.getWorkPermitNumber())
                .param(TestEmployee.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code updateEmployee_Updated_ExpectOKUpdated} is a test on
     * {@link EmployeeController#updateEmployee(String, String, Employee)} to verify
     * if the method will return the updated specific employee with Http Status
     * OK(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployee_Updated_ExpectOKUpdated() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        String name = employee.getName();
        employee.setName("Blah blah");
        employeeRepository.save(employee);
        employee.setName(name);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestEmployee.URL_EXTENSION + employee.getWorkPermitNumber())
                .param(TestEmployee.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeController).setMessageConverters(TestJsonConverter.messageConverter)
                .build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employee)));
    }

    /**
     * {@code deleteEmployee_NotFound_ExpectNotFound} is a test on
     * {@link EmployeeController#deleteEmployee(String)} to verify if the method
     * will return Http Status Not Found(404) when the employee with the specific id
     * is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteEmployee_NotFound_ExpectNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestEmployee.URL_EXTENSION + TestEmployee.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code deleteEmployee_Deleted_ExpectOK} is a test on
     * {@link EmployeeController#deleteEmployee(String)} to verify if the method
     * will return Http Status OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteEmployee_Deleted_ExpectOK() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        employee.setCompany(company);
        companyRepository.save(company);
        employeeRepository.save(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestEmployee.URL_EXTENSION + employee.getWorkPermitNumber());

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}