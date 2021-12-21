package com.kaizen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.dto.EmployeeDTO;
import com.kaizen.model.entity.Employee;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.employee.EmployeeService;
import com.kaizen.service.image.ImageService;

/**
 * {@code EmployeeController} is a rest controller for employee.
 *
 * @author Teo Keng Swee
 * @author Gregory Koh
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-10-22
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    /**
     * The employee's service used to do the business's logic for employee.
     */
    private final EmployeeService employeeService;

    /**
     * The company's service used to do the business's logic for company.
     */
    private final CompanyService companyService;

    /**
     * The Image's service used to do the business's logic for image.
     */
    private final ImageService imageService;

    /**
     * Create an employee controller with the specific employee's service and
     * company's service.
     * 
     * @param employeeService the employee's service used by the application.
     * @param companyService  the company's service used by the application.
     * @param imageService    the image's service used by the application.
     */
    @Autowired
    public EmployeeController(EmployeeService employeeService, CompanyService companyService,
            ImageService imageService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.imageService = imageService;
    }

    /**
     * Get all employees through employee's service.
     * @param compId the company's id.
     * @return the list of all employees by company.
     */
    @GetMapping(params = { "compId" })
    public List<EmployeeDTO> getEmployees(@RequestParam String compId) {
        List<EmployeeDTO> employeeList = new ArrayList<>();
        for(Employee em : employeeService.listEmployeesByCompany(compId)){
            employeeList.add(convertToDTO(em));
        }
        return employeeList;
    }

    /**
     * Get the employee with the specific id through employee's service.
     * 
     * @param id the id of the employee.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     * @return the employee with that id.
     */
    @GetMapping(value = "/specific", params={"id"})
    public Employee getEmployee(@RequestParam String id) throws NullValueException, ObjectNotExistsException {
        return employeeService.getEmployee(id);
    }

    /**
     * Get the company with the specific company's id through company's service, set
     * the company of the specific employee and create the specific employee through
     * employee's service.
     * 
     * @param compId   the company's id of the employee to create.
     * @param employee the employee to create.
     * @exception NullValueException    If the id of the company is null.
     * @exception ObjectExistsException If the company is in the repository.
     * @return the created employee.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(params = { "compId" })
    public Employee createEmployee(@RequestParam String compId, @Valid @RequestBody Employee employee)
            throws NullValueException, ObjectExistsException {
        employee.setCompany(companyService.getCompany(compId));
        return employeeService.addEmployee(employee);
    }

    /**
     * Get the company with the specific company's id through company's service, set
     * the company of the specific employee and update the employee with the
     * specific id and employee through employee's service.
     * 
     * @param id      the id of the employee to update.
     * @param compId  the company's id of the employee to update.
     * @param company the employee to update.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     * @return the updated employee.
     */
    @PutMapping(value = "/{id}", params = { "compId" })
    public Employee updateEmployee(@PathVariable String id, @RequestParam String compId,
            @Valid @RequestBody Employee employee) throws NullValueException, ObjectNotExistsException {
        employee.setCompany(companyService.getCompany(compId));
        return employeeService.updateEmployee(id, employee);
    }

    /**
     * Delete the employee with the specific id through employee's service.
     * 
     * @param id the id of the employee to delete.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     */
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable String id) throws NullValueException, ObjectNotExistsException {
        employeeService.deleteEmployee(id);
    }


     /**
     * Create an Employee DTO from the specific Employee.
     * 
     * @param employee the Employee to to create Employee DTO.
     * @return the DTO of the specific Employee.
     */
    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(employee.getWorkPermitNumber(), employee.getName(), employee.getPassportNumber(),
                employee.getWorkId(), employee.getEmployeeRole(), employee.getLevy(),
                employee.getWorkPermitDateOfIssue(), employee.getWorkPermitExpiryDate(),
                employee.getWorkContactNumber(), employee.getWorkSiteLocation(), employee.getSingaporeAddress(),
                employee.isVaccStatus(), employee.isForSharing(), employee.isShared(),
                employee.getCompany().getName(), imageService.getProfileImageURL(employee), employee.getDescription());
    }
}
