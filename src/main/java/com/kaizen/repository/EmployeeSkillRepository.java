package com.kaizen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.EmployeeSkill;
import com.kaizen.model.entity.EmployeeSkillKey;

/**
 * Employee's skill specific extension of
 * {@link org.springframework.data.jpa.repository.JpaRepository}.
 *
 * @author Chong Zhan Han
 * @version 1.0
 * @since 2021-10-15
 */
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, EmployeeSkillKey> {
    /**
     * Find the employee's skills of an employee.
     *
     * @param employeeId the id of the employee to find the employee's skills.
     * @return the list of employee's skills.
     */
    List<EmployeeSkill> findEmployeeSkillsByIdEmployee(String employeeId);

    /**
     * Find the employee's skills of a skill.
     *
     * @param skillId the id of the skill to find the employee's skills.
     * @return the list of employee's skills.
     */
    List<EmployeeSkill> findEmployeeSkillsByIdSkill(String skillId);

    /**
     * Find the employee's skills of an employee and a skill.
     *
     * @param employeeId the id of the employee to find the employee's skills.
     * @param skillId    the id of the skill to find the employee's skills.
     * @return the employee's skill.
     */
    EmployeeSkill findEmployeeSkillByIdEmployeeAndIdSkill(String employeeId, String skillId);

    /**
     * Find the employee's skills of an employee and a skill.
     *
     * @return a list of list of strings for every skill the skill name , count of employee with the skill 
     * and minimum cost of the skill  
     */
    @Query(value = "select skill_id, count(0), min(cost) from (select * from employee_skill where company_uen != ?1) as t1 group by skill_id" ,nativeQuery = true)
    public List<List<String>> findEmployeeSkillCountAndMinCost(String companyUEN);


    /**
     * Find the employee's skills of an employee and a skill.
     *
     * 
     * @return a list of employee skills under a company
     */
    List<EmployeeSkill> findByCompany(Company company);
}
