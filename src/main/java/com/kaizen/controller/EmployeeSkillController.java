package com.kaizen.controller;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.dto.EmployeeSkillDTO;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;
import com.kaizen.model.entity.EmployeeSkill;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.company.CompanyServiceImpl;
import com.kaizen.service.employee.EmployeeService;
import com.kaizen.service.employeeSkill.EmployeeSkillService;
import com.kaizen.service.image.ImageService;
import com.kaizen.service.skill.SkillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;


import javax.validation.Valid;

/**
 * {@code EmployeeSkillController} is a rest controller for employee's skill.
 *
 * @author Teo Keng Swee
 * @author Chong Zhan Han
 * @author Gregory Koh
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/api/employeeSkills")
public class EmployeeSkillController {
    /**
     * The employee's skill's service used to do the business's logic for employee's
     * skill.
     */
    private final EmployeeSkillService employeeSkillService;
    /**
     * The company's service used to do the business's logic for employee's
     * company.
     */
    private final CompanyService companyService;

    /**
     * The image service used to do the business's logic for employee's
     * image.
     */
    private final ImageService imageService;

    /**
     * The employee's service used to do the business's logic for employee.
     */
    private final EmployeeService employeeService;
    /**
     * The skill's service used to do the business's logic for skill.
     */
    private final SkillService skillService;

    /**
     * Create an employee's skill controller with the specific employee's skill's
     * service, employee's service and skill's service.
     * 
     * @param employeeSkillService the employee's skill's service used by the
     *                             application.
     * @param employeeService      the employee's service used by the application.
     * @param skillService         the skill's service used by the application.
     * @param imageService         the image's service used by the application.
     * @param companyService         the skill's service used by the application.
     */
    @Autowired
    public EmployeeSkillController(EmployeeSkillService employeeSkillService, EmployeeService employeeService,
            SkillService skillService, ImageService imageService, CompanyService companyService) {
        this.employeeSkillService = employeeSkillService;
        this.employeeService = employeeService;
        this.skillService = skillService;
        this.imageService = imageService;
        this.companyService = companyService;
    }

    /**
     * Get all employee's skills that do not belong to a certain company through employee's skill's service.
     *
     *  @param compId the company's id.
     * @return the list of all employee's skills not from the specified company.
     */
    @GetMapping(value = "/all", params = { "compId" } )
    public List<EmployeeSkillDTO> getAllEmployeeSkillsNotFromCompany(@RequestParam String compId) {
        List<EmployeeSkillDTO> employeeSkillList = new ArrayList<>();
        for(EmployeeSkill em : employeeSkillService.listEmployeeSkills()){
            if(em.getCompany().getUEN().equals(compId)) continue;
            employeeSkillList.add(convertToDTO(em));
        }

        return employeeSkillList;
    }

    /**
     * Get all employee's skills that belongs to a certain company through employee's skill's service.
     * 
     * @param compId the company's id.
     * @return the list of all employee's skills from the specified company.
     */
    @GetMapping(params = { "compId" })
    public List<EmployeeSkillDTO> getEmployeeSkillsByCompany(@RequestParam String compId) {
        List<EmployeeSkillDTO> employeeSkillList = new ArrayList<>();
        for(EmployeeSkill em : employeeSkillService.listEmployeeSkillsByCompany(compId)){
            employeeSkillList.add(convertToDTO(em));
        }
        return employeeSkillList;
    }

    /**
     * Get all employee's skills of the employee with the specific employee's id
     * through employee's skill's service.
     * 
     * @param empId the id of the employee.
     * @exception NullValueException If the id of the employee is null.
     * @return the list of all employee's skills of the employee.
     */
    @GetMapping(params = { "empId" })
    public List<EmployeeSkill> getEmployeeSkillsByEmployee(@RequestParam String empId) throws NullValueException {
        return employeeSkillService.getEmployeeSkillsByEmployee(empId);
    }

    /**
     * Get all employee's skills of the skill with the specific skill's id through
     * employee's skill's service.
     * 
     * @param compId the company's id.
     * @param skillId the id of the skill.
     * @exception NullValueException If the id of the employee is null.
     * @return the list of all employee's skills of the skill.
     */
    @GetMapping(params = { "skillId" , "compId" })
    public List<EmployeeSkillDTO> getEmployeeSkillsBySkill(@RequestParam String skillId, @RequestParam String compId) throws NullValueException {
        List<EmployeeSkillDTO> employeeSkillList = new ArrayList<>();
        for(EmployeeSkill em : employeeSkillService.getEmployeeSkillsBySkill(skillId)){
            if(em.getCompany().getUEN().equals(compId)) continue;
            employeeSkillList.add(convertToDTO(em));
        }

        return employeeSkillList;
    }

    /**
     * Get the employee's skill with the specific employee's id and skill's id
     * through employee's skill's service.
     * 
     * @param empId   the id of the employee.
     * @param skillId the id of the skill.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     * @return the list of all employee's skills of the skill.
     */
    @GetMapping(params = { "empId", "skillId" })
    public EmployeeSkill getEmployeeSkillsByEmployeeAndSkill(@RequestParam String empId, @RequestParam String skillId)
            throws NullValueException, ObjectNotExistsException {
        return employeeSkillService.getEmployeeSkillByEmployeeAndSkill(empId, skillId);
    }

    /**
     * Get the employee with employee's id, in the specific employee's skill,
     * through employee's service, set the employee of the specific employee's
     * skill, get the skill with skill's id, in the specific employee's skill,
     * through skill's service, set the skill of the specific employee's skill and
     * create the specific employee's skill through employee's skill's service.
     * 
     * @param employeeSkill the employee's skill to create.
     * @exception NullValueException    If the id of the company is null.
     * @exception ObjectExistsException If the company is in the repository.
     * @return the created employee's skill.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(params = { "compId" })
    public EmployeeSkill createEmployeeSkill(@RequestParam String compId, @Valid @RequestBody EmployeeSkill employeeSkill)
            throws NullValueException, ObjectExistsException {
        employeeSkill.setEmployee(employeeService.getEmployee(employeeSkill.getId().getEmployee()));
        employeeSkill.setSkill(skillService.getSkill(employeeSkill.getId().getSkill()));
        employeeSkill.setCompany(companyService.getCompany(compId));
        return employeeSkillService.addEmployeeSkill(employeeSkill);
    }

    /**
     * Get the employee's skill's id with the specific employee's id and skill's id
     * through employee's skill's service and update the employee's skill with the
     * employee's skill's id and the specific employee's skill through employee's
     * skill's service.
     * 
     * @param empId         the employee's id of the employee's skill to update.
     * @param skillId       the skill's id of the employee's skill to update.
     * @param compId        the company's id of the employee
     * @param employeeSkill the employee's skill to update.
     * @return the updated employee's skill.
     */
    @PutMapping(params = { "empId", "skillId", "compId"  })
    public EmployeeSkill updateEmployeeSkill(@RequestParam String empId, @RequestParam String skillId, @RequestParam String compId,
            @Valid @RequestBody EmployeeSkill employeeSkill) throws NullValueException, ObjectNotExistsException {
                employeeSkill.setCompany(companyService.getCompany(compId));
        return employeeSkillService.updateEmployeeSkill(
                employeeSkillService.getEmployeeSkillByEmployeeAndSkill(empId, skillId).getId(), employeeSkill);
    }

    /**
     * Get the employee's skill's id with the specific employee's id and skill's id
     * through employee's skill's service and delete the employee's skill with the
     * employee's skill's id through employee's skill's service.
     * 
     * @param empId   the employee's id of the employee's skill to delete.
     * @param skillId the skill's id of the employee's skill to delete.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     */
    @DeleteMapping(params = { "empId", "skillId" })
    public void deleteEmployeeSkill(@RequestParam String empId, @RequestParam String skillId)
            throws NullValueException, ObjectNotExistsException {
        employeeSkillService
                .deleteEmployeeSkill(employeeSkillService.getEmployeeSkillByEmployeeAndSkill(empId, skillId).getId());
    }

    /**
     * Get the collated list of details; namely the name of the Skill, Number of Employees within the category
     * and the lowest cost at which they can hire within a hashmap 
     * for each Skills present within the database
     * 
     * @return the collated details of each category of skills
     */
    @RequestMapping(value = "/collate" , params= {"compId"} , method =  RequestMethod.GET)
    public List<HashMap<String, String>> collate(@RequestParam String compId) {
        List<List<String>> result = employeeSkillService.collate(compId);

        List<HashMap<String, String>> res = null;

        if(result != null && !result.isEmpty()){
            res = new ArrayList<HashMap<String,String>>();

            for (List<String> object : result) {
                HashMap<String, String> toAdd = new HashMap<>();
                toAdd.put("name", object.get(0));
                toAdd.put("pax", object.get(1));
                toAdd.put("min", object.get(2));
                res.add(toAdd);
            }
        }

        return res;
    }
      
    /**
     * Create an EmployeeSkill DTO from the specific EmployeeSkill.
     * 
     * @param emp the EmployeeSkill to to create EmployeeSkill DTO.
     * @return the DTO of the specific EmployeeSkill.
     */
    private EmployeeSkillDTO convertToDTO(EmployeeSkill emp) {
        Employee em = emp.getEmployee();
        return new EmployeeSkillDTO(em.getWorkPermitNumber(), em.getName(), em.getDescription(), em.getEmployeeRole(),
                emp.getSkill().getSkill(), emp.getExperience(), emp.getCost(), emp.getRating(),
                imageService.getProfileImageURL(em), em.getCompany().getName(), em.getCompany().getUEN());
    }
}