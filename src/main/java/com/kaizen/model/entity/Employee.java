package com.kaizen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Represents an employee.
 *
 * @author Teo Keng Swee
 * @author Chong Zhan Han
 * @author Gregory Koh
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-11-07
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "employee")
public class Employee {
    /**
     * Represents the employee's work permit number(nonnull, 9 to 255 characters) and is the company's id.
     */
    @Id
    @NotNull(message = "Employee's Work Permit Number should not be null")
    @Size(min = 9, max = 255, message = "Employee's Work Permit Number should be at least 9 characters long")
    @Column(name = "work_permit_number")
    private String workPermitNumber;

    /**
     * Represents the employee's name(nonnull, up to 255 characters).
     */
    @NotNull(message = "Employee's Name should not be null")
    @Size(max = 255)
    @Column(name = "employee_name")
    private String name;

    /**
     * Represents the employee's passport number(nonnull, 10 to 255 characters).
     */
    @NotNull(message = "Employee's Passport Number should not be null")
    @Size(min = 10, max = 255, message = "Employee's PassportNumber should be at least 10 characters long")
    @Column(name = "passport_number")
    private String passportNumber;

    /**
     * Represents the employee's work id(nonnull, 10 to 255 characters).
     */
    @NotNull(message = "Employee's Work Permit Number should not be null")
    @Column(name = "work_id")
    @Size(min = 10, max = 255, message = "Employee's Work ID should be at least 10 characters long")
    private String workId;

    /**
     * Represents the employee's role(nonnull, up to 255 characters).
     */
    @NotNull(message = "Employee's Role should not be null")
    @Size(max = 255)
    @Column(name = "employee_role")
    private String employeeRole;

    /**
     * Represents the employee's levy(nonnull).
     */
    @NotNull(message = "Employee's Levy should not be null")
    @Column(name = "levy")
    private int levy;

    /**
     * Represents the employee's work permit date of issue(nonnull, past).
     */
    @NotNull(message = "Employee's Work Permit Date Of Issue should not be null")
    @Past
    @Column(name = "work_permit_date_of_issue")
    private LocalDate workPermitDateOfIssue;

    /**
     * Represents the employee's work permit expiry date(nonnull, future).
     */
    @NotNull(message = "Employee's Work Permit Expiry Date should not be null")
    @Future
    @Column(name = "work_permit_expiry_date")
    private LocalDate workPermitExpiryDate;

    /**
     * Represents the employee's work contact number(nonnull, 8 characters).
     */
    @NotNull(message = "Employee's Work Contact Number should not be null")
    @Size(min = 8, max = 8, message = "Employee's Work Contact Number should be 8 characters long")
    @Column(name = "work_contact_number")
    private String workContactNumber;

    /**
     * Represents the employee's work site location(nonnull, 10 to 255 characters).
     */
    @NotNull(message = "Employee's Work Site Location should not be null")
    @Size(min = 10, max = 255, message = "Employee's Work Site Location should be at least 10 characters long")
    @Column(name = "work_site_location")
    private String workSiteLocation;

    /**
     * Represents the employee's singapore address(nonnull, 10 to 255 characters).
     */
    @NotNull(message = "Employee's Singapore Address should not be null")
    @Size(min = 10, max = 255, message = "Employee's Singapore Address shouldbe at least 10 characters long")
    @Column(name = "singapore_address")
    private String singaporeAddress;

    /**
     * Represents the employee's vaccination status(nonnull).
     */
    @NotNull(message = "Employee's Vaccination Status should not be null")
    @Column(name = "vacc_status")
    private boolean vaccStatus;

    /**
     * Represents is the employee for sharing.
     */
    @Column(name = "for_sharing")
    private boolean forSharing;

    /**
     * Represents is the employee shared.
     */
    @Column(name = "shared")
    private boolean shared;

    /**
     * Represents the description of the employee by its company.
     */
    @Column(name = "description")
    private String description;

    /**
     * Represents the employee's company(nonnull).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    private Company company;

    /**
     * Represents the employee's skills.
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<EmployeeSkill> employeeSkills;

    /**
     * Represents the ART tests that are taken.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Art> artTests;

    /**
     * Represents the projects that employee involved in.
     */
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    @JsonIgnore
    private Set<Project> projects;

    /**
     * Represents the images of the employee.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Image> images;

    /**
     * Represents the transactions of the employee.
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Transaction> transactions;

    @Override 
    public int hashCode(){
        return workPermitNumber.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.workPermitNumber.equals(((Employee) obj).workPermitNumber);
    }
}


