package com.kaizen.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a key of the transaction
 *
 * @author Bryan Tan
 * @author Tan Jie En
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
public class TransactionKey implements Serializable {
    /**
     * Represents the id of the loan company.
     */
    @Column(name = "loan_company_id")
    private String loanCompany;


    /**
     * Represents the id of the borrowing company.
     */
    @Column(name = "borrowing_company_id")
    private String borrowingCompany;

    /**
     * Represents the id of the loaned employee
     */
    @Column(name = "employee_id")
    private String employee;

    /**
     * Represents the start date of the loan
     */
    @Column(name = "loan_start_date")
    private LocalDate startDate;

    
}