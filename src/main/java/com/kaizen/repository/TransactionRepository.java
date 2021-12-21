 package com.kaizen.repository;

 import java.util.List;

 import com.kaizen.model.entity.EmployeeSkill;
 import com.kaizen.model.entity.Transaction;

 import com.kaizen.model.entity.TransactionKey;
 import org.springframework.data.jpa.repository.JpaRepository;

 import java.time.LocalDate;

 /**
  * Transaction specific extension of
  * {@link org.springframework.data.jpa.repository.JpaRepository}.
  *
  * @author Bryan Tan
  * @author Tan Jie En
  * @version 1.0
  * @since 2021-10-18
  */

 public interface TransactionRepository extends JpaRepository<Transaction, TransactionKey> {
     /**
      * Find the transactions where the co is the loan co
      *
      * @param companyId the id of the loan company
      * @return the list of the loan's company transactions
      */
     List<Transaction> findTransactionsByIdLoanCompany(String companyId);

         /**
      * Find the transactions where the co is the hiring co
      *
      * @param companyId the id of the borrowing company
      * @return the list of the borrowing's company transactions
      */
     List<Transaction> findTransactionsByIdBorrowingCompany(String companyId);

     /**
      * Find the employee's transaction of an employee and specific date.
      *
      * @param employeeId the id of the employee to find the employee's transaction.
      * @param date    the date of the transaction to find the employee's transaction.
      * @return the employee's transaction.
      */
     Transaction findTransactionByIdEmployeeAndStartDate(String employeeId, LocalDate date);

     /**
      * Find the employee's transactions of an employee
      *
      * @param employeeId the id of the employee to find the employee's transaction.
      * @return the employee's transactions.
      */
     List<Transaction> findTransactionsByIdEmployee(String employeeId);
 }

