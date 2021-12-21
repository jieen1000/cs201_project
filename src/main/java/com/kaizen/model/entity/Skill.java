package com.kaizen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Represents a skill.
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
@EqualsAndHashCode
@Table(name = "skill")
public class Skill {
    /**
     * Represents the skill and is the skill's id.
     */
    @Id
    @Column(unique = true, name = "skill")
    private String skill;

    /**
     * Represents the skill's task(nonnull, up to 255 characters).
     */
    @NotNull(message = "Task Description should not be null")
    @Size(max = 255)
    @Column(name = "task")
    private String task;

    /**
     * Represents the employee's skills of the skill.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "skill", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<EmployeeSkill> employeeSkills;

    
}
