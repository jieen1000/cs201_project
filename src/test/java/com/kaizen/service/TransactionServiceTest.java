package com.kaizen.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import com.kaizen.exceptions.*;
import com.kaizen.model.TestTransaction;
import com.kaizen.model.entity.Transaction;
import com.kaizen.model.entity.TransactionKey;
import com.kaizen.repository.TransactionRepository;
import com.kaizen.service.transaction.TransactionService;
import com.kaizen.service.transaction.TransactionServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@code TransactionServiceTest} is a test class to do unit testing on
 * {@link TransactionService} using {@link TransactionServiceImpl}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@ContextConfiguration(classes = { TransactionServiceImpl.class })
@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {
    /**
     * The mocked transaction's repository used for testing.
     */
    @MockBean
    private TransactionRepository transactionRepository;

    /**
     * The transaction's service used for testing.
     */
    @Autowired
    private TransactionService transactionService;

    /**
     * {@code listTransactions_Found_ReturnFound} is a test on
     * {@link TransactionService#listTransactions()} to verify if the method will call
     * {@link TransactionRepository#findAll()} and return the list of all transactions.
     */
    @Test
    void listTransactions_Found_ReturnFound() {
        List<Transaction> transactions = new ArrayList<>();
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> foundTransactions = transactionService.listTransactions();

        assertSame(transactions, foundTransactions);
        verify(transactionRepository).findAll();
    }

    /**
     * {@code addTransaction_Null_ThrowNullValueException} is a test on
     * {@link TransactionService#addTransaction(Transaction)} to verify if the method will throw
     * {@link NullValueException} when the specific transaction is null.
     */
    @Test
    void addTransaction_Null_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            transactionService.addTransaction(null);
        });
    }

    /**
     * {@code addTransaction_SameDateCollide_InvalidDateException} is a test on
     * {@link TransactionService#addTransaction(Transaction)} to verify if the
     * method will call
     * {@link TransactionRepository#findTransactionsByIdEmployee(String)} and throw
     * {@link InvalidDateException} when the employee have collide dates.
     */
    @Test
    void addTransaction_SameDateCollide_InvalidDateException() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = TestTransaction.createTransaction();
        transactions.add(transaction);
        when(transactionRepository.findTransactionsByIdEmployee(transaction.getEmployee().getWorkPermitNumber()))
                .thenReturn(transactions);

        assertThrows(InvalidDateException.class, () -> {
            transactionService.addTransaction(transaction);
        });

        verify(transactionRepository).findTransactionsByIdEmployee(transaction.getEmployee().getWorkPermitNumber());
    }

    /**
     * {@code addTransaction_StartDateCollide_InvalidDateException} is a test on
     * {@link TransactionService#addTransaction(Transaction)} to verify if the
     * method will call
     * {@link TransactionRepository#findTransactionsByIdEmployee(String)} and throw
     * {@link InvalidDateException} when the employee have collide start date.
     */
    @Test
    void addTransaction_StartDateCollide_InvalidDateException() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = TestTransaction.createTransaction();
        transactions.add(transaction);
        when(transactionRepository.findTransactionsByIdEmployee(transaction.getEmployee().getWorkPermitNumber()))
                .thenReturn(transactions);
        LocalDate startDate = transaction.getStartDate().plusDays(1);
        transaction.setStartDate(startDate);
        transaction.getId().setStartDate(startDate);

        assertThrows(InvalidDateException.class, () -> {
            transactionService.addTransaction(transaction);
        });

        verify(transactionRepository).findTransactionsByIdEmployee(transaction.getEmployee().getWorkPermitNumber());
    }

    /**
     * {@code addTransaction_EndDateCollide_InvalidDateException} is a test on
     * {@link TransactionService#addTransaction(Transaction)} to verify if the
     * method will call
     * {@link TransactionRepository#findTransactionsByIdEmployee(String)} and throw
     * {@link InvalidDateException} when the employee have collide end date.
     */
    @Test
    void addTransaction_EndDateCollide_InvalidDateException() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = TestTransaction.createTransaction();
        transactions.add(transaction);
        when(transactionRepository.findTransactionsByIdEmployee(transaction.getEmployee().getWorkPermitNumber()))
                .thenReturn(transactions);
        LocalDate startDate = transaction.getStartDate().minusDays(1);
        transaction.setStartDate(startDate);
        transaction.getId().setStartDate(startDate);
        LocalDate endDate = transaction.getEndDate().minusDays(1);
        transaction.setEndDate(endDate);

        assertThrows(InvalidDateException.class, () -> {
            transactionService.addTransaction(transaction);
        });

        verify(transactionRepository).findTransactionsByIdEmployee(transaction.getEmployee().getWorkPermitNumber());
    }

    /**
     * {@code addTransaction_NewBeforeDates_ReturnSaved} is a test on
     * {@link TransactionService#addTransaction(Transaction)} to verify if the
     * method will call
     * {@link TransactionRepository#findTransactionsByIdEmployee(String)} and
     * {@link TransactionRepository#save(Transaction)} and save and return the
     * specific transaction.
     */
    @Test
    void addTransaction_NewBeforeDates_ReturnSaved() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = TestTransaction.createTransaction();
        LocalDate startDate = transaction.getEndDate().plusDays(1);
        transaction.setStartDate(startDate);
        TransactionKey key = transaction.getId();
        transaction.getId().setStartDate(startDate);
        LocalDate endDate = startDate.plusDays(1);
        transaction.setEndDate(endDate);
        transactions.add(transaction);
        when(transactionRepository.findTransactionsByIdEmployee(transaction.getEmployee().getWorkPermitNumber()))
                .thenReturn(transactions);
        transaction.setId(new TransactionKey(key.getLoanCompany(), key.getBorrowingCompany(), key.getEmployee(),
                LocalDate.now().plusDays(1)));
        transaction = TestTransaction.createTransaction();
                transaction.setStartDate(LocalDate.now().plusDays(1));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction savedTransaction = transactionService.addTransaction(transaction);

        assertSame(transaction, savedTransaction);
        verify(transactionRepository).findTransactionsByIdEmployee(transaction.getEmployee().getWorkPermitNumber());
        verify(transactionRepository).save(transaction);
    }

    /**
     * {@code addTransaction_NewAfterDates_ReturnSaved} is a test on
     * {@link TransactionService#addTransaction(Transaction)} to verify if the
     * method will call
     * {@link TransactionRepository#findTransactionsByIdEmployee(String)} and
     * {@link TransactionRepository#save(Transaction)} and save and return the
     * specific transaction.
     */
    @Test
    void addTransaction_NewAfterDates_ReturnSaved() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(TestTransaction.createTransaction());
        Transaction transaction = TestTransaction.createTransaction();
        LocalDate startDate = transaction.getEndDate();
        transaction.setStartDate(startDate);
        transaction.getId().setStartDate(startDate);
        LocalDate endDate = startDate.plusDays(1);
        transaction.setEndDate(endDate);
        when(transactionRepository.findTransactionsByIdEmployee(transaction.getEmployee().getWorkPermitNumber()))
                .thenReturn(transactions);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction savedTransaction = transactionService.addTransaction(transaction);

        assertSame(transaction, savedTransaction);
        verify(transactionRepository).findTransactionsByIdEmployee(transaction.getEmployee().getWorkPermitNumber());
        verify(transactionRepository).save(transaction);
    }

    /**
     * {@code deleteTransaction_NotFound_ThrowObjectNotExistsException} is a test on
     * {@link TransactionService#deleteTransaction(String)} to verify if the method
     * will call {@link TransactionRepository#findById(String)} and throw
     * {@link ObjectNotExistsException} when the transaction with the specific id
     * does not exists.
     */
    @Test
    void deleteTransaction_NotFound_ThrowObjectNotExistsException() {
        when(transactionRepository.findById(TestTransaction.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotExistsException.class, () -> {
            transactionService.deleteTransaction(TestTransaction.TEST_ID);
        });

        verify(transactionRepository).findById(TestTransaction.TEST_ID);
    }

    /**
     * {@code deleteTransaction_Deleted} is a test on
     * {@link TransactionService#deleteTransaction(String)} to verify if the method
     * will call {@link TransactionRepository#findById(String)} and
     * {@link TransactionRepository#deleteById(String)} and delete the transaction
     * with specific id.
     */
    @Test
    void deleteTransaction_Deleted() {
        when(transactionRepository.findById(TestTransaction.TEST_ID))
                .thenReturn(Optional.of(TestTransaction.createTransaction()));
        doNothing().when(transactionRepository).deleteById(TestTransaction.TEST_ID);

        transactionService.deleteTransaction(TestTransaction.TEST_ID);

        verify(transactionRepository).findById(TestTransaction.TEST_ID);
        verify(transactionRepository).deleteById(TestTransaction.TEST_ID);
    }

    /**
     * {@code getTransactionsByLoanCompany_NullCompId_ThrowNullValueException} is a
     * test on {@link TransactionService#getTransactionsByLoanCompany(String)} to
     * verify if the method will throw {@link NullValueException} when the specific company
     * id is null.
     */
    @Test
    void getTransactionsByLoanCompany_NullCompId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            transactionService.getTransactionsByLoanCompany(null);
        });
    }

    /**
     * {@code getTransactionsByLoanCompany_Found_ReturnFound} is a test on
     * {@link TransactionService#getTransactionsByLoanCompany(String)} to verify if
     * the method will call
     * {@link TransactionRepository#findTransactionsByIdLoanCompany(String)} and
     * return the list of all transactions of a loan company.
     */
    @Test
    void getTransactionsByLoanCompany_Found_ReturnFound() {
        List<Transaction> transactions = new ArrayList<>();
        when(transactionRepository
                .findTransactionsByIdLoanCompany(TestTransaction.TEST_ID
                        .getLoanCompany()))
                .thenReturn(transactions);

        List<Transaction> found = transactionService.getTransactionsByLoanCompany(TestTransaction.TEST_ID.getLoanCompany());

        assertSame(transactions, found);
        verify(transactionRepository).findTransactionsByIdLoanCompany(TestTransaction.TEST_ID.getLoanCompany());
    }

    /**
     * {@code getTransactionsByBorrowingCompany_NullCompId_ThrowNullValueException}
     * is a test on
     * {@link TransactionService#getTransactionsByBorrowingCompany(String)} to
     * verify if the method will throw {@link NullValueException} when the specific company
     * id is null.
     */
    @Test
    void getTransactionsByBorrowingCompany_NullCompId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            transactionService.getTransactionsByBorrowingCompany(null);
        });
    }

    /**
     * {@code getTransactionsByBorrowingCompany_Found_ReturnFound} is a test on
     * {@link TransactionService#getTransactionsByBorrowingCompany(String)} to
     * verify if the method will call
     * {@link TransactionRepository#findTransactionsByIdBorrowingCompany(String)} and
     * return the list of all transactions of a borrowing company.
     */
    @Test
    void getTransactionsByBorrowingCompany_Found_ReturnFound() {
        List<Transaction> transactions = new ArrayList<>();
        when(transactionRepository.findTransactionsByIdBorrowingCompany(TestTransaction.TEST_ID.getBorrowingCompany()))
                .thenReturn(transactions);

        List<Transaction> found = transactionService
                .getTransactionsByBorrowingCompany(TestTransaction.TEST_ID.getBorrowingCompany());

        assertEquals(transactions, found);
        verify(transactionRepository).findTransactionsByIdBorrowingCompany(TestTransaction.TEST_ID.getBorrowingCompany());
    }

    /**
     * {@code getTransactionByEmployeeAndStartDate_NullEmpId_ThrowNullValueException}
     * is a test on
     * {@link TransactionService#getTransactionByEmployeeAndStartDate(String, LocalDate)}
     * to verify if the method will throw {@link NullValueException} when the
     * specific employee id is null.
     */
    @Test
    void getTransactionByEmployeeAndStartDate_NullEmpId_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            transactionService.getTransactionByEmployeeAndStartDate(null, TestTransaction.TEST_ID.getStartDate());
        });
    }

    /**
     * {@code getTransactionByEmployeeAndStartDate_NullDate_ThrowNullValueException}
     * is a test on
     * {@link TransactionService#getTransactionByEmployeeAndStartDate(String, LocalDate)}
     * to verify if the method will throw {@link NullValueException} when the
     * specific date is null.
     */
    @Test
    void getTransactionByEmployeeAndStartDate_NullDate_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            transactionService.getTransactionByEmployeeAndStartDate(
                    TestTransaction.TEST_ID.getEmployee(), null);
        });
    }

    /**
     * {@code getTransactionByEmployeeAndStartDate_NotExist_ThrowObjectNotExistsException}
     * is a test on
     * {@link TransactionService#getTransactionByEmployeeAndStartDate(String, LocalDate)}
     * to verify if the method will call
     * {@link TransactionRepository#findTransactionByIdEmployeeAndStartDate(String, java.time.LocalDate)}
     * and throw {@link ObjectNotExistsException} when the transaction does not
     * exists in the repository.
     */
    @Test
    void getTransactionByEmployeeAndStartDate_NotExist_ThrowObjectNotExistsException() {
        when(transactionRepository.findTransactionByIdEmployeeAndStartDate(TestTransaction.TEST_ID.getEmployee(),
                TestTransaction.TEST_ID.getStartDate())).thenReturn(null);

        assertThrows(ObjectNotExistsException.class, () -> {
            transactionService.getTransactionByEmployeeAndStartDate(TestTransaction.TEST_ID.getEmployee(),
                    TestTransaction.TEST_ID.getStartDate());
        });

        verify(transactionRepository).findTransactionByIdEmployeeAndStartDate(TestTransaction.TEST_ID.getEmployee(),
                TestTransaction.TEST_ID.getStartDate());
    }

    /**
     * {@code getTransactionByEmployeeAndStartDate_Found_ReturnFound} is a test on
     * {@link TransactionService#getTransactionByEmployeeAndStartDate(String, LocalDate)}
     * to verify if the method will call
     * {@link TransactionRepository#findTransactionByIdEmployeeAndStartDate(String, java.time.LocalDate)}
     * and return the specific transaction.
     */
    @Test
    void getTransactionByEmployeeAndStartDate_Found_ReturnFound() {
        Transaction transaction = TestTransaction.createTransaction();
        when(transactionRepository.findTransactionByIdEmployeeAndStartDate(TestTransaction.TEST_ID.getEmployee(),
                TestTransaction.TEST_ID.getStartDate())).thenReturn(transaction);

        Transaction found = transactionService.getTransactionByEmployeeAndStartDate(
                TestTransaction.TEST_ID.getEmployee(), TestTransaction.TEST_ID.getStartDate());

        assertSame(transaction, found);
        verify(transactionRepository).findTransactionByIdEmployeeAndStartDate(TestTransaction.TEST_ID.getEmployee(),
                TestTransaction.TEST_ID.getStartDate());
    }

    /**
     * {@code updateTransaction_NullTransaction_ThrowNullValueException} is a test
     * on {@link TransactionService#updateTransaction(Transaction)} to verify if the
     * method will throw {@link NullValueException} when the specific transaction is
     * null .
     */
    @Test
    void updateTransaction_NullTransaction_ThrowNullValueException() {
        assertThrows(NullValueException.class, () -> {
            transactionService.updateTransaction(null);
        });
    }

    /**
     * {@code updateTransaction_NotExist_ThrowObjectNotExistsException} is a test on
     * {@link TransactionService#updateTransaction(Transaction)} to verify if the
     * method will call
     * {@link TransactionRepository#findTransactionByIdEmployeeAndStartDate(String, java.time.LocalDate)}
     * and throw {@link ObjectNotExistsException} when the transaction does not
     * exists in the repository.
     */
    @Test
    void updateTransaction_NotExist_ThrowObjectNotExistsException() {
        when(transactionRepository.findTransactionByIdEmployeeAndStartDate(TestTransaction.TEST_ID.getEmployee(),
                TestTransaction.TEST_ID.getStartDate())).thenReturn(null);

        assertThrows(ObjectNotExistsException.class, () -> {
            transactionService.updateTransaction(TestTransaction.createTransaction());
        });

        verify(transactionRepository).findTransactionByIdEmployeeAndStartDate(TestTransaction.TEST_ID.getEmployee(),
                TestTransaction.TEST_ID.getStartDate());
    }

    /**
     * {@code updateTransaction_Updated_ReturnUpdated} is a test on
     * {@link TransactionService#updateTransaction(Transaction)} to verify if the
     * method will call
     * {@link TransactionRepository#findTransactionByIdEmployeeAndStartDate(String, java.time.LocalDate)},
     * {@link TransactionRepository#deleteById(TransactionKey)} and
     * {@link TransactionRepository#save(Transaction)} and return updated
     * transaction.
     */
    @Test
    void updateTransaction_Updated_ReturnUpdated() {
        Transaction transaction = TestTransaction.createTransaction();
        when(transactionRepository.findTransactionByIdEmployeeAndStartDate(TestTransaction.TEST_ID.getEmployee(),
                TestTransaction.TEST_ID.getStartDate())).thenReturn(transaction);
        doNothing().when(transactionRepository).deleteById(transaction.getId());
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction updated = transactionService.updateTransaction(transaction);

        assertSame(transaction, updated);
        verify(transactionRepository).findTransactionByIdEmployeeAndStartDate(TestTransaction.TEST_ID.getEmployee(),
                TestTransaction.TEST_ID.getStartDate());
        verify(transactionRepository).deleteById(transaction.getId());
        verify(transactionRepository).save(transaction);
    }
}
