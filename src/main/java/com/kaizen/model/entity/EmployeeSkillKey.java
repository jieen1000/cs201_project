package com.kaizen.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Represents a key of employee's skill.
 *
 * @author Chong Zhan Han
 * @version 1.0
 * @since 2021-10-15
 */
@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeSkillKey implements Serializable {
    /**
     * Represents the id of the employee of the key of employee's skill.
     */
    @Column(name = "employee_id")
    private String employee;

    /**
     * Represents the id of the skill of the key of employee's skill.
     */
    @Column(name = "skill_id")
    private String skill;
}