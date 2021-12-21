package com.kaizen.service.employeeSkill;

import com.kaizen.exceptions.*;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.EmployeeSkill;
import com.kaizen.model.entity.EmployeeSkillKey;
import com.kaizen.repository.EmployeeSkillRepository;
import com.kaizen.service.company.CompanyService;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * {@code EmployeeSkillServiceImpl} is an implementation of
 * {@code EmployeeSkillService}.
 *
 * @author Chong Zhan Han
 * @author Gregory Koh
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-10-16
 */
@Service
public class EmployeeSkillServiceImpl implements EmployeeSkillService {
    /**
     * The employee's skill's repository that stored skills.
     */
    private final EmployeeSkillRepository employeeSkillRepository;
    private final CompanyService companyService;

    /**
     * Represents the simple name of the EmployeeSkill's class.
     */
    private final String EMPLOYEESKILL_SIMPLE_NAME;

    /**
     * Create a employee's skill's service implementation with the specific
     * employee's skill's repository and set the {@code EMPLOYEESKILL_SIMPLE_NAME}
     * with the simple name of the Skill's class
     * 
     * @param employeeSkillRepository the employee's skill's repository used by the
     *                                application.
     */
    public EmployeeSkillServiceImpl(EmployeeSkillRepository employeeSkillRepository, CompanyService companyService) {
        this.employeeSkillRepository = employeeSkillRepository;
        this.companyService = companyService;
        EMPLOYEESKILL_SIMPLE_NAME = EmployeeSkill.class.getSimpleName();
    }

    /**
     * Get all employee's skills stored in the repository.
     * 
     * @return the list of all employee's skills.
     */
    @Override
    public List<EmployeeSkill> listEmployeeSkills() {
        return employeeSkillRepository.findAll();
    }

