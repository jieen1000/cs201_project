package com.kaizen.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.kaizen.exceptions.*;
import com.kaizen.model.TestCompany;
import com.kaizen.model.TestEmployee;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;
import com.kaizen.repository.EmployeeRepository;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.employee.EmployeeService;
import com.kaizen.service.employee.EmployeeServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code EmployeeServiceTest} is a test class to do unit testing on
 * {@link EmployeeService} using {@link EmployeeServiceImpl}.
 *
 * @author Gregory Koh
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-11-06
 */
@ContextConfiguration(classes = { EmployeeServiceImpl.class })
@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    /**
     * The mocked employee's repository used for testing.
     */
    @MockBean
    private EmployeeRepository employeeRepository;

    /**
     * The company's repository used for testing.
     */
    @MockBean
    private CompanyService companyService;

    /**
     * The employee's service used for testing.
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * {@code listEmployeesByCompany_Found_ReturnFound} is a test on
     * {@link EmployeeService#listEmployeesByCompany()} to verify if the method will
     * call {@link CompanyService#getCompany(String)} and throw
     * {@link NullValueException} when the specific company id is null.
     */
    @Test
    void listEmployeesByCompany_Null_ThrowNullValueException() {
        when(companyService.getCompany(null)).thenThrow(new NullValueException());

        assertThrows(NullValueException.class, () -> {
            employeeService.listEmployeesByCompany(null);
        });

        verify(companyService).getCompany(null);
    }

    /**
     * {@code listEmployeesByCompany_Found_ReturnFound} is a test on
     * {@link EmployeeService#listEmployeesByCompany()} to verify if the method will
     * call {@link CompanyService#getCompany(String)} and throw
     * {@link ObjectNotExistsException} when the company with the specific id does not exist.
     */
    @Test
    void listEmployeesByCompany_NotFound_ThrowObjectNotExistsException() {
        when(companyService.getCompany(any(String.class))).thenThrow(new ObjectNotExistsException());

        assertThrows(ObjectNotExistsException.class, () -> {
            employeeService.listEmployeesByCompany(TestCompany.TEST_ID);
        });

        verify(companyService).getCompany(any(String.class));
    }

    /**
     * {@code listEmployeesByCompany_Found_ReturnFound} is a test on
     * {@link EmployeeService#listEmployeesByCompany()} to verify if the method will
     * call {@link CompanyService#getCompany(String)} and
     * {@link EmployeeRepository#findByCompany(Company)} and return the list of all
     * employees.
     */
    @Test
    void listEmployeesByCompany_Found_ReturnFound() {
        List<Employee> employees = new ArrayList<>();
        when(companyService.getCompany(any(String.class))).thenReturn(TestCompany.createCompany());
        when(employeeRepository.findByCompany(any(Company.class))).thenReturn(employees);

        List<Employee> foundEmployees = employeeService.listEmployeesByCompany(TestCompany.TEST_ID);

        assertSame(employees, foundEmployees);
        verify(companyService).getCompany(any(String.class));
        verify(employeeRepository).findByCompany(any(Company.class));
    }

    /**
     * {@code getEmployee_Null_ThrowNullValueException} is a test on
     * {@link EmployeeService#getEmployee(String)} to verify if the method will throw
     * {@link NullValueException} when the specific id is null.
     */
    @Test
    void getEmployee_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeService.getEmployee(null);
        });
    }

    /**
     * {@code getEmployee_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link EmployeeService#getEmployee(String)} to verify if the method will call
     * {@link EmployeeRepository#findById(String)} and throw
     * {@link ObjectNotExistsException} when the employee with the specific id does
     * not exists.
     */
    @Test
    void getEmployee_NotFound_ThrowObjectNotExistsException() {
        when(employeeRepository.findById(TestEmployee.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            employeeService.getEmployee(TestEmployee.TEST_ID);
        });

        verify(employeeRepository).findById(TestEmployee.TEST_ID);
    }

    /**
     * {@code getEmployee_Found_ReturnFound} is a test on
     * {@link EmployeeService#getEmployee(String)} to verify if the method will call
     * {@link EmployeeRepository#findById(String)} and return the employee with the
     * specific id.
     */
    @Test
    void getEmployee_Found_ReturnFound() {
        Employee employee = TestEmployee.createEmployee();
        when(employeeRepository.findById(employee.getWorkPermitNumber())).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.getEmployee(employee.getWorkPermitNumber());

        assertSame(employee, foundEmployee);
        verify(employeeRepository).findById(employee.getWorkPermitNumber());
    }

    /**
     * {@code addEmployee_Null_ThrowNullValueException} is a test on
     * {@link EmployeeService#addEmployee(Employee)} to verify if the method will throw
     * {@link NullValueException} when the specific employee is null.
     */
    @Test
    void addEmployee_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeService.addEmployee(null);
        });
    }

    /**
     * {@code addEmployee_SameID_ThrowObjectExistsException} is a test on
     * {@link EmployeeService#addEmployee(Employee)} to verify if the method will call
     * {@link EmployeeRepository#findById(String)} and throw
     * {@link ObjectExistsException} when the id of the specific employee exists.
     */
    @Test
    void addEmployee_SameID_ThrowObjectExistsException() {
        Employee employee = TestEmployee.createEmployee();
        when(employeeRepository.findById(employee.getWorkPermitNumber())).thenReturn(Optional.of(employee));

        assertThrows(ObjectExistsException.class, () -> {
            employeeService.addEmployee(employee);
        });

        verify(employeeRepository).findById(employee.getWorkPermitNumber());
    }

    /**
     * {@code addEmployee_New_ReturnSaved} is a test on
     * {@link EmployeeService#addEmployee(Employee)} to verify if the method will call
     * {@link EmployeeRepository#findById(String)} and
     * {@link EmployeeRepository#save(Employee)} and save and return the specific
     * employee.
     */
    @Test
    void addEmployee_New_ReturnSaved() {
        Employee employee = TestEmployee.createEmployee();
        when(employeeRepository.findById(employee.getWorkPermitNumber())).thenReturn(Optional.empty());
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeService.addEmployee(employee);

        assertSame(employee, savedEmployee);
        verify(employeeRepository).findById(employee.getWorkPermitNumber());
        verify(employeeRepository).save(employee);
    }

    /**
     * {@code updateEmployee_NullId_ThrowNullValueException} is a test on
     * {@link EmployeeService#updateEmployee(String, Employee)} to verify if the method
     * will throw {@link NullValueException} when the specific id is null.
     */
    @Test
    void updateEmployee_NullId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeService.updateEmployee(null, TestEmployee.createEmployee());
        });
    }

    /**
     * {@code updateEmployee_NullUpdatedEmployee_ThrowNullValueException} is a test on
     * {@link EmployeeService#updateEmployee(String, Employee)} to verify if the method
     * will throw {@link NullValueException} when the specific employee is null
     *.
     */
    @Test
    void updateEmployee_NullUpdatedEmployee_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeService.updateEmployee(TestEmployee.TEST_ID, null);
        });
    }

    /**
     * {@code updateEmployee_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link EmployeeService#updateEmployee(String, Employee)} to verify if the method
     * will call {@link EmployeeRepository#findById(String)} and throw
     * {@link ObjectNotExistsException} when the employee with the specific id does
     * not exists.
     */
    @Test
    void updateEmployee_NotFound_ThrowObjectNotExistsException() {
        when(employeeRepository.findById(TestEmployee.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            employeeService.updateEmployee(TestEmployee.TEST_ID, TestEmployee.createEmployee());
        });

        verify(employeeRepository).findById(TestEmployee.TEST_ID);
    }

    /**
     * {@code updateEmployee_Updated_ReturnUpdated} is a test on
     * {@link EmployeeService#updateEmployee(String, Employee)} to verify if the method
     * will call {@link EmployeeRepository#findById(String)} and
     * {@link EmployeeRepository#save(Employee)} and save and return the specific
     * employee.
     */
    @Test
    void updateEmployee_Updated_ReturnUpdated() {
        Employee employee = TestEmployee.createEmployee();
        when(employeeRepository.findById(employee.getWorkPermitNumber())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee updatedEmployee = employeeService.updateEmployee(employee.getWorkPermitNumber(), employee);

        assertSame(employee, updatedEmployee);
        verify(employeeRepository).findById(employee.getWorkPermitNumber());
        verify(employeeRepository).save(employee);
    }

    /**
     * {@code deleteEmployee_NullId_ThrowNullValueException} is a test on
     * {@link EmployeeService#deleteEmployee(String)} to verify if the method will
     * throw {@link NullValueException} when the specific id is null.
     */
    @Test
    void deleteEmployee_NullId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeService.deleteEmployee(null);
        });
    }

    /**
     * {@code deleteEmployee_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link EmployeeService#deleteEmployee(String)} to verify if the method will
     * call {@link EmployeeRepository#findById(String)} and throw
     * {@link ObjectNotExistsException} when the employee with the specific id does
     * not exists.
     */
    @Test
    void deleteEmployee_NotFound_ThrowObjectNotExistsException() {
        when(employeeRepository.findById(TestEmployee.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            employeeService.deleteEmployee(TestEmployee.TEST_ID);
        });

        verify(employeeRepository).findById(TestEmployee.TEST_ID);
    }

    /**
     * {@code deleteEmployee_Deleted} is a test on
     * {@link EmployeeService#deleteEmployee(String)} to verify if the method will
     * call {@link EmployeeRepository#findById(String)} and
     * {@link EmployeeRepository#deleteById(String)} and delete the employee with
     * specific id.
     */
    @Test
    void deleteEmployee_Deleted() {
        when(employeeRepository.findById(TestEmployee.TEST_ID)).thenReturn(Optional.of(TestEmployee.createEmployee()));
        doNothing().when(employeeRepository).deleteById(TestEmployee.TEST_ID);

        employeeService.deleteEmployee(TestEmployee.TEST_ID);

        verify(employeeRepository).findById(TestEmployee.TEST_ID);
        verify(employeeRepository).deleteById(TestEmployee.TEST_ID);
    }
}
