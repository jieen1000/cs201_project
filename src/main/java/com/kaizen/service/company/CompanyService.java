package com.kaizen.service.company;

import java.util.List;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.entity.Company;

/**
 * {@code CompanyService} captures what are needed for business's logic for
 * company.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
public interface CompanyService {
  /**
   * Get all companies stored in the repository.
   * 
   * @return the list of all companies.
   */
  List<Company> listCompanies();

  /**
   * Get the company with the specific id from the repository.
   * 
   * @param id the id of the company.
   * @exception NullValueException       If the id of the company is null.
   * @exception ObjectNotExistsException If the company is not in the repository.
   * @return the company with that id.
   */
  Company getCompany(String id) throws NullValueException, ObjectNotExistsException;

  /**
   * Get the company with the specific name from the repository.
   *
   * @param name the name of the company.
   * @exception NullValueException       If the name of the company is null.
   * @exception ObjectNotExistsException If the company is not in the repository.
   * @return the company with that id.
   */
  Company getCompanyByName(String name) throws NullValueException, ObjectNotExistsException;

  /**
   * Create the specific company in the repository.
   * 
   * @param company the company to create.
   * @exception NullValueException    If the id of the company is null.
   * @exception ObjectExistsException If the company is in the repository.
   * @return the created company.
   */
  Company addCompany(Company company) throws NullValueException, ObjectExistsException;

  /**
   * Update the company with the specific id and company in the repository.
   * 
   * @param id      the id of the company to update.
   * @param company the company to update.
   * @exception NullValueException       If the id of the company is null or the
   *                                     company is null.
   * @exception ObjectNotExistsException If the company is not in the repository.
   * @return the updated company.
   */
  Company updateCompany(String id, Company company) throws NullValueException, ObjectNotExistsException;

  /**
   * Delete the company with the specific id in the repository.
   * 
   * @param id the id of the company to delete.
   * @exception NullValueException       If the id of the company is null.
   * @exception ObjectNotExistsException If the company is not in the repository.
   */
  void deleteCompany(String id) throws NullValueException, ObjectNotExistsException;
}