    /**
     * Get the employee's skill with the specific id from the repository.
     * 
     * @param id the id of the employee's skill.
     * @exception NullValueException       If the id of the employee's skill is null.
     * @exception ObjectNotExistsException If the employee's skill is not in the repository.
     * @return the employee's skill with that id.
     */
    @Override
    public EmployeeSkill getEmployeeSkill(EmployeeSkillKey id) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        return employeeSkillRepository.findById(id)
                .orElseThrow(() -> new ObjectNotExistsException(EMPLOYEESKILL_SIMPLE_NAME, id.toString()));
    }

    /**
     * Get all employee's skills of the employee with the specific employee's id
     * from the repository.
     * 
     * @param empId the id of the employee.
     * @exception NullValueException If the id of the employee is null.
     * @return the employee's skill with that id.
     */
    @Override
    public List<EmployeeSkill> getEmployeeSkillsByEmployee(String employeeId) throws NullValueException {
        if (employeeId == null) {
            throw new NullValueException("Employee's Id");
        }
        return employeeSkillRepository.findEmployeeSkillsByIdEmployee(employeeId);
    }

    /**
     * Get all employee's skills of the skill with the specific skill's id from the
     * repository.
     * 
     * @param skillId the id of the skill.
     * @exception NullValueException If the id of the skill is null.
     * @return the employee's skill with that id.
     */
    @Override
    public List<EmployeeSkill> getEmployeeSkillsBySkill(String skillId) throws NullValueException {
        if (skillId == null) {
            throw new NullValueException("Skill's Id");
        }
        return employeeSkillRepository.findEmployeeSkillsByIdSkill(skillId);
    }

    /**
     * Get the employee's skill with the specific employee's id and skill's id from
     * the repository.
     * 
     * @param empId   the id of the employee.
     * @param skillId the id of the skill.
     * @exception NullValueException If the id of the employee or the id of the
     *                               skill is null.
     * @return the employee's skill with that id.
     */
    @Override
    public EmployeeSkill getEmployeeSkillByEmployeeAndSkill(String employeeId, String skillId)
            throws NullValueException, ObjectNotExistsException {
        if (employeeId == null) {
            throw new NullValueException("Employee's Id");
        } else if (skillId == null) {
            throw new NullValueException("Skill's Id");
        }
        EmployeeSkill employeeSkill = employeeSkillRepository.findEmployeeSkillByIdEmployeeAndIdSkill(employeeId,
                skillId);
        if (employeeSkill == null) {
            throw new ObjectNotExistsException(EMPLOYEESKILL_SIMPLE_NAME,
                    "(Employee's Id: " + employeeId + ", Skill's Id: " + skillId + ")");
        }
        return employeeSkill;
    }

    /**
     * Create the specific employee's skill in the repository.
     * 
     * @param employeeSkill the employee's skill to create.
     * @exception NullValueException    If the id of the employee's skill is null.
     * @exception ObjectExistsException If the employee's skill is in the
     *                                  repository.
     * @return the created employee's skill.
     */
    @Override
    public EmployeeSkill addEmployeeSkill(EmployeeSkill employeeSkill) {
        validateEmployeeSkillNotNull(employeeSkill);
        validateIdNotNull(employeeSkill.getId());
        if (employeeSkillRepository.findById(employeeSkill.getId()).isPresent()) {
            throw new ObjectExistsException(EMPLOYEESKILL_SIMPLE_NAME, employeeSkill.getId().toString());
        }
        return employeeSkillRepository.save(employeeSkill);
    }

    /**
     * Update the employee's skill with the specific id and employee's skill in the
     * repository.
     * 
     * @param id            the id of the employee's skill to update.
     * @param employeeSkill the employee's skill to update.
     * @exception NullValueException       If the id of the employee's skill is null
     *                                     or the skill is null.
     * @exception ObjectNotExistsException If the employee's skill is not in the
     *                                     repository.
     * @return the updated employee's skill.
     */
    @Override
    public EmployeeSkill updateEmployeeSkill(EmployeeSkillKey id, EmployeeSkill employeeSkill)
            throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        validateEmployeeSkillNotNull(employeeSkill);
        validateEmployeeSkillExists(id);
        return employeeSkillRepository.save(employeeSkill);
    }

    /**
     * Delete the employee's skill with the specific id in the repository.
     * 
     * @param id the id of the employee's skill to delete.
     * @exception NullValueException       If the id of the employee's skill is
     *                                     null.
     * @exception ObjectNotExistsException If the employee's skill is not in the
     *                                     repository.
     */
    @Override
    public void deleteEmployeeSkill(EmployeeSkillKey id) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        validateEmployeeSkillExists(id);
        employeeSkillRepository.deleteById(id);
    }

    /**
     * Validate the specific id is not null.
     * 
     * @param id the id of the employee's skill to validate.
     * @exception NullValueException If the id of the employee's skill is null.
     */
    private void validateIdNotNull(EmployeeSkillKey id) throws NullValueException {
        if (id == null) {
            throw new NullValueException(EMPLOYEESKILL_SIMPLE_NAME + "'s id");
        }
    }

    /**
     * Validate the specific employee's skill is not null.
     * 
     * @param emmployeeSkill the employee's skill to validate.
     * @exception NullValueException If the employee's skill is null.
     */
    private void validateEmployeeSkillNotNull(EmployeeSkill employeeSkill) throws NullValueException {
        if (employeeSkill == null) {
            throw new NullValueException(EMPLOYEESKILL_SIMPLE_NAME);
        }
    }

    /**
     * Validate the employee's skill with the specific id is in the repository.
     * 
     * @param id the id of the employee's skill to validate.
     * @exception ObjectNotExistsException If the employee's skill is not in the
     *                                     repository.
     */
    private void validateEmployeeSkillExists(EmployeeSkillKey id) throws ObjectNotExistsException {
        if (employeeSkillRepository.findById(id).isEmpty()) {
            throw new ObjectNotExistsException(EMPLOYEESKILL_SIMPLE_NAME, id.toString());
        }
    }

    /**
     * Get the list of list of Strings of each skill name, count of Employee under the skill 
     * and minimum cost of of the employee listed in the repository.
     * 
     * @return a List of records in a List of Strings of Skill Name, count of employees under the skill 
     * and the minimum cost of the skill
     * @param compId the id of the company.
     */
    public List<List<String>> collate(String compId){
        return employeeSkillRepository.findEmployeeSkillCountAndMinCost(compId);
    }
    

    /**
     * Get all employee's skills of the employee with the specific company's id
     * from the repository.
     * 
     * @param compId the id of the employee.
     * @return the list of employee's skill from the specified company.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     */
    @Override
    public List<EmployeeSkill> listEmployeeSkillsByCompany(String compId) throws NullValueException, ObjectNotExistsException {
        Company com = companyService.getCompany(compId);
        return employeeSkillRepository.findByCompany(com);    
    }
}