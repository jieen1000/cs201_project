package com.kaizen.model;

import java.time.LocalDate;

import com.kaizen.model.dto.TransactionDTO;
import com.kaizen.model.entity.Transaction;
import com.kaizen.model.entity.TransactionKey;

/**
 * {@code TestTransaction} is a mock class that stored configurations needed to do
 * testing related to {@link Transaction} and {@link TransactionController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-07
 */
public class TestTransaction {
    /**
     * Represents the transaction's id used for testing.
     */
    public final static TransactionKey TEST_ID = new TransactionKey(
            TestCompany.TEST_ID.substring(1) + "1", TestCompany.TEST_ID.substring(1) + "2", TestEmployee.TEST_ID, LocalDate.now().plusDays(1));

    /**
     * Represents the URL extension of the transaction's API endpoint.
     */
    public final static String URL_EXTENSION = "/api/transactions/";

    /**
     * Represents the URL extension of the transaction's API endpoint to get
     * incoming transactions.
     */
    public final static String URL_EXTENSION_INCOMING = URL_EXTENSION + "incoming";

    /**
     * Represents the URL extension of the transaction's API endpoint to get
     * outgoing transactions.
     */
    public final static String URL_EXTENSION_OUTGOING = URL_EXTENSION + "outgoing";

    /**
     * Represents the company's id key that used in transaction's API call.
     */
    public final static String COMP_ID_KEY = "compId";

    /**
     * Represents the employee's id key that used in transaction's API call.
     */
    public final static String EMP_ID_KEY = "empId";

    /**
     * Represents the date key that used in transaction's API call.
     */
    public final static String DATE_KEY = "date";

    /**
     * Represents the status key that used in transaction's API call.
     */
    public final static String STATUS_KEY = "status";

    /**
     * Create a transaction with necessary fields filled to use for testing.
     * 
     * @return the transaction to use for testing.
     */
    public static Transaction createTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(TEST_ID);
        transaction.setBorrowingCompany(TestCompany.createCompany());
        transaction.getBorrowingCompany().setUEN(TEST_ID.getBorrowingCompany());
        transaction.setLoanCompany(TestCompany.createCompany());
        transaction.getLoanCompany().setUEN(TEST_ID.getLoanCompany());
        transaction.setEmployee(TestEmployee.createEmployee());
        transaction.getEmployee().setCompany(transaction.getLoanCompany());
        transaction.setStartDate(TEST_ID.getStartDate());
        transaction.setEndDate(TEST_ID.getStartDate().plusDays(30));
        transaction.setStatus("Pending");
        transaction.setTotalCost(2000);
        return transaction;
    }

    /**
     * Create a transaction DTO with necessary fields filled to use for testing.
     * 
     * @return the transaction DTO to use for testing.
     */
    public static TransactionDTO createTransactionDTO() {
        Transaction transaction = createTransaction();
        return new TransactionDTO(transaction.getStartDate(), transaction.getEndDate(), transaction.getTotalCost(),
                transaction.getLoanCompany().getUEN(), transaction.getBorrowingCompany().getUEN(),
                transaction.getEmployee().getWorkPermitNumber(), transaction.getStatus());
    }

    /**
     * Create an invalid transaction DTO with necessary fields filled to use for testing.
     * 
     * @return the invalid transaction DTO to use for testing.
     */
    public static TransactionDTO createInvalidTransactionDTO() {
        Transaction transaction = createTransaction();
        return new TransactionDTO(transaction.getStartDate(), transaction.getEndDate(), transaction.getTotalCost(),
                transaction.getLoanCompany().getUEN(), transaction.getBorrowingCompany().getUEN(),
                transaction.getEmployee().getWorkPermitNumber(), null);
    }
}
