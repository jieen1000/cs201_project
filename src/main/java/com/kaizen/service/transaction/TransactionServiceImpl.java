package com.kaizen.service.transaction;

import com.kaizen.exceptions.*;
import com.kaizen.model.entity.Transaction;
import com.kaizen.model.entity.TransactionKey;
import com.kaizen.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.util.List;

/**
 * {@code TransactionServiceImpl} is an implementation of
 * {@code TransactionService}. Based on ProjectServiceImpl
 *
 * @author Bryan Tan
 * @author Tan Jie En
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    /**
     * The Transaction's repository that stored Transactions.
     */
    private final TransactionRepository transactionRepository;

    /**
     * Represents the simple name of the Transaction's class.
     */
    private final String TRANSACTION_SIMPLE_NAME;

    /**
     * Create a Transaction's service implementation with the specific Transaction's
     * repository and set the {@code Transaction_SIMPLE_NAME} with the simple name
     * of the Transaction's class
     *
     * @param TransactionRepository the Transaction's repository used by the
     *                              application.
     */
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        TRANSACTION_SIMPLE_NAME = Transaction.class.getSimpleName();
    }

    /**
     * Get all Transactions stored in the repository.
     *
     * @return the list of all Transactions.
     */
    @Override
    public List<Transaction> listTransactions() {
        return transactionRepository.findAll();
    }

    /**
     * Create the specific Transaction in the repository.
     *
     * @param Transaction the Transaction to create.
     * @exception NullValueException    If the id of the Transaction is null.
     * @exception ObjectExistsException If the Transaction is in the repository.
     * @return the created Transaction.
     */
    @Override
    public Transaction addTransaction(Transaction transaction) throws NullValueException, ObjectExistsException {
        validateTransactionNotNull(transaction);

        // Want to perform validation where transaction added cannot have dates that
        // collide for the same employee
        List<Transaction> transactionsToCompare = transactionRepository
                .findTransactionsByIdEmployee(transaction.getEmployee().getWorkPermitNumber());
        for (Transaction transactionComp : transactionsToCompare) {
            // start, end, comparestart, compareend

            validateDateRange(transaction.getStartDate(), transaction.getEndDate(), transactionComp.getStartDate(),
                    transactionComp.getEndDate());
        }
        return transactionRepository.save(transaction);
    }

    /**
     * Delete the Transaction with the specific id in the repository.
     *
     * @param id the id of the Transaction to delete.
     * @exception NullValueException       If the id of the Transaction is null.
     * @exception ObjectNotExistsException If the Transaction is not in the
     *                                     repository.
     */
    @Override
    public void deleteTransaction(TransactionKey id) throws NullValueException, ObjectNotExistsException {
        validateTransactionExists(id);
        transactionRepository.deleteById(id);
    }

    /**
     * Get all transactions where the company is the loaning company from the
     * repository.
     *
     * @param companyId the id of the company.
     * @exception NullValueException If the id of the company is null.
     * @return the company's transactions.
     */
    @Override
    public List<Transaction> getTransactionsByLoanCompany(String companyId) throws NullValueException {
        if (companyId == null) {
            throw new NullValueException("Skill's Id");
        }
        return transactionRepository.findTransactionsByIdLoanCompany(companyId);
    }

    /**
     * Get all transactions of the hiring company from the repository.
     *
     * @param companyId the id of the company.
     * @exception NullValueException If the id of the company is null.
     * @return the company's transactions.
     */
    @Override
    public List<Transaction> getTransactionsByBorrowingCompany(String companyId) throws NullValueException {
        if (companyId == null) {
            throw new NullValueException("Skill's Id");
        }
        return transactionRepository.findTransactionsByIdBorrowingCompany(companyId);
    }

    /**
     * Validate the specific Transaction is not null.
     *
     * @param Transaction the Transaction to validate.
     * @exception NullValueException If the Transaction is null.
     */
    private void validateTransactionNotNull(Transaction Transaction) throws NullValueException {
        if (Transaction == null) {
            throw new NullValueException(TRANSACTION_SIMPLE_NAME);
        }
    }

    /**
     * Get the employee's transaction with the specific employee's id and Date from
     * the repository.
     *
     * @param empId the id of the employee.
     * @param date  the date of the transaction.
     * @exception NullValueException       If the id of the employee or the date of
     *                                     the transaction is null.
     * @exception ObjectNotExistsException If the transaction is not in the
     *                                     repository.
     * @return the Transaction with that employee and date
     */
    @Override
    public Transaction getTransactionByEmployeeAndStartDate(String empId, LocalDate date)
            throws NullValueException, ObjectNotExistsException {
        if (empId == null) {
            throw new NullValueException("Employee's Id");
        } else if (date == null) {
            throw new NullValueException("LocalDate Date");
        }
        Transaction employeeTransaction = transactionRepository.findTransactionByIdEmployeeAndStartDate(empId, date);
        if (employeeTransaction == null) {
            throw new ObjectNotExistsException(TRANSACTION_SIMPLE_NAME,
                    "(Employee's Id: " + empId + ", date: " + date + ")");
        }
        return employeeTransaction;
    }

    /**
     * Update the employee's transaction status with the specific employee's id and
     * Date from the repository.
     *
     * @param empId  the id of the employee.
     * @param date   the date of the transaction.
     * @param status the status of the transaction
     * @exception NullValueException       If the id of the employee or the date of
     *                                     the transaction is null.
     * @exception ObjectNotExistsException If the transaction is not in the
     *                                     repository.
     * @return the Transaction with that employee and date
     */
    @Override
    public Transaction updateTransaction(Transaction transaction) throws NullValueException, ObjectNotExistsException {
        if (transaction == null) {
            throw new NullValueException("Transaction");
        }
        Transaction employeeTransaction = transactionRepository.findTransactionByIdEmployeeAndStartDate(
                transaction.getEmployee().getWorkPermitNumber(), transaction.getStartDate());

        if (employeeTransaction == null) {
            throw new ObjectNotExistsException(TRANSACTION_SIMPLE_NAME,
                    "(Employee's Id: " + transaction.getEmployee().getWorkPermitNumber() + ", date: " + transaction.getStartDate() + ")");
        }

        employeeTransaction.setStatus(transaction.getStatus());
        transactionRepository.deleteById(employeeTransaction.getId());
        transactionRepository.save(employeeTransaction);
        return employeeTransaction;
    }

    /**
     * Validate the Transaction with the specific id is in the repository.
     *
     * @param id the id of the Transaction to validate.
     * @exception ObjectNotExistsException If the Transaction is not in the
     *                                     repository.
     */
    private void validateTransactionExists(TransactionKey id) throws ObjectNotExistsException {
        if (transactionRepository.findById(id).isEmpty()) {
            throw new ObjectNotExistsException(TRANSACTION_SIMPLE_NAME, "" + id.toString());
        }
    }

    /**
     * Validate the Transaction to be inserted has valid date range.
     *
     * @param startDate        the startDate of the Transaction to validate.
     * @param endDate          the endDate of the Transaction to validate.
     * @param compareStartDate the compareStartDate of the Transaction to validate.
     * @param compareEndDate   the compareEndDate of the Transaction to validate.
     * @exception ObjectNotExistsException If the Transaction is not in the
     *                                     repository.
     */
    private void validateDateRange(LocalDate startDate, LocalDate endDate, LocalDate compareStartDate,
            LocalDate compareEndDate) throws InvalidDateException {
        if ((startDate.isAfter(compareStartDate) && startDate.isBefore(compareEndDate))
                || (endDate.isAfter(compareStartDate) && endDate.isBefore(compareEndDate)) || startDate.isEqual(
                        compareStartDate)) {
            throw new InvalidDateException(startDate, endDate);
        }
    }
}
