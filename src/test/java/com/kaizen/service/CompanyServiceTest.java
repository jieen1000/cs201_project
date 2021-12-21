package com.kaizen.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.kaizen.exceptions.*;
import com.kaizen.model.TestCompany;
import com.kaizen.model.entity.Company;
import com.kaizen.repository.CompanyRepository;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.company.CompanyServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code CompanyServiceTest} is a test class to do unit testing on
 * {@link CompanyService} using {@link CompanyServiceImpl}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-06
 */
@ContextConfiguration(classes = { CompanyServiceImpl.class })
@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    /**
     * The mocked company's repository used for testing.
     */
    @MockBean
    private CompanyRepository companyRepository;

    /**
     * The company's service used for testing.
     */
    @Autowired
    private CompanyService companyService;

    /**
     * {@code listCompanies_Found_ReturnFound} is a test on
     * {@link CompanyService#listCompanies()} to verify if the method will call
     * {@link CompanyRepository#findAll()} and return the list of all companies.
     */
    @Test
    void listCompanies_Found_ReturnFound() {
        List<Company> companys = new ArrayList<>();
        when(companyRepository.findAll()).thenReturn(companys);

        List<Company> foundCompanys = companyService.listCompanies();

        assertSame(companys, foundCompanys);
        verify(companyRepository).findAll();
    }

    /**
     * {@code getCompany_Null_ThrowNullValueException} is a test on
     * {@link CompanyService#getCompany(String)} to verify if the method will throw
     * {@link NullValueException} when the specific id is null.
     */
    @Test
    void getCompany_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            companyService.getCompany(null);
        });
    }

    /**
     * {@code getCompany_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link CompanyService#getCompany(String)} to verify if the method will call
     * {@link CompanyRepository#findById(String)} and throw
     * {@link ObjectNotExistsException} when the company with the specific id does
     * not exists.
     */
    @Test
    void getCompany_NotFound_ThrowObjectNotExistsException() {
        when(companyRepository.findById(TestCompany.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            companyService.getCompany(TestCompany.TEST_ID);
        });

        verify(companyRepository).findById(TestCompany.TEST_ID);
    }

    /**
     * {@code getCompany_Found_ReturnFound} is a test on
     * {@link CompanyService#getCompany(String)} to verify if the method will call
     * {@link CompanyRepository#findById(String)} and return the company with the
     * specific id.
     */
    @Test
    void getCompany_Found_ReturnFound() {
        Company company = TestCompany.createCompany();
        when(companyRepository.findById(company.getUEN())).thenReturn(Optional.of(company));

        Company foundCompany = companyService.getCompany(company.getUEN());

        assertSame(company, foundCompany);
        verify(companyRepository).findById(company.getUEN());
    }

    /**
     * {@code getCompanyByName_Found_ReturnFound} is a test on
     * {@link CompanyService#getCompanyByName(String)} to verify if the method will
     * call {@link CompanyRepository#findById(String)} and return the company with
     * the specific name.
     */
    @Test
    void getCompanyByName_Found_ReturnFound() {
        Company company = TestCompany.createCompany();
        when(companyRepository.findByName(company.getName())).thenReturn(company);

        Company foundCompany = companyService.getCompanyByName(company.getName());

        assertSame(company, foundCompany);
        verify(companyRepository).findByName(company.getName());
    }

    /**
     * {@code addCompany_Null_ThrowNullValueException} is a test on
     * {@link CompanyService#addCompany(Company)} to verify if the method will throw
     * {@link NullValueException} when the specific company is null.
     */
    @Test
    void addCompany_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            companyService.addCompany(null);
        });
    }

    /**
     * {@code addCompany_SameID_ThrowObjectExistsException} is a test on
     * {@link CompanyService#addCompany(Company)} to verify if the method will call
     * {@link CompanyRepository#findById(String)} and throw
     * {@link ObjectExistsException} when the id of the specific company exists.
     */
    @Test
    void addCompany_SameID_ThrowObjectExistsException() {
        Company company = TestCompany.createCompany();
        when(companyRepository.findById(company.getUEN())).thenReturn(Optional.of(company));

        assertThrows(ObjectExistsException.class, () -> {
            companyService.addCompany(company);
        });

        verify(companyRepository).findById(company.getUEN());
    }

    /**
     * {@code addCompany_New_ReturnSaved} is a test on
     * {@link CompanyService#addCompany(Company)} to verify if the method will call
     * {@link CompanyRepository#findById(String)} and
     * {@link CompanyRepository#save(Company)} and save and return the specific
     * company.
     */
    @Test
    void addCompany_New_ReturnSaved() {
        Company company = TestCompany.createCompany();
        when(companyRepository.findById(company.getUEN())).thenReturn(Optional.empty());
        when(companyRepository.save(company)).thenReturn(company);

        Company savedCompany = companyService.addCompany(company);

        assertSame(company, savedCompany);
        verify(companyRepository).findById(company.getUEN());
        verify(companyRepository).save(company);
    }

    /**
     * {@code updateCompany_NullId_ThrowNullValueException} is a test on
     * {@link CompanyService#updateCompany(String, Company)} to verify if the method
     * will throw {@link NullValueException} when the specific id is null.
     */
    @Test
    void updateCompany_NullId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            companyService.updateCompany(null, TestCompany.createCompany());
        });
    }

    /**
     * {@code updateCompany_NullUpdatedCompany_ThrowNullValueException} is a test on
     * {@link CompanyService#updateCompany(String, Company)} to verify if the method
     * will throw {@link NullValueException} when the specific company is null.
     */
    @Test
    void updateCompany_NullUpdatedCompany_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            companyService.updateCompany(TestCompany.TEST_ID, null);
        });
    }

    /**
     * {@code updateCompany_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link CompanyService#updateCompany(String, Company)} to verify if the method
     * will call {@link CompanyRepository#findById(String)} and throw
     * {@link ObjectNotExistsException} when the company with the specific id does
     * not exists.
     */
    @Test
    void updateCompany_NotFound_ThrowObjectNotExistsException() {
        when(companyRepository.findById(TestCompany.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            companyService.updateCompany(TestCompany.TEST_ID, TestCompany.createCompany());
        });

        verify(companyRepository).findById(TestCompany.TEST_ID);
    }

    /**
     * {@code updateCompany_Updated_ReturnUpdated} is a test on
     * {@link CompanyService#updateCompany(String, Company)} to verify if the method
     * will call {@link CompanyRepository#findById(String)} and
     * {@link CompanyRepository#save(Company)} and save and return the specific
     * company.
     */
    @Test
    void updateCompany_Updated_ReturnUpdated() {
        Company company = TestCompany.createCompany();
        when(companyRepository.findById(company.getUEN())).thenReturn(Optional.of(company));
        when(companyRepository.save(company)).thenReturn(company);

        Company updatedCompany = companyService.updateCompany(company.getUEN(), company);

        assertSame(company, updatedCompany);
        verify(companyRepository).findById(company.getUEN());
        verify(companyRepository).save(company);
    }

    /**
     * {@code deleteCompany_NullId_ThrowNullValueException} is a test on
     * {@link CompanyService#deleteCompany(String)} to verify if the method will
     * throw {@link NullValueException} when the specific id is null.
     */
    @Test
    void deleteCompany_NullId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            companyService.deleteCompany(null);
        });
    }

    /**
     * {@code deleteCompany_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link CompanyService#deleteCompany(String)} to verify if the method will
     * call {@link CompanyRepository#findById(String)} and throw
     * {@link ObjectNotExistsException} when the company with the specific id does
     * not exists.
     */
    @Test
    void deleteCompany_NotFound_ThrowObjectNotExistsException() {
        when(companyRepository.findById(TestCompany.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            companyService.deleteCompany(TestCompany.TEST_ID);
        });

        verify(companyRepository).findById(TestCompany.TEST_ID);
    }

    /**
     * {@code deleteCompany_Deleted} is a test on
     * {@link CompanyService#deleteCompany(String)} to verify if the method will
     * call {@link CompanyRepository#findById(String)} and
     * {@link CompanyRepository#deleteById(String)} and delete the company with
     * specific id.
     */
    @Test
    void deleteCompany_Deleted() {
        when(companyRepository.findById(TestCompany.TEST_ID)).thenReturn(Optional.of(TestCompany.createCompany()));
        doNothing().when(companyRepository).deleteById(TestCompany.TEST_ID);

        companyService.deleteCompany(TestCompany.TEST_ID);

        verify(companyRepository).findById(TestCompany.TEST_ID);
        verify(companyRepository).deleteById(TestCompany.TEST_ID);
    }
}
