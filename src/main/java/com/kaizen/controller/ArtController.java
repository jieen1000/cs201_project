package com.kaizen.controller;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.dto.ArtDTO;
import com.kaizen.model.entity.Art;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;
import com.kaizen.service.art.ArtService;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.employee.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

/**
 * {@code ArtController} is a rest controller for ART.
 *
 * @author Teo Keng Swee
 * @author Gregory Koh
 * @author Pang Jun Rong
 * @version 1.1
 * @since 2021-11-06
 */
@RestController
@RequestMapping("/api/covidTest")
public class ArtController {
    /**
     * The ART's service used to do the business's logic for ART.
     */
    private final ArtService artService;

    /**
     * The employee's service used to do the business's logic for employee.
     */
    private final EmployeeService employeeService;

    /**
     * The company's service used to do the business's logic for company.
     */
    private final CompanyService companyService;

    /**
     * Create an ART's controller with the specific ART's service, employee's
     * service and company's service.
     * 
     * @param ArtService      the ART's service used by the application.
     * @param employeeService the employee's service used by the application.
     * @param companyService  the company's service used by the application.
     */
    @Autowired
    public ArtController(ArtService artService, EmployeeService employeeService, CompanyService companyService) {
        this.artService = artService;
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    /**
     * Get all ART DTOs through Art's service.
     * 
     * @return the list of all ART DTOs.
     */
    @GetMapping(params = { "compId" })
    public List<ArtDTO> getArts(@RequestParam String compId) {
        List<ArtDTO> artDTOs = new ArrayList<>();
        for (Art art : artService.listArtsByCompany(compId)) {
            artDTOs.add(convertToDTO(art));
        }
        return artDTOs;
    }

    /**
     * Get the latest list of ART DTOs for each Employee from a specific Company
     * through Art's service.
     * 
     * @param compId the Company's Id to search for.
     * @return the list of latest ART DTOs.
     */
    @GetMapping(value = "/latest", params = { "compId" })
    public List<ArtDTO> getlatestArts(@RequestParam String compId) {
        List<ArtDTO> artDTOs = new ArrayList<>();
        for (Art art : artService.listLatestArts()) {
            if (!art.getCompany().getUEN().equals(compId))
                continue;
            artDTOs.add(convertToDTO(art));
        }
        return artDTOs;
    }

    /**
     * Create the specific ART through ART's service.
     * 
     * @param empId  the id of employee to set in ART.
     * @param compId the id of company to set in ART.
     * @param artDTO the ART DTO to create from.
     * @exception NullValueException       If the empId is null.
     * @exception ObjectNotExistsException If the employee with the specific id is
     *                                     not in the repository.
     * @return the created Art DTO.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(params = { "empId", "compId" })
    public ArtDTO createArt(@RequestParam String empId, @RequestParam String compId, @Valid @RequestBody ArtDTO artDTO)
            throws NullValueException, ObjectExistsException {
        Art art = convertToEntity(artDTO);
        setEmployee(art, empId);
        setCompany(art, compId);
        return convertToDTO(artService.addArt(art));
    }

    /**
     * Delete the ART with the specific id through ART's service.
     * 
     * @param id the id of the ART to delete.
     * @exception NullValueException       If the id is null.
     * @exception ObjectNotExistsException If the ART is not in the repository.
     */
    @DeleteMapping("/{id}")
    public void deleteArt(@PathVariable Long id) throws NullValueException, ObjectNotExistsException {
        artService.deleteArt(id);
    }

    /**
     * Create an ART DTO from the specific ART.
     * 
     * @param art the ART to to create ART DTO.
     * @return the DTO of the specific ART.
     */
    private ArtDTO convertToDTO(Art art) {
        Employee employee = art.getEmployee();
        return new ArtDTO(art.getId(), art.getDateOfTest(), art.getExpiryDate(), art.isResult(),
                employee.getWorkPermitNumber(), employee.getName(), art.getCompany().getUEN());
    }

    /**
     * Create an ART from the specific ART DTO.
     * 
     * @param artDTO the ART DTO to to create ART.
     * @return the ART of the specific ART DTO.
     */
    private Art convertToEntity(ArtDTO artDTO) {
        Art art = new Art();
        art.setDateOfTest(artDTO.getDateOfTest());
        art.setExpiryDate(artDTO.getDateOfTest().plusDays(7));
        art.setResult(artDTO.isResult());
        return art;
    }

    /**
     * Set employee with specific empId to the specific ART.
     * 
     * @param art   the ART to to add employee.
     * @param empId the employee's id to get the employee and add to the specific
     *              ART.
     * @exception NullValueException       If the empId is null.
     * @exception ObjectNotExistsException If the employee with the specific id is
     *                                     not in the repository.
     */
    private void setEmployee(Art art, String empId) throws NullValueException, ObjectNotExistsException {
        Employee employee = employeeService.getEmployee(empId);
        art.setEmployee(employee);
    }

    /**
     * Set company with specific compId to the specific ART.
     * 
     * @param art    the ART to to add company.
     * @param compId the company's id to get the company and add to the specific
     *               ART.
     * @exception NullValueException       If the compId is null.
     * @exception ObjectNotExistsException If the company with the specific id is
     *                                     not in the repository.
     */
    private void setCompany(Art art, String compId) throws NullValueException, ObjectNotExistsException {
        Company company = companyService.getCompany(compId);
        art.setCompany(company);
    }
}