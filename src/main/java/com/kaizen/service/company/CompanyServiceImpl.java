package com.kaizen.service.company;

import org.springframework.stereotype.Service;

import java.util.List;

import com.kaizen.exceptions.*;
import com.kaizen.model.entity.Company;
import com.kaizen.repository.CompanyRepository;

/**
 * {@code CompanyServiceImpl} is an implementation of {@code CompanyService}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    /**
     * The company's repository that stored companies.
     */
    private final CompanyRepository companyRepository;

    /**
     * Represents the simple name of the Company's class.
     */
    private final String COMPANY_SIMPLE_NAME;

    /**
     * Create a company's service implementation with the specific company's
     * repository and set the {@code COMPANY_SIMPLE_NAME} with the simple name of
     * the Company's class
     * 
     * @param companyRepository the company's repository used by the application.
     */
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
        COMPANY_SIMPLE_NAME = Company.class.getSimpleName();
    }

    /**
     * Get all companies stored in the repository.
     * 
     * @return the list of all companies.
     */
    @Override
    public List<Company> listCompanies() {
        return companyRepository.findAll();
    }

    /**
     * Get the company with the specific id from the repository.
     * 
     * @param id the id of the company.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     * @return the company with that id.
     */
    @Override
    public Company getCompany(String id) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        return companyRepository.findById(id).orElseThrow(() -> new ObjectNotExistsException(COMPANY_SIMPLE_NAME, id));
    }

    /**
     * Get the company with the specific id from the repository.
     *
     * @param name the name of the company.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     * @return the company with that id.
     */
    @Override
    public Company getCompanyByName(String name) throws NullValueException, ObjectNotExistsException {
        return companyRepository.findByName(name);
    }

    /**
     * Create the specific company in the repository.
     * 
     * @param company the company to create.
     * @exception NullValueException    If the id of the company is null.
     * @exception ObjectExistsException If the company is in the repository.
     * @return the created company.
     */
    @Override
    public Company addCompany(Company company) throws NullValueException, ObjectExistsException {
        validateCompanyNotNull(company);
        validateIdNotNull(company.getUEN());
        if (companyRepository.findById(company.getUEN()).isPresent()) {
            throw new ObjectExistsException(COMPANY_SIMPLE_NAME, company.getUEN());
        }
        return companyRepository.save(company);
    }

    /**
     * Update the company with the specific id and company in the repository.
     * 
     * @param id      the id of the company to update.
     * @param company the company to update.
     * @exception NullValueException       If the id of the company is null or the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     * @return the updated company.
     */
    @Override
    public Company updateCompany(String id, Company company) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        validateCompanyNotNull(company);
        validateCompanyExists(id);
        return companyRepository.save(company);
    }

    /**
     * Delete the company with the specific id in the repository.
     * 
     * @param id the id of the company to delete.
     * @exception NullValueException       If the id of the company is null.
     * @exception ObjectNotExistsException If the company is not in the repository.
     */
    @Override
    public void deleteCompany(String id) throws NullValueException, ObjectNotExistsException {
        validateIdNotNull(id);
        validateCompanyExists(id);
        companyRepository.deleteById(id);
    }

    /**
     * Validate the specific id is not null.
     * 
     * @param id the id of the company to validate.
     * @exception NullValueException If the id of the company is null.
     */
    private void validateIdNotNull(String id) throws NullValueException {
        if (id == null) {
            throw new NullValueException(COMPANY_SIMPLE_NAME + "'s id");
        }
    }

    /**
     * Validate the specific company is not null.
     * 
     * @param company the company to validate.
     * @exception NullValueException If the company is null.
     */
    private void validateCompanyNotNull(Company company) throws NullValueException {
        if (company == null) {
            throw new NullValueException(COMPANY_SIMPLE_NAME);
        }
    }

    /**
     * Validate the company with the specific id is in the repository.
     * 
     * @param id the id of the company to validate.
     * @exception ObjectNotExistsException If the company is not in the repository.
     */
    private void validateCompanyExists(String id) throws ObjectNotExistsException {
        if (companyRepository.findById(id).isEmpty()) {
            throw new ObjectNotExistsException(COMPANY_SIMPLE_NAME, id);
        }
    }
}