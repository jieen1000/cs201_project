package com.kaizen.service.employeeSkill;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.entity.EmployeeSkill;
import com.kaizen.model.entity.EmployeeSkillKey;

import java.util.List;

/**
 * {@code EmployeeSkillService} captures what are needed for business's logic
 * for employee's skill.
 *
 * @author Chong Zhan Han
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-10-16
 */
public interface EmployeeSkillService {
    /**
     * Get all employee's skills stored in the repository.
     * 
     * @return the list of all employee's skills.
     */
    List<EmployeeSkill> listEmployeeSkills();

    /**
     * Get all employee's skills from a company stored in the repository.
     * 
     * @return the list of all employee's skills By company.
     */
    List<EmployeeSkill> listEmployeeSkillsByCompany(String compId);

    /**
     * Get the employee's skill with the specific id from the repository.
     * 
     * @param id the id of the employee's skill.
     * @exception NullValueException       If the id of the employee's skill is null.
     * @exception ObjectNotExistsException If the employee's skill is not in the repository.
     * @return the employee's skill with that id.
     */
    EmployeeSkill getEmployeeSkill(EmployeeSkillKey id) throws NullValueException, ObjectNotExistsException;

    /**
     * Get all employee's skills of the employee with the specific employee's id
     * from the repository.
     * 
     * @param empId the id of the employee.
     * @exception NullValueException If the id of the employee is null.
     * @return the employee's skill with that id.
     */
    List<EmployeeSkill> getEmployeeSkillsByEmployee(String employeeId) throws NullValueException;

    /**
     * Get all employee's skills of the skill with the specific skill's id from the
     * repository.
     * 
     * @param skillId the id of the skill.
     * @exception NullValueException If the id of the skill is null.
     * @return the employee's skill with that id.
     */
    List<EmployeeSkill> getEmployeeSkillsBySkill(String skillId) throws NullValueException;

    /**
     * Get the employee's skill with the specific employee's id and
     * skill's id from the repository.
     * 
     * @param empId   the id of the employee.
     * @param skillId the id of the skill.
     * @exception NullValueException If the id of the employee or the id of the
     *                               skill is null.
     * @return the employee's skill with that id.
     */
    EmployeeSkill getEmployeeSkillByEmployeeAndSkill(String employeeId, String skillId)
            throws NullValueException, ObjectNotExistsException;

    /**
     * Create the specific employee's skill in the repository.
     * 
     * @param employee's skill the employee's skill to create.
     * @exception NullValueException    If the id of the employee's skill is null.
     * @exception ObjectExistsException If the employee's skill is in the repository.
     * @return the created employee's skill.
     */
    EmployeeSkill addEmployeeSkill(EmployeeSkill employeeSkill) throws NullValueException, ObjectExistsException;

    /**
     * Update the employee's skill with the specific id and employee's skill in the repository.
     * 
     * @param id    the id of the employee's skill to update.
     * @param employee's skill the employee's skill to update.
     * @exception NullValueException       If the id of the employee's skill is null or the
     *                                     employee's skill is null.
     * @exception ObjectNotExistsException If the employee's skill is not in the repository.
     * @return the updated employee's skill.
     */
    EmployeeSkill updateEmployeeSkill(EmployeeSkillKey id, EmployeeSkill employeeSkill)
            throws NullValueException, ObjectNotExistsException;

    /**
     * Delete the employee's skill with the specific id in the repository.
     * 
     * @param id the id of the employee's skill to delete.
     * @exception NullValueException       If the id of the employee's skill is null.
     * @exception ObjectNotExistsException If the employee's skill is not in the repository.
     */
    void deleteEmployeeSkill(EmployeeSkillKey id) throws NullValueException, ObjectNotExistsException;


    /**
     * Get the list of list of Strings of each skill name, count of Employee under the skill 
     * and minimum cost of of the employee listed in the repository.
     * 
     * @return a List of records in a List of Strings of Skill Name, count of employees under the skill 
     * and the minimum cost of the skill
     * @param compId the id of the company.
     */
    public List<List<String>> collate(String compId);
}