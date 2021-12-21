package com.kaizen.controller;

import static org.mockito.Mockito.*;

import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.*;
import com.kaizen.model.dto.EmployeeDTO;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.employee.EmployeeService;
import com.kaizen.service.image.ImageService;

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
 * {@code EmployeeControllerTest} is a test class to do unit testing on
 * {@link EmployeeController}.
 *
 * @author Gregory Koh
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-11-10
 */
@ContextConfiguration(classes = { EmployeeController.class })
@ExtendWith(SpringExtension.class)
class EmployeeControllerTest {
    /**
     * The employee's controller used for testing.
     */
    @Autowired
    private EmployeeController employeeController;

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
     * The mocked image's service used for testing.
     */
    @MockBean
    private ImageService imageService;

    /**
     * {@code getEmployees_MissingCompId_ExpectBadRequest} is a test on
     * {@link EmployeeController#getEmployees(String)} to verify if the method will
     * return Http Status Bad Request(400) when the specific company's id is missing
     * in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployees_MissingCompId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployee.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code getEmployees_MissingCompany_ExpectNotFound} is a test on
     * {@link EmployeeController#getEmployees(String)} to verify if the method will
     * call {@link EmployeeService#listEmployeesByCompany(String)} and return Http
     * Status Not Found(404) when the company with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployees_MissingCompany_ExpectNotFound() throws Exception {
        when(employeeService.listEmployeesByCompany(any(String.class))).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployee.URL_EXTENSION)
                .param(TestEmployee.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(employeeService).listEmployeesByCompany(any(String.class));
    }

    /**
     * {@code getEmployees_Found_ExpectOKFound} is a test on
     * {@link EmployeeController#getEmployees(String)} to verify if the method will
     * call {@link EmployeeService#listEmployeesByCompany(String)} and
     * {@link ImageService#getProfileImageURL(Employee)} and return the list of all
     * employee DTOs of a company with Http Status Ok(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployees_Found_ExpectOKFound() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(TestEmployee.createEmployee());
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        employeeDTOs.add(TestEmployee.createEmployeeDTO());
        when(employeeService.listEmployeesByCompany(any(String.class))).thenReturn(employees);
        when(imageService.getProfileImageURL(any(Employee.class))).thenReturn(TestImage.DEFAULT_IMAGE_URL);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployee.URL_EXTENSION)
                .param(TestEmployee.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employeeDTOs)));

        verify(employeeService).listEmployeesByCompany(any(String.class));
        verify(imageService).getProfileImageURL(any(Employee.class));
    }

    /**
     * {@code getEmployee_NotFound_ExpectNotFound} is a test on
     * {@link EmployeeController#getEmployee(String)} to verify if the method will
     * call {@link EmployeeService#getEmployee(String)} and return Http Status Not
     * Found(404) when the specific employee is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployee_NotFound_ExpectNotFound() throws Exception {
        when(employeeService.getEmployee(TestEmployee.TEST_ID)).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployee.URL_EXTENSION_SPECIFIC)
                .param(TestEmployee.ID_KEY, TestEmployee.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(employeeService).getEmployee(TestEmployee.TEST_ID);
    }

    /**
     * {@code getEmployee_Found_ExpectOKFound} is a test on
     * {@link EmployeeController#getEmployee(String)} to verify if the method will
     * call {@link EmployeeService#getEmployee(String)} and return the employee with
     * the specific id with Http Status OK(200) and content type of
     * application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getEmployee_Found_ExpectOKFound() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        when(employeeService.getEmployee(employee.getWorkPermitNumber())).thenReturn(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestEmployee.URL_EXTENSION_SPECIFIC)
                .param(TestEmployee.ID_KEY, employee.getWorkPermitNumber());

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employee)));

        verify(employeeService).getEmployee(employee.getWorkPermitNumber());
    }

    /**
     * {@code addEmployee_MissingEmployee_ExpectBadRequest} is a test on
     * {@link EmployeeController#createEmployee(String, Employee)} to verify if the
     * method will return Http Status Bad Request(400) when the specific employee is
     * missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addEmployee_MissingEmployee_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployee.URL_EXTENSION)
                .param(TestEmployee.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code addEmployee_MissingCompId_ExpectBadRequest} is a test on
     * {@link EmployeeController#createEmployee(String, Employee)} to verify if the
     * method will return Http Status Bad Request(400) when the specific company's
     * id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addEmployee_MissingCompId_ExpectBadRequest() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployee.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code addCompany_InvalidCompany_ExpectBadRequest} is a test on
     * {@link EmployeeController#createEmployee(String, Employee)} to verify if the
     * method will return Http Status Bad Request(400) when the specific company is
     * invalid in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addEmployee_InvalidEmployee_ExpectBadRequest() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        employee.setName(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployee.URL_EXTENSION)
                .param(TestEmployee.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code addEmployee_MissingCompany_ExpectNotFound} is a test on
     * {@link EmployeeController#createEmployee(String, Employee)} to verify if the
     * method will call {@link CompanyService#getCompany(String)} and return Http
     * Status Not Found(404) when the company with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addEmployee_MissingCompany_ExpectNotFound() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        when(companyService.getCompany(TestCompany.TEST_ID)).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployee.URL_EXTENSION)
                .param(TestEmployee.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(TestCompany.TEST_ID);
    }

    /**
     * {@code addEmployee_SameID_ExpectConflict} is a test on
     * {@link EmployeeController#createEmployee(String, Employee)} to verify if the
     * method will call {@link CompanyService#getCompany(String)} and
     * {@link EmployeeService#addEmployee(Employee)} and return Http Status
     * Conflict(409) when a company with the same id already exists.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addEmployee_SameID_ExpectConflict() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        when(companyService.getCompany(company.getUEN())).thenReturn(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployee.URL_EXTENSION)
                .param(TestEmployee.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);
        employee.setCompany(company);
        when(employeeService.addEmployee(employee)).thenThrow(new ObjectExistsException());

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict());

        verify(companyService).getCompany(company.getUEN());
        verify(employeeService).addEmployee(employee);
    }

    /**
     * {@code addEmployee_New_ExpectCreatedSaved} is a test on
     * {@link EmployeeController#createEmployee(Employee)} to verify if the method
     * will call {@link CompanyService#getCompany(String)} and
     * {@link EmployeeService#addEmployee(Employee)} and return the created specific
     * employee with Http Status Created(201) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void addEmployee_New_ExpectCreatedSaved() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        when(companyService.getCompany(company.getUEN())).thenReturn(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestEmployee.URL_EXTENSION)
                .param(TestEmployee.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);
        employee.setCompany(company);
        when(employeeService.addEmployee(employee)).thenReturn(employee);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employee)));

        verify(companyService).getCompany(company.getUEN());
        verify(employeeService).addEmployee(employee);
    }

    /**
     * {@code updateEmployee_MissingEmployee_ExpectBadRequest} is a test on
     * {@link EmployeeController#updateEmployee(String, String, Employee)} to verify
     * if the method will return Http Status Bad Request(400) when the specific
     * employee is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployee_MissingEmployee_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestEmployee.URL_EXTENSION + TestEmployee.TEST_ID)
                .param(TestEmployee.COMP_ID_KEY, TestCompany.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateEmployee_MissingEmployee_ExpectBadRequest} is a test on
     * {@link EmployeeController#updateEmployee(String, String, Employee)} to verify
     * if the method will return Http Status Bad Request(400) when the specific
     * company's id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployee_MissingCompId_ExpectBadRequest() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestEmployee.URL_EXTENSION + employee.getWorkPermitNumber())
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateEmployee_MissingEmployee_ExpectBadRequest} is a test on
     * {@link EmployeeController#updateEmployee(String, String, Employee)} to verify
     * if the method will return Http Status Bad Request(400) when the specific
     * employee is invalid in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployee_InvalidEmployee_ExpectBadRequest() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        employee.setName(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestEmployee.URL_EXTENSION + employee.getWorkPermitNumber())
                .param(TestEmployee.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateEmployee_MissingCompany_ExpectNotFound} is a test on
     * {@link EmployeeController#updateEmployee(String, String, Employee)} to verify
     * if the method will call {@link CompanyService#getCompany(String)} and return
     * Http Status Not Found(404) when the company with the specific id is not
     * found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployee_MissingCompany_ExpectNotFound() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        when(companyService.getCompany(TestCompany.TEST_ID)).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestEmployee.URL_EXTENSION + employee.getWorkPermitNumber())
                .param(TestEmployee.COMP_ID_KEY, TestCompany.TEST_ID)
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(TestCompany.TEST_ID);
    }

    /**
     * {@code updateEmployee_NotFound_ExpectNotFound} is a test on
     * {@link EmployeeController#updateEmployee(String, String, Employee)} to verify
     * if the method will call {@link CompanyService#getCompany(String)} and
     * {@link EmployeeService#getEmployee(String)} and return Http Status Not
     * Found(404) when the employee with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployee_NotFound_ExpectNotFound() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        when(companyService.getCompany(company.getUEN())).thenReturn(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestEmployee.URL_EXTENSION + employee.getWorkPermitNumber())
                .param(TestEmployee.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);
        employee.setCompany(company);
        when(employeeService.updateEmployee(employee.getWorkPermitNumber(), employee))
                .thenThrow(new ObjectNotExistsException());

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(company.getUEN());
        verify(employeeService).updateEmployee(employee.getWorkPermitNumber(), employee);
    }

    /**
     * {@code updateEmployee_Updated_ExpectOKUpdated} is a test on
     * {@link EmployeeController#updateEmployee(String, String, Employee)} to verify
     * if the method will call {@link CompanyService#getCompany(String)} and
     * {@link EmployeeService#getEmployee(String)} and return the updated specific
     * employee with Http Status OK(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateEmployee_Updated_ExpectOKUpdated() throws Exception {
        Employee employee = TestEmployee.createEmployee();
        Company company = TestCompany.createCompany();
        when(companyService.getCompany(company.getUEN())).thenReturn(company);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestEmployee.URL_EXTENSION + employee.getWorkPermitNumber())
                .param(TestEmployee.COMP_ID_KEY, company.getUEN())
                .content(TestJsonConverter.writeValueAsString(employee)).contentType(MediaType.APPLICATION_JSON);
        employee.setCompany(company);
        when(employeeService.updateEmployee(employee.getWorkPermitNumber(), employee)).thenReturn(employee);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(employee)));

        verify(companyService).getCompany(company.getUEN());
        verify(employeeService).updateEmployee(employee.getWorkPermitNumber(), employee);
    }

    /**
     * {@code deleteEmployee_NotFound_ExpectNotFound} is a test on
     * {@link EmployeeController#deleteEmployee(String)} to verify if the method
     * will call {@link EmployeeService#deleteEmployee(String)} and return Http
     * Status Not Found(404) when the employee with the specific id is not found.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteEmployee_NotFound_ExpectNotFound() throws Exception {
        doThrow(new ObjectNotExistsException()).when(employeeService).deleteEmployee(TestEmployee.TEST_ID);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestEmployee.URL_EXTENSION + TestEmployee.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(employeeService).deleteEmployee(TestEmployee.TEST_ID);
    }

    /**
     * {@code deleteEmployee_Deleted_ExpectOK} is a test on
     * {@link EmployeeController#deleteEmployee(String)} to verify if the method
     * will call {@link EmployeeService#deleteEmployee(String)} and return Http
     * Status OK(200).
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteEmployee_Deleted_ExpectOK() throws Exception {
        doNothing().when(employeeService).deleteEmployee(TestEmployee.TEST_ID);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(TestEmployee.URL_EXTENSION + TestEmployee.TEST_ID);

        MockMvcBuilders.standaloneSetup(employeeController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(employeeService).deleteEmployee(TestEmployee.TEST_ID);
    }
}
