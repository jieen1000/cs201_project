package com.kaizen.model;

import com.kaizen.model.dto.EmployeeSkillDTO;
import com.kaizen.model.entity.*;

/**
 * {@code TestEmployeeSkill} is a mock class that stored configurations needed
 * to do testing related to {@link EmployeeSkill} and
 * {@link EmployeeSkillController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-06
 */
public class TestEmployeeSkill {
    /**
     * Represents the employee's skill's id used for testing.
     */
    public final static EmployeeSkillKey TEST_ID = new EmployeeSkillKey(TestEmployee.TEST_ID, TestSkill.TEST_ID);

    /**
     * Represents the URL extension of the employee's skill's API endpoint.
     */
    public final static String URL_EXTENSION = "/api/employeeSkills/";

    /**
     * Represents the URL extension of the employee's skill's API endpoint to get
     * all employee's skills.
     */
    public final static String URL_EXTENSION_ALL = URL_EXTENSION + "all";

    /**
     * Represents the URL extension of the employee's skill's API endpoint to get
     * collate.
     */
    public final static String URL_EXTENSION_COLLATE = URL_EXTENSION + "collate";

    /**
     * Represents the company's id key that used in employee's skill's API call.
     */
    public final static String COMP_ID_KEY = "compId";

    /**
     * Represents the employee's id key that used in employee's skill's API call.
     */
    public final static String EMP_ID_KEY = "empId";

    /**
     * Represents the skill's id key that used in employee's skill's API call.
     */
    public final static String SKILL_ID_KEY = "skillId";

    /**
     * Create an employee's skill with necessary fields filled to use for testing.
     * 
     * @return the employee's skill to use for testing.
     */
    public static EmployeeSkill createEmployeeSkill() {
        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setId(TEST_ID);
        employeeSkill.setEmployee(TestEmployee.createEmployee());
        Company company = TestCompany.createCompany();
        employeeSkill.getEmployee().setCompany(company);
        employeeSkill.setCompany(company);
        employeeSkill.setSkill(TestSkill.createSkill());
        return employeeSkill;
    }

    /**
     * Create an invalid employee's skill with necessary fields filled to use for
     * testing.
     * 
     * @return the invalid employee's skill to use for testing.
     */
    public static EmployeeSkill createInvalidEmployeeSkill() {
        EmployeeSkill employeeSkill = createEmployeeSkill();
        employeeSkill.setRating(10);
        return employeeSkill;
    }

    /**
     * Create an employee's skill DTO with necessary fields filled to use for
     * testing.
     * 
     * @return the employee's skill DTO to use for testing.
     */
    public static EmployeeSkillDTO createEmployeeSkillDTO() {
        EmployeeSkill employeeSkill = createEmployeeSkill();
        Employee employee = employeeSkill.getEmployee();
        return new EmployeeSkillDTO(employee.getWorkPermitNumber(), employee.getName(), employee.getDescription(),
                employee.getEmployeeRole(), employeeSkill.getSkill().getSkill(), employeeSkill.getExperience(),
                employeeSkill.getCost(), employeeSkill.getRating(), TestImage.DEFAULT_IMAGE_URL,
                employee.getCompany().getName(), employee.getCompany().getUEN());
    }
}
