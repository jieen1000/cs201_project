package com.kaizen.service.employee;

import org.springframework.stereotype.Service;

import java.util.List;

import com.kaizen.exceptions.*;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;
import com.kaizen.repository.CompanyRepository;
import com.kaizen.repository.EmployeeRepository;
import com.kaizen.service.company.CompanyService;

/**
 * {@code EmployeeServiceImpl} is an implementation of {@code EmployeeService}.
 *
 * @author Teo Keng Swee
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-27
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    /**
     * The employee's repository that stored employees.
     */
    private final EmployeeRepository employeeRepository;

    /**
     * The company's repository that stored employees.
     */
    private final CompanyService companyService;

    /**
     * Represents the simple name of the Employee's class.
     */
    private final String EMPLOYEE_SIMPLE_NAME;

    /**
     * Create a employee's service implementation with the specific employee's
     * repository and set the {@code EMPLOYEE_SIMPLE_NAME} with the simple name of
     * the Employee's class
     * 
     * @param employeeRepository the employee's repository used by the application.
     * @param companyService the company's repository used by the application.
     */
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CompanyService companyService) {
        this.employeeRepository = employeeRepository;
        this.companyService = companyService;
        EMPLOYEE_SIMPLE_NAME = Employee.class.getSimpleName();
    }

    /**
     * Get all employees from a specific company stored in the repository.
     * 
     * @return the list of all employees from a specific company.
     */
    @Override
    public List<Employee> listEmployeesByCompany(String compId) {
        Company com = companyService.getCompany(compId);
        return employeeRepository.findByCompany(com);
    }

    /**
     * Get the employee with the specific id from the repository.
     * 
     * @param id the id of the employee.
     * @exception NullValueException       If the given id is null.
     * @exception ObjectNotExistsException If the employee is not in the repository.
     * @return the employee with that id.
     */
    @Override
    public Employee getEmployee(String id) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotExistsException(EMPLOYEE_SIMPLE_NAME, id));
    }

    /**
     * Create the specific employee in the repository.
     * 
     * @param employee the employee to create.
     * @exception NullValueException    If the id of the employee is null.
     * @exception ObjectExistsException If the employee is in the repository.
     * @return the created employee.
     */
    @Override
    public Employee addEmployee(Employee employee) throws NullValueException, ObjectExistsException {
        validateEmployeeNotNull(employee);
        validateIdNotNull(employee.getWorkPermitNumber());
        if (employeeRepository.findById(employee.getWorkPermitNumber()).isPresent()) {
            throw new ObjectExistsException(EMPLOYEE_SIMPLE_NAME, employee.getWorkPermitNumber());
        }
        return employeeRepository.save(employee);
    }

    /**
     * Update the employee with the specific id and employee in the repository.
     * 
     * @param id       the id of the employee to update.
     * @param employee the employee to update.
     * @exception NullValueException       If the id of the employee is null or the
     *                                     employee is null.
     * @exception ObjectNotExistsException If the employee is not in the repository.
     * @return the updated employee.
     */
    @Override
    public Employee updateEmployee(String id, Employee employee) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        validateEmployeeNotNull(employee);
        validateEmployeeExists(id);
        return employeeRepository.save(employee);
    }

    /**
     * Delete the employee with the specific id in the repository.
     * 
     * @param id the id of the employee to delete.
     * @exception NullValueException       If the id of the employee is null or the
     *                                     employee is null.
     * @exception ObjectNotExistsException If the employee is not in the repository.
     */
    @Override
    public void deleteEmployee(String id) throws NullValueException, ObjectExistsException {
        validateIdNotNull(id);
        validateEmployeeExists(id);
        employeeRepository.deleteById(id);
    }

    /**
     * Validate the specific id is not null.
     * 
     * @param id the id of the employee to validate.
     * @exception NullValueException If the id of the employee is null.
     */
    private void validateIdNotNull(String id) throws NullValueException {
        if (id == null) {
            throw new NullValueException(EMPLOYEE_SIMPLE_NAME + "'s id");
        }
    }

    /**
     * Validate the specific employee is not null.
     * 
     * @param employee the employee to validate.
     * @exception NullValueException If the employee is null.
     */
    private void validateEmployeeNotNull(Employee employee) throws NullValueException {
        if (employee == null) {
            throw new NullValueException(EMPLOYEE_SIMPLE_NAME);
        }
    }

    /**
     * Validate the employee with the specific id is in the repository.
     * 
     * @param id the id of the employee to validate.
     * @exception ObjectNotExistsException If the employee is not in the repository.
     */
    private void validateEmployeeExists(String id) throws ObjectNotExistsException {
        if (employeeRepository.findById(id).isEmpty()) {
            throw new ObjectNotExistsException(EMPLOYEE_SIMPLE_NAME, id);
        }
    }
}