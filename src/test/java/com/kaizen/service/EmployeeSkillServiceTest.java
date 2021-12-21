package com.kaizen.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.kaizen.exceptions.*;
import com.kaizen.model.TestCompany;
import com.kaizen.model.TestEmployeeSkill;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.EmployeeSkill;
import com.kaizen.model.entity.EmployeeSkillKey;
import com.kaizen.repository.EmployeeSkillRepository;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.employeeSkill.EmployeeSkillService;
import com.kaizen.service.employeeSkill.EmployeeSkillServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code EmployeeSkillServiceTest} is a test class to do unit testing on
 * {@link EmployeeSkillService} using {@link EmployeeSkillServiceImpl}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-06
 */
@ContextConfiguration(classes = { EmployeeSkillServiceImpl.class })
@ExtendWith(SpringExtension.class)
public class EmployeeSkillServiceTest {
    /**
     * The mocked employee's skill's repository used for testing.
     */
    @MockBean
    private EmployeeSkillRepository employeeSkillRepository;

    /**
     * The mocked company's service used for testing.
     */
    @MockBean
    private CompanyService companyService;

    /**
     * The employee's skill's service used for testing.
     */
    @Autowired
    private EmployeeSkillService employeeSkillService;

    /**
     * {@code listEmployeeSkills_Found_ReturnFound} is a test on
     * {@link EmployeeSkillService#listEmployeeSkills()} to verify if the method
     * will call {@link EmployeeSkillRepository#findAll()} and return the list of
     * all employee's skills.
     */
    @Test
    void listEmployeeSkills_Found_ReturnFound() {
        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        when(employeeSkillRepository.findAll()).thenReturn(employeeSkills);

        List<EmployeeSkill> foundEmployeeSkills = employeeSkillService.listEmployeeSkills();

        assertSame(employeeSkills, foundEmployeeSkills);
        verify(employeeSkillRepository).findAll();
    }

