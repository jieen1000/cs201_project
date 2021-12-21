package com.kaizen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Represents an employee's skill.
 *
 * @author Chong Zhan Han
 * @version 1.0
 * @since 2021-10-15
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employeeSkill")
public class EmployeeSkill {
    /**
     * Represents the id of the employee's skill.
     */
    @EmbeddedId
    private EmployeeSkillKey id;

    /**
     * Represents the employee of employee's skill.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employee")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    /**
     * Represents the skill of employee's skill.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skill")
    @JoinColumn(name = "skill_id")
    private Skill skill;

    /**
     * Represents the experience(years) of employee's skill.
     */
    @Column(name = "experience")
    private int experience;

    /**
     * Represents the rating(0 to 5) of employee's skill.
     */
    @Column(name = "rating")
    @Min(value=0, message="rating: positive number, min 0 is required")
    @Max(value=5, message="rating: maximum rating is 5 stars")
    private double rating;

    /**
     * Represents the cost(0 or more) of employee's skill.
     */
    @Column(name = "cost")
    @Min(value=0, message="costs: positive number, min 0 is required")
    private double cost;

    /**
     * Represents the company of the employee.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_uen")
    private Company company;
   
    @Override 
    public int hashCode(){
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.id.equals(((EmployeeSkill) obj).id);
    }

    
}