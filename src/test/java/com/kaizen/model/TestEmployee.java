package com.kaizen.model;

import java.time.LocalDate;

import com.kaizen.model.dto.EmployeeDTO;
import com.kaizen.model.entity.Employee;

/**
 * {@code TestEmployee} is a mock class that stored configurations needed to do
 * testing related to {@link Employee} and {@link EmployeeController}.
 *
 * @author Gregory Koh
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-11-10
 */
public class TestEmployee {
    /**
     * Represents the employee's id used for testing.
     */
    public final static String TEST_ID = "0123456789";

    /**
     * Represents the URL extension of the employee's API endpoint.
     */
    public final static String URL_EXTENSION = "/api/employees/";

    /**
     * Represents the URL extension of the employee's API endpoint to get specific employee.
     */
    public final static String URL_EXTENSION_SPECIFIC = URL_EXTENSION + "specific";

    /**
     * Represents the company's id key that used in employee's API call.
     */
    public final static String COMP_ID_KEY = "compId";

    /**
     * Represents the id key that used in employee's API call.
     */
    public final static String ID_KEY = "id";

    /**
     * Create an employee with necessary fields filled to use for testing.
     * 
     * @return the employee to use for testing.
     */
    public static Employee createEmployee() {
        Employee employee = new Employee();
        employee.setWorkPermitNumber(TEST_ID);
        employee.setName("Name");
        employee.setPassportNumber("0123456789");
        employee.setWorkId("0123456789");
        employee.setEmployeeRole("Employee Role");
        employee.setLevy(400);
        employee.setWorkPermitDateOfIssue(LocalDate.ofEpochDay(1L));
        employee.setWorkPermitExpiryDate(LocalDate.now().plusDays(1));
        employee.setWorkContactNumber("91929394");
        employee.setWorkSiteLocation("Work Site Location");
        employee.setSingaporeAddress("42 Main St");
        employee.setVaccStatus(true);
        employee.setCompany(TestCompany.createCompany());
        return employee;
    }

    /**
     * Create an employee DTO with necessary fields filled to use for testing.
     * 
     * @return the employee DTO to use for testing.
     */
    public static EmployeeDTO createEmployeeDTO() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        Employee employee = createEmployee();
        employeeDTO.setWorkPermitNumber(employee.getWorkPermitNumber());
        employeeDTO.setName(employee.getName());
        employeeDTO.setPassportNumber(employee.getPassportNumber());
        employeeDTO.setWorkId(employee.getWorkId());
        employeeDTO.setEmployeeRole(employee.getEmployeeRole());
        employeeDTO.setLevy(employee.getLevy());
        employeeDTO.setWorkPermitDateOfIssue(employee.getWorkPermitDateOfIssue());
        employeeDTO.setWorkPermitExpiryDate(employee.getWorkPermitExpiryDate());
        employeeDTO.setWorkContactNumber(employee.getWorkContactNumber());
        employeeDTO.setWorkSiteLocation(employee.getWorkSiteLocation());
        employeeDTO.setSingaporeAddress(employee.getSingaporeAddress());
        employeeDTO.setVaccStatus(employee.isVaccStatus());
        employeeDTO.setProfileURL(TestImage.DEFAULT_IMAGE_URL);
        employeeDTO.setCompany(employee.getCompany().getName());
        return employeeDTO;
    }
}
