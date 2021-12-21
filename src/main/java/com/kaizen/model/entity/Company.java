package com.kaizen.model.entity;

import lombok.*;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a company.
 *
 * @author Chong Zhan Han
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-07
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company")
public class Company {
    /**
     * Represents the company's Unique Entity Number(UEN) and is the company's id.
     */
    @Id
    @NotNull(message = "Company's UEN should not be null")
    @Size(min = 9, max = 10, message = "Company's UEN should be 9 or 10 characters long")
    @Column(unique = true, name = "UEN")
    private String UEN;

    /**
     * Represents the company's name.
     */
    @NotNull(message = "Company's Name should not be null")
    @Size(max = 255, message = "Company's Name should be at most 255 characters long")
    @Column(name = "company_name")
    private String name;

    /**
     * Represents the company's employees.
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Employee> employees;

    /**
     * Represents the arts done within the Company.
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Art> arts;
    
    /**
     * Represents the specialisations of the employees of the Company.
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<EmployeeSkill> employeeSkills;

    /**
     * Represents the loan transactions of the Company.
     */
    @OneToMany(mappedBy = "loanCompany", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Transaction> loanTransactions;

    /**
     * Represents the borrowing transactions of the Company.
     */
    @OneToMany(mappedBy = "borrowingCompany", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Transaction> borrowingTransactions;

    @Override 
    public int hashCode(){
        return UEN.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.UEN.equals(((Company) obj).UEN);
    }
}
