package com.kaizen.model.entity;

import lombok.*;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.kaizen.model.*;

import javax.validation.constraints.Min;
import java.time.LocalDate;


/**
 * 
 * @author Bryan Tan
 * @author Tan Jie En
 * @version 1.0
 * @since 2021-10-25
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

  /**
   * Represents the id of the Transaction which is a composite key of
   * loan_company_id, borrowing_company_id, employee_id, loan_start_date and
   * serializable.
   */
  @EmbeddedId
  private TransactionKey id;

  /**
   * Represents the employee transacted in transaction.
   */
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("employee")
  @JoinColumn(name = "employee_id")
  private Employee employee;

  /**
   * Represents the employee of employee's company.
   */
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("loanCompany")
  @JoinColumn(name = "loan_company_id")
  private Company loanCompany;

  /**
   * Represents the employee of employee's skill.
   */
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("borrowingCompany")
  @JoinColumn(name = "borrowing_company_id")
  private Company borrowingCompany;

  /**
   * Represents the start date of the employee loan.
   */
  @Column(name = "loan_start_date", insertable = false, updatable = false)
//  @MapsId("startDate")
  private LocalDate startDate;

  /**
   * Represents the end date of loan.
   */
  @Column(name = "loan_end_date")
  private LocalDate endDate;

  /**
   * Represents the total hire cost
   */
  @Column(name = "total_cost")
  @Min(value = 0, message = "rating: positive number, min $0 is required")
  private double totalCost;

  /**
   * Represents the acceptance status of the request. Can be "Accepted", "Pending"
   * or "Rejected"
   */
  @Column(name = "loan_status")
  private String status;
  
  @Override 
  public int hashCode(){
      return id.hashCode();
  }

  @Override
  public boolean equals(Object obj){
      if (obj == null || getClass() != obj.getClass()) {
          return false;
      }
      return this.id.equals(((Transaction) obj).id);
  }
}