    /**
     * {@code getEmployeeSkill_Null_ThrowNullValueException} is a test on
     * {@link EmployeeSkillService#getEmployeeSkill(EmployeeSkillKey)} to verify if
     * the method will throw {@link NullValueException} when the specific id is null
     * .
     */
    @Test
    void getEmployeeSkill_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeSkillService.getEmployeeSkill(null);
        });
    }

    /**
     * {@code getEmployeeSkill_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link EmployeeSkillService#getEmployeeSkill(EmployeeSkillKey)} to verify if
     * the method will call
     * {@link EmployeeSkillRepository#findById(EmployeeSkillKey)} and throw
     * {@link ObjectNotExistsException} when the employee's skill with the specific
     * id does not exists.
     */
    @Test
    void getEmployeeSkill_NotFound_ThrowObjectNotExistsException() {
        when(employeeSkillRepository.findById(TestEmployeeSkill.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            employeeSkillService.getEmployeeSkill(TestEmployeeSkill.TEST_ID);
        });

        verify(employeeSkillRepository).findById(TestEmployeeSkill.TEST_ID);
    }

    /**
     * {@code getEmployeeSkill_Found_ReturnFound} is a test on
     * {@link EmployeeSkillService#getEmployeeSkill(EmployeeSkillKey)} to verify if
     * the method will call
     * {@link EmployeeSkillRepository#findById(EmployeeSkillKey)} and return the
     * employee's skill with the specific id.
     */
    @Test
    void getEmployeeSkill_Found_ReturnFound() {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        when(employeeSkillRepository.findById(employeeSkill.getId())).thenReturn(Optional.of(employeeSkill));

        EmployeeSkill foundEmployeeSkill = employeeSkillService.getEmployeeSkill(employeeSkill.getId());

        assertSame(employeeSkill, foundEmployeeSkill);
        verify(employeeSkillRepository).findById(employeeSkill.getId());
    }

    /**
     * {@code getEmployeeSkillsByEmployee_Null_ThrowNullValueException} is a test on
     * {@link EmployeeSkillService#getEmployeeSkillsByEmployee(String)} to verify if
     * the method will throw {@link NullValueException} when the specific employee's
     * id is null.
     */
    @Test
    void getEmployeeSkillsByEmployee_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeSkillService.getEmployeeSkillsByEmployee(null);
        });
    }

    /**
     * {@code getEmployeeSkillsByEmployee_Found_ReturnFound} is a test on
     * {@link EmployeeSkillService#getEmployeeSkillsByEmployee(String)} to verify if
     * the method will call
     * {@link EmployeeSkillRepository#findEmployeeSkillsByIdEmployee(String)} and
     * return the list of all employee's skills of the employee with the specific
     * employee's id.
     */
    @Test
    void getEmployeeSkillsByEmployee_Found_ReturnFound() {
        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        when(employeeSkillRepository.findEmployeeSkillsByIdEmployee(TestEmployeeSkill.TEST_ID.getEmployee()))
                .thenReturn(employeeSkills);

        List<EmployeeSkill> foundEmployeeSkills = employeeSkillService
                .getEmployeeSkillsByEmployee(TestEmployeeSkill.TEST_ID.getEmployee());

        assertSame(employeeSkills, foundEmployeeSkills);
        verify(employeeSkillRepository).findEmployeeSkillsByIdEmployee(TestEmployeeSkill.TEST_ID.getEmployee());
    }

    /**
     * {@code getEmployeeSkillsBySkill_Null_ThrowNullValueException} is a test on
     * {@link EmployeeSkillService#getEmployeeSkillsBySkill(String)} to verify if
     * the method will throw {@link NullValueException} when the specific skill's id
     * is null.
     */
    @Test
    void getEmployeeSkillsBySkill_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeSkillService.getEmployeeSkillsBySkill(null);
        });
    }

    /**
     * {@code getEmployeeSkillsBySkill_Found_ReturnFound} is a test on
     * {@link EmployeeSkillService#getEmployeeSkillsBySkill(String)} to verify if
     * the method will call
     * {@link EmployeeSkillRepository#findEmployeeSkillsByIdSkill(String)} and
     * return the list of all employee's skills of the skill with the specific
     * skill's id.
     */
    @Test
    void getEmployeeSkillsBySkill_Found_ReturnFound() {
        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        when(employeeSkillRepository.findEmployeeSkillsByIdSkill(TestEmployeeSkill.TEST_ID.getSkill()))
                .thenReturn(employeeSkills);

        List<EmployeeSkill> foundEmployeeSkills = employeeSkillService
                .getEmployeeSkillsBySkill(TestEmployeeSkill.TEST_ID.getSkill());

        assertSame(employeeSkills, foundEmployeeSkills);
        verify(employeeSkillRepository).findEmployeeSkillsByIdSkill(TestEmployeeSkill.TEST_ID.getSkill());
    }

    /**
     * {@code getEmployeeSkillByEmployeeAndSkill_Null_ThrowNullValueException} is a
     * test on
     * {@link EmployeeSkillService#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * to verify if the method will throw {@link NullValueException} when the
     * specific employee's id and skill's id are null.
     */
    @Test
    void getEmployeeSkillByEmployeeAndSkill_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeSkillService.getEmployeeSkillByEmployeeAndSkill(null, null);
        });
    }

    /**
     * {@code getEmployeeSkillByEmployeeAndSkill_NullEmployeeId_ThrowNullValueException}
     * is a test on
     * {@link EmployeeSkillService#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * to verify if the method will throw {@link NullValueException} when the
     * specific employee's id is null.
     */
    @Test
    void getEmployeeSkillByEmployeeAndSkill_NullEmployeeId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeSkillService.getEmployeeSkillByEmployeeAndSkill(null, TestEmployeeSkill.TEST_ID.getSkill());
        });
    }

    /**
     * {@code getEmployeeSkillByEmployeeAndSkill_NullSkillId_ThrowNullValueException}
     * is a test on
     * {@link EmployeeSkillService#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * to verify if the method will throw {@link NullValueException} when the
     * specific skill's id is null.
     */
    @Test
    void getEmployeeSkillByEmployeeAndSkill_NullSkillId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeSkillService.getEmployeeSkillByEmployeeAndSkill(TestEmployeeSkill.TEST_ID.getEmployee(), null);
        });
    }

    /**
     * {@code getEmployeeSkillByEmployeeAndSkill_NotFound_ThrowObjectNotExistsException}
     * is a test on
     * {@link EmployeeSkillService#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * to verify if the method will call
     * {@link EmployeeSkillRepository#findEmployeeSkillByIdEmployeeAndIdSkill(String, String)}
     * and throw {@link ObjectNotExistsException} when the employee's skill with the
     * specific employee's id and skill's id does not exists.
     */
    @Test
    void getEmployeeSkillByEmployeeAndSkill_NotFound_ThrowObjectNotExistsException() {
        when(employeeSkillRepository.findEmployeeSkillByIdEmployeeAndIdSkill(TestEmployeeSkill.TEST_ID.getEmployee(),
                TestEmployeeSkill.TEST_ID.getSkill())).thenReturn(null);

        assertThrows(ObjectNotExistsException.class, () -> {
            employeeSkillService.getEmployeeSkillByEmployeeAndSkill(TestEmployeeSkill.TEST_ID.getEmployee(),
                    TestEmployeeSkill.TEST_ID.getSkill());
        });

        verify(employeeSkillRepository).findEmployeeSkillByIdEmployeeAndIdSkill(TestEmployeeSkill.TEST_ID.getEmployee(),
                TestEmployeeSkill.TEST_ID.getSkill());
    }

    /**
     * {@code getEmployeeSkillByEmployeeAndSkill_Found_ReturnFound} is a test on
     * {@link EmployeeSkillService#getEmployeeSkillByEmployeeAndSkill(String, String)}
     * to verify if the method will call
     * {@link EmployeeSkillRepository#findEmployeeSkillByIdEmployeeAndIdSkill(String, String)}
     * and return the employee's skill with the specific employee's id and skill's
     * id.
     */
    @Test
    void getEmployeeSkillByEmployeeAndSkill_Found_ReturnFound() {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        when(employeeSkillRepository.findEmployeeSkillByIdEmployeeAndIdSkill(TestEmployeeSkill.TEST_ID.getEmployee(),
                TestEmployeeSkill.TEST_ID.getSkill())).thenReturn(employeeSkill);

        EmployeeSkill foundEmployeeSkill = employeeSkillService.getEmployeeSkillByEmployeeAndSkill(
                TestEmployeeSkill.TEST_ID.getEmployee(), TestEmployeeSkill.TEST_ID.getSkill());

        assertSame(employeeSkill, foundEmployeeSkill);
        verify(employeeSkillRepository).findEmployeeSkillByIdEmployeeAndIdSkill(TestEmployeeSkill.TEST_ID.getEmployee(),
                TestEmployeeSkill.TEST_ID.getSkill());
    }

    /**
     * {@code addEmployeeSkill_Null_ThrowNullValueException} is a test on
     * {@link EmployeeSkillService#addEmployeeSkill(EmployeeSkill)} to verify if the
     * method will throw {@link NullValueException} when the specific employee's
     * skill is null.
     */
    @Test
    void addEmployeeSkill_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeSkillService.addEmployeeSkill(null);
        });
    }

    /**
     * {@code addEmployeeSkill_SameID_ThrowObjectExistsException} is a test on
     * {@link EmployeeSkillService#addEmployeeSkill(EmployeeSkill)} to verify if the
     * method will call {@link EmployeeSkillRepository#findById(EmployeeSkillKey)}
     * and throw {@link ObjectExistsException} when the id of the specific
     * employee's skill exists.
     */
    @Test
    void addEmployeeSkill_SameID_ThrowObjectExistsException() {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        when(employeeSkillRepository.findById(employeeSkill.getId())).thenReturn(Optional.of(employeeSkill));

        assertThrows(ObjectExistsException.class, () -> {
            employeeSkillService.addEmployeeSkill(employeeSkill);
        });

        verify(employeeSkillRepository).findById(employeeSkill.getId());
    }

    /**
     * {@code addEmployeeSkill_New_ReturnSaved} is a test on
     * {@link EmployeeSkillService#addEmployeeSkill(EmployeeSkill)} to verify if the
     * method will call {@link EmployeeSkillRepository#findById(EmployeeSkillKey)}
     * and {@link EmployeeSkillRepository#save(EmployeeSkill)} and save and return
     * the specific employee's skill.
     */
    @Test
    void addEmployeeSkill_New_ReturnSaved() {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        when(employeeSkillRepository.findById(employeeSkill.getId())).thenReturn(Optional.empty());
        when(employeeSkillRepository.save(employeeSkill)).thenReturn(employeeSkill);

        EmployeeSkill savedEmployeeSkill = employeeSkillService.addEmployeeSkill(employeeSkill);

        assertSame(employeeSkill, savedEmployeeSkill);
        verify(employeeSkillRepository).findById(employeeSkill.getId());
        verify(employeeSkillRepository).save(employeeSkill);
    }

    /**
     * {@code updateEmployeeSkill_NullId_ThrowNullValueException} is a test on
     * {@link EmployeeSkillService#updateEmployeeSkill(EmployeeSkillKey, EmployeeSkill)}
     * to verify if the method will throw {@link NullValueException} when the
     * specific id is null.
     */
    @Test
    void updateEmployeeSkill_NullId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeSkillService.updateEmployeeSkill(null, TestEmployeeSkill.createEmployeeSkill());
        });
    }

    /**
     * {@code updateEmployeeSkill_NullUpdatedEmployeeSkill_ThrowNullValueException}
     * is a test on
     * {@link EmployeeSkillService#updateEmployeeSkill(EmployeeSkillKey, EmployeeSkill)}
     * to verify if the method will throw {@link NullValueException} when the
     * specific employee's skill is null.
     */
    @Test
    void updateEmployeeSkill_NullUpdatedEmployeeSkill_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeSkillService.updateEmployeeSkill(TestEmployeeSkill.TEST_ID, null);
        });
    }

    /**
     * {@code updateEmployeeSkill_NotFound_ThrowObjectNotExistsException} is a test
     * on
     * {@link EmployeeSkillService#updateEmployeeSkill(EmployeeSkillKey, EmployeeSkill)}
     * to verify if the method will call
     * {@link EmployeeSkillRepository#findById(EmployeeSkillKey)} and throw
     * {@link ObjectNotExistsException} when the employee's skill with the specific
     * id does not exists.
     */
    @Test
    void updateEmployeeSkill_NotFound_ThrowObjectNotExistsException() {
        when(employeeSkillRepository.findById(TestEmployeeSkill.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            employeeSkillService.updateEmployeeSkill(TestEmployeeSkill.TEST_ID,
                    TestEmployeeSkill.createEmployeeSkill());
        });

        verify(employeeSkillRepository).findById(TestEmployeeSkill.TEST_ID);
    }

    /**
     * {@code updateEmployeeSkill_Updated_ReturnUpdated} is a test on
     * {@link EmployeeSkillService#updateEmployeeSkill(EmployeeSkillKey, EmployeeSkill)}
     * to verify if the method will call
     * {@link EmployeeSkillRepository#findById(EmployeeSkillKey)} and
     * {@link EmployeeSkillRepository#save(EmployeeSkill)} and save and return the
     * specific employee's skill.
     */
    @Test
    void updateEmployeeSkill_Updated_ReturnUpdated() {
        EmployeeSkill employeeSkill = TestEmployeeSkill.createEmployeeSkill();
        when(employeeSkillRepository.findById(employeeSkill.getId())).thenReturn(Optional.of(employeeSkill));
        when(employeeSkillRepository.save(employeeSkill)).thenReturn(employeeSkill);

        EmployeeSkill updatedEmployeeSkill = employeeSkillService.updateEmployeeSkill(employeeSkill.getId(),
                employeeSkill);

        assertSame(employeeSkill, updatedEmployeeSkill);
        verify(employeeSkillRepository).findById(employeeSkill.getId());
        verify(employeeSkillRepository).save(employeeSkill);
    }

    /**
     * {@code deleteEmployeeSkill_NullId_ThrowNullValueException} is a test on
     * {@link EmployeeSkillService#deleteEmployeeSkill(EmployeeSkillKey)} to verify
     * if the method will throw {@link NullValueException} when the specific id is
     * null.
     */
    @Test
    void deleteEmployeeSkill_NullId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            employeeSkillService.deleteEmployeeSkill(null);
        });
    }

    /**
     * {@code deleteEmployeeSkill_NotFound_ThrowObjectNotExistsException} is a test
     * on {@link EmployeeSkillService#deleteEmployeeSkill(EmployeeSkillKey)} to
     * verify if the method will call
     * {@link EmployeeSkillRepository#findById(EmployeeSkillKey)} and throw
     * {@link ObjectNotExistsException} when the employee's skill with the specific
     * id does not exists.
     */
    @Test
    void deleteEmployeeSkill_NotFound_ThrowObjectNotExistsException() {
        when(employeeSkillRepository.findById(TestEmployeeSkill.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            employeeSkillService.deleteEmployeeSkill(TestEmployeeSkill.TEST_ID);
        });

        verify(employeeSkillRepository).findById(TestEmployeeSkill.TEST_ID);
    }

    /**
     * {@code deleteEmployeeSkill_Deleted} is a test on
     * {@link EmployeeSkillService#deleteEmployeeSkill(EmployeeSkillKey)} to verify
     * if the method will call
     * {@link EmployeeSkillRepository#findById(EmployeeSkillKey)} and
     * {@link EmployeeSkillRepository#deleteById(EmployeeSkillKey)} and delete the
     * employee's skill with specific id.
     */
    @Test
    void deleteEmployeeSkill_Deleted() {
        when(employeeSkillRepository.findById(TestEmployeeSkill.TEST_ID))
                .thenReturn(Optional.of(TestEmployeeSkill.createEmployeeSkill()));
        doNothing().when(employeeSkillRepository).deleteById(TestEmployeeSkill.TEST_ID);

        employeeSkillService.deleteEmployeeSkill(TestEmployeeSkill.TEST_ID);

        verify(employeeSkillRepository).findById(TestEmployeeSkill.TEST_ID);
        verify(employeeSkillRepository).deleteById(TestEmployeeSkill.TEST_ID);
    }

    /**
     * {@code collate_Found_ReturnFound} is a test on
     * {@link EmployeeSkillService#collate(String)} to verify if the method will
     * call {@link EmployeeSkillRepository#findEmployeeSkillCountAndMinCost()} and
     * return the list of records.
     */
    @Test
    void collate_Found_ReturnFound() {
        List<List<String>> list = new ArrayList<>();
        when(employeeSkillRepository.findEmployeeSkillCountAndMinCost(TestCompany.TEST_ID)).thenReturn(list);

        List<List<String>> found = employeeSkillService.collate(TestCompany.TEST_ID);

        assertSame(list, found);
        verify(employeeSkillRepository).findEmployeeSkillCountAndMinCost(TestCompany.TEST_ID);
    }

    /**
     * {@code listEmployeeSkillsByCompany_CompanyNull_ThrowNullValueException} is a
     * test on {@link EmployeeSkillService#listEmployeeSkillsByCompany(String)} to
     * verify if the method will call {@link CompanyService#getCompany(String)} and
     * throw {@link NullValueException} when the specific company id is null.
     */
    @Test
    void listEmployeeSkillsByCompany_CompanyNull_ThrowNullValueException() {
        when(companyService.getCompany(null)).thenThrow(new NullValueException());

        assertThrows(NullValueException.class, () -> {
            employeeSkillService.listEmployeeSkillsByCompany(null);
        });

        verify(companyService).getCompany(null);
    }

    /**
     * {@code listEmployeeSkillsByCompany_CompanyNotFound_ThrowObjectNotExistsException}
     * is a test on {@link EmployeeSkillService#listEmployeeSkillsByCompany(String)}
     * to verify if the method will call {@link CompanyService#getCompany(String)}
     * and throw {@link ObjectNotExistsException} when the company with the specific
     * id does not exists.
     */
    @Test
    void listEmployeeSkillsByCompany_CompanyNotFound_ThrowObjectNotExistsException() {
        when(companyService.getCompany(any(String.class))).thenThrow(new ObjectNotExistsException());

        assertThrows(ObjectNotExistsException.class, () -> {
            employeeSkillService.listEmployeeSkillsByCompany(TestCompany.TEST_ID);
        });

        verify(companyService).getCompany(any(String.class));
    }

    /**
     * {@code listEmployeeSkillsByCompany_Found_ReturnFound} is a test on
     * {@link EmployeeSkillService#listEmployeeSkillsByCompany(String)} to verify if
     * the method will call {@link CompanyService#getCompany(String)} and
     * {@link EmployeeSkillRepository#findByCompany(Company)} and return the list of
     * employee's skills of a company.
     */
    @Test
    void listEmployeeSkillsByCompany_Found_ReturnFound() {
        List<EmployeeSkill> employeeSkills = new ArrayList<>();
        when(companyService.getCompany(any(String.class))).thenReturn(TestCompany.createCompany());
        when(employeeSkillRepository.findByCompany(any(Company.class))).thenReturn(employeeSkills);

        List<EmployeeSkill> found = employeeSkillService.listEmployeeSkillsByCompany(TestCompany.TEST_ID);

        assertSame(employeeSkills, found);
        verify(companyService).getCompany(any(String.class));
        verify(employeeSkillRepository).findByCompany(any(Company.class));
    }
}
