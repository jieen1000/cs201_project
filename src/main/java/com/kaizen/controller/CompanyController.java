package com.kaizen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.entity.Company;
import com.kaizen.service.company.CompanyService;

/**
 * {@code CompanyController} is a rest controller for company.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    /**
     * The company's service used to do the business's logic for company.
     */
    private final CompanyService companyService;

    /**
     * Create a company's controller with the specific company's service.
     * 
     * @param companyService the company's service used by the application.
     */
    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Get all companies through company's service.
     * 
     * @return the list of all companies.
     */
    @GetMapping
    public List<Company> getCompanies() {
        return companyService.listCompanies();
    }

    /**
     * Get the company with the specific id through company's service.
     * 
     * @param id the id of the company.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     * @return the company with that id.
     */
    @GetMapping("/{id}")
    public Company getCompany(@PathVariable String id) throws NullValueException, ObjectNotExistsException {
        return companyService.getCompany(id);
    }

    /**
     * Get the company with the specific name through company's service.
     *
     * @param id the name of the company.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     * @return the company with that id.
     */
    @GetMapping(value = "/specific", params={"name"})
    public Company getCompanyByName(@RequestParam String name) throws NullValueException, ObjectNotExistsException {
        return companyService.getCompanyByName(name);
    }

    /**
     * Create the specific company through company's service.
     * 
     * @param company the company to create.
     * @exception NullValueException    If the id of the company is null.
     * @exception ObjectExistsException If the company is in the repository.
     * @return the created company.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Company createCompany(@Valid @RequestBody Company company) throws NullValueException, ObjectExistsException {
        return companyService.addCompany(company);
    }

    /**
     * Update the company with the specific id and company through company's
     * service.
     * 
     * @param id      the id of the company to update.
     * @param company the company to update.
     * @exception NullValueException       If the id of the company is null or the
     *                                     company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     * @return the updated company.
     */
    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable String id, @Valid @RequestBody Company company)
            throws NullValueException, ObjectNotExistsException {
        return companyService.updateCompany(id, company);
    }

    /**
     * Delete the company with the specific id through company's service.
     * 
     * @param id the id of the company to delete.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     */
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable String id) throws NullValueException, ObjectNotExistsException {
        companyService.deleteCompany(id);
    }
}
