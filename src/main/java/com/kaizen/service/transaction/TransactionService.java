package com.kaizen.service.transaction;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.entity.Transaction;
import com.kaizen.model.entity.TransactionKey;

import java.time.LocalDate;
import java.util.List;

/**
 * {@code TransactionService} captures what are needed for business's logic for
 * Transactions. Based on ProjectService
 * 
 * @author Bryan Tan
 * @author Tan Jie En
 * @version 1.0
 * @since 2021-10-18
 */
public interface TransactionService {
    /**
     * Get all Transaction stored in the repository.
     *
     * @return the list of all Transactions.
     */
    List<Transaction> listTransactions();

    /**
     * Create the specific Transaction in the repository.
     *
     * @param Transaction the Transaction to create.
     * @exception NullValueException    If the id of the Transaction is null.
     * @exception ObjectExistsException If the Transaction is in the repository.
     * @return the created Transaction.
     */
    Transaction addTransaction(Transaction transaction) throws NullValueException, ObjectExistsException;

     /**
     * Update the Transaction with the specific id and Transaction in the
     repository.
     *
     * @param id the id of the Transaction to update.
     * @param Transaction the Transaction to update.
     * @exception NullValueException If the id of the Transaction is null or the
     * Transaction is null.
     * @exception ObjectNotExistsException If the Transaction is not in the
     repository.
     * @return the updated Transaction.
     */
     Transaction updateTransaction(Transaction transaction) throws NullValueException, ObjectNotExistsException;

    /**
     * Delete the Transaction with the specific id in the repository.
     *
     * @param id the id of the Transaction to delete.
     * @exception NullValueException       If the id of the Transaction is null.
     * @exception ObjectNotExistsException If the Transaction is not in the
     *                                     repository.
     */
    void deleteTransaction(TransactionKey id) throws NullValueException, ObjectNotExistsException;

    /**
     * Get all transactions where the company is the loan company from the
     * repository.
     *
     * @param companyId the id of the company.
     * @exception NullValueException If the id of the employee is null.
     * @return the employee's skill with that id.
     */
    List<Transaction> getTransactionsByLoanCompany(String companyId) throws NullValueException;

    /**
     * Get all transactions where the company is the hiring company from the
     * repository.
     *
     * @param companyId the id of the company.
     * @exception NullValueException If the id of the employee is null.
     * @return the employee's skill with that id.
     */
    List<Transaction> getTransactionsByBorrowingCompany(String companyId) throws NullValueException;

    Transaction getTransactionByEmployeeAndStartDate(String empId, LocalDate date)
            throws NullValueException, ObjectNotExistsException;

}

