package com.kaizen.service.employee;

import java.util.List;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.entity.Employee;

/**
 * {@code EmployeeService} captures what are needed for business's logic for
 * employee.
 *
 * @author Teo Keng Swee
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-27
 */
public interface EmployeeService {
  /**
   * Get all employees from a specific company stored in the repository.
   * 
   * @return the list of all employees from a specific company.
   */
  List<Employee> listEmployeesByCompany(String compId);

  /**
   * Get the employee with the specific id from the repository.
   * 
   * @param id the id of the employee.
   * @exception NullValueException       If the given id is null.
   * @exception ObjectNotExistsException If the employee is not in the repository.
   * @return the employee with that id.
   */
  Employee getEmployee(String id) throws NullValueException, ObjectNotExistsException;

  /**
   * Create the specific employee in the repository.
   * 
   * @param employee the employee to create.
   * @exception NullValueException    If the id of the employee is null.
   * @exception ObjectExistsException If the employee is in the repository.
   * @return the created employee.
   */
  Employee addEmployee(Employee employee) throws NullValueException, ObjectExistsException;

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
  Employee updateEmployee(String id, Employee employee) throws NullValueException, ObjectNotExistsException;

  /**
   * Delete the employee with the specific id in the repository.
   * 
   * @param id the id of the employee to delete.
   */
  void deleteEmployee(String id) throws NullValueException, ObjectExistsException;
}