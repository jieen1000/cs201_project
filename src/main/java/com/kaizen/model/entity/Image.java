package com.kaizen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaizen.model.AbstractEntity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * News specific extension of
 * {@link org.springframework.data.jpa.repository.JpaRepository}.
 *
 * @author Tan Jie En
 * @author Teo Keng Swee
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-22
 */

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "employeeimage")
public class Image extends AbstractEntity {

  /**
   * Represents the employee's image
   */
  @NotNull(message = "Employee's profileURL should not be null")
  @Column(name = "profile_url")
  private String profileURL;

  /**
   * Represents the employee to which the image belong to
   */
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

}