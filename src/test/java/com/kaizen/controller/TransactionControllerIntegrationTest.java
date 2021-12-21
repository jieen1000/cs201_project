package com.kaizen.controller;

import com.kaizen.model.*;
import com.kaizen.model.dto.TransactionDTO;
import com.kaizen.model.entity.Transaction;
import com.kaizen.repository.*;

import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * {@code TransactionControllerIntegrationTest} is a test class to do
 * integration testing from {@link TransactionController} using H2 embeded
 * database.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-07
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TransactionControllerIntegrationTest {
    /**
     * The transaction's controller used for testing.
     */
    @Autowired
    private TransactionController transactionController;

    /**
     * The transaction's repository used for testing.
     */
    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * The company's repository used for testing.
     */
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * The employee's repository used for testing.
     */
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * {@code tearDown} is apply after each tests to clear database
     */
    @AfterEach
    public void tearDown() {
        transactionRepository.deleteAll();
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    /**
     * {@code getIncomingTransactions_Found_ExpectOKFound} is a test on
     * {@link TransactionController#getIncomingTransactions(String)} to verify if
     * the method will return the list of all transaction DTOs of a loan company
     * with Http Status Ok(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getIncomingTransactions_Found_ExpectOKFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        companyRepository.save(transaction.getBorrowingCompany());
        companyRepository.save(transaction.getLoanCompany());
        companyRepository.flush();
        employeeRepository.save(transaction.getEmployee());
        employeeRepository.flush();
        transactionRepository.save(transaction);
        transactionRepository.flush();
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        transactionDTOs.add(TestTransaction.createTransactionDTO());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestTransaction.URL_EXTENSION_INCOMING)
                .param(TestTransaction.COMP_ID_KEY, TestTransaction.TEST_ID.getLoanCompany());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
                        MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(transactionDTOs)));
    }

    /**
     * {@code getOutgoingTransactions_Found_ExpectOKFound} is a test on
     * {@link TransactionController#getOutgoingTransactions(String)} to verify if
     * the method will return the list of all transaction DTOs of a borrowing
     * company with Http Status Ok(200) and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getOutgoingTransactions_Found_ExpectOKFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        companyRepository.save(transaction.getBorrowingCompany());
        companyRepository.save(transaction.getLoanCompany());
        companyRepository.flush();
        employeeRepository.save(transaction.getEmployee());
        employeeRepository.flush();
        transactionRepository.save(transaction);
        transactionRepository.flush();
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        transactionDTOs.add(TestTransaction.createTransactionDTO());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestTransaction.URL_EXTENSION_OUTGOING)
                .param(TestTransaction.COMP_ID_KEY, TestTransaction.TEST_ID.getBorrowingCompany());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
                        MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(transactionDTOs)));
    }

    /**
     * {@code replaceTransaction_NotFoundLoanCompany_ExpectNotFound} is a test on
     * {@link TransactionController#replaceTransaction(String, String, Transaction)}
     * to verify if the method will return Http Status Not Found(404) when the loan
     * company with the specific id is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void replaceTransaction_NotFoundLoanCompany_ExpectNotFound() throws Exception {
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code replaceTransaction_NotFoundBorrowingCompany_ExpectNotFound} is a test
     * on
     * {@link TransactionController#replaceTransaction(String, String, Transaction)}
     * to verify if the method will return Http Status Not Found(404) when the
     * borrowing company with the specific id is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void replaceTransaction_NotFoundBorrowingCompany_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        companyRepository.save(transaction.getLoanCompany());
        companyRepository.flush();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code replaceTransaction_NotFoundEmployee_ExpectNotFound} is a test on
     * {@link TransactionController#replaceTransaction(String, String, Transaction)}
     * to verify if the method will return Http Status Not Found(404) when the
     * employee with the specific id is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void replaceTransaction_NotFoundEmployee_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        companyRepository.save(transaction.getBorrowingCompany());
        companyRepository.save(transaction.getLoanCompany());
        companyRepository.flush();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code replaceTransaction_NotFoundTransaction_ExpectNotFound} is a test on
     * {@link TransactionController#replaceTransaction(String, String, Transaction)}
     * to verify if the method will return Http Status Not Found(404) when the
     * transaction with the specific employee id and start date is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void replaceTransaction_NotFoundTransaction_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        companyRepository.save(transaction.getBorrowingCompany());
        companyRepository.save(transaction.getLoanCompany());
        companyRepository.flush();
        employeeRepository.save(transaction.getEmployee());
        employeeRepository.flush();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code replaceTransaction_Replaced_ExpectOKReplaced} is a test on
     * {@link TransactionController#replaceTransaction(String, String, Transaction)}
     * to verify if the method will return the updated specific transaction DTO with
     * Http Status OK(200) and content type of application/json.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void replaceTransaction_Replaced_ExpectOKReplaced() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        companyRepository.save(transaction.getBorrowingCompany());
        companyRepository.save(transaction.getLoanCompany());
        companyRepository.flush();
        employeeRepository.save(transaction.getEmployee());
        employeeRepository.flush();
        transactionRepository.save(transaction);
        transactionRepository.flush();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
                        MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(transactionDTO)));
    }

    /**
     * {@code updateTransactionStatus_NotFoundTransaction_ExpectNotFound} is a test
     * on
     * {@link TransactionController#updateTransactionStatus(String, String, String)}
     * to verify if the method will return Http Status Not Found(404) when the
     * transaction with the specific employee id and start date is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateTransactionStatus_NotFoundTransaction_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.EMP_ID_KEY, transaction.getEmployee().getWorkPermitNumber())
                .param(TestTransaction.DATE_KEY, transaction.getStartDate().toString())
                .param(TestTransaction.STATUS_KEY, transaction.getStatus());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code updateTransactionStatus_NotFoundTransaction_ExpectNotFound} is a test
     * on
     * {@link TransactionController#updateTransactionStatus(String, String, String)}
     * to verify if the method will return the updated specific transaction DTO with
     * Http Status OK(200) and content type of application/json.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateTransactionStatus_Updated_ExpectOkUpdated() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        companyRepository.save(transaction.getBorrowingCompany());
        companyRepository.save(transaction.getLoanCompany());
        companyRepository.flush();
        employeeRepository.save(transaction.getEmployee());
        employeeRepository.flush();
        transactionRepository.save(transaction);
        transactionRepository.flush();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.EMP_ID_KEY, transaction.getEmployee().getWorkPermitNumber())
                .param(TestTransaction.DATE_KEY, transaction.getStartDate().toString())
                .param(TestTransaction.STATUS_KEY, transaction.getStatus());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
                        MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(transactionDTO)));
    }

    /**
     * {@code createTransaction_NotFoundLoanCompany_ExpectNotFound} is a test on
     * {@link TransactionController#createTransaction(String, String, Transaction)}
     * to verify if the method will return Http Status Not Found(404) when the loan
     * company with the specific id is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createTransaction_NotFoundLoanCompany_ExpectNotFound() throws Exception {
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code createTransaction_NotFoundBorrowingCompany_ExpectNotFound} is a test
     * on
     * {@link TransactionController#createTransaction(String, String, Transaction)}
     * to verify if the method will return Http Status Not Found(404) when the
     * borrowing company with the specific id is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createTransaction_NotFoundBorrowingCompany_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        companyRepository.save(transaction.getLoanCompany());
        companyRepository.flush();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code createTransaction_NotFoundEmployee_ExpectNotFound} is a test on
     * {@link TransactionController#createTransaction(String, String, Transaction)}
     * to verify if the method will return Http Status Not Found(404) when the
     * employee with the specific id is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createTransaction_NotFoundEmployee_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        companyRepository.save(transaction.getBorrowingCompany());
        companyRepository.save(transaction.getLoanCompany());
        companyRepository.flush();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code createTransaction_DateCollide_ExpectConflict} is a test on
     * {@link TransactionController#createTransaction(String, String, Transaction)}
     * to verify if the method will return Http Status Conflict(409) when the
     * transaction dates collided with existing transactions of the transaction's
     * employee.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createTransaction_DateCollide_ExpectConflict() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        companyRepository.save(transaction.getBorrowingCompany());
        companyRepository.save(transaction.getLoanCompany());
        companyRepository.flush();
        employeeRepository.save(transaction.getEmployee());
        employeeRepository.flush();
        transactionRepository.save(transaction);
        transactionRepository.flush();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    /**
     * {@code createTransaction_Created_ExpectCreated} is a test on
     * {@link TransactionController#createTransaction(String, String, Transaction)}
     * to verify if the method will return the created specific transaction DTO with
     * Http Status OK(200) and content type of application/json.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createTransaction_Created_ExpectCreated() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        companyRepository.save(transaction.getBorrowingCompany());
        companyRepository.save(transaction.getLoanCompany());
        companyRepository.flush();
        employeeRepository.save(transaction.getEmployee());
        employeeRepository.flush();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
                        MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(transactionDTO)));
    }

    /**
     * {@code deleteTransaction_NotFoundTransaction_ExpectNotFound} is a test on
     * {@link TransactionController#deleteTransaction(String, String)} to verify if
     * the method will return Http Status Not Found(404) when the transaction with
     * the specific employee id and start date is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteTransaction_NotFoundTransaction_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.EMP_ID_KEY, transaction.getEmployee().getWorkPermitNumber())
                .param(TestTransaction.DATE_KEY, transaction.getStartDate().toString());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * {@code deleteTransaction_NotFoundTransaction_ExpectNotFound} is a test on
     * {@link TransactionController#deleteTransaction(String, String)} to verify if
     * the method will return the Http Status OK(200).
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteTransaction_Deleted_ExpectOkDeleted() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        companyRepository.save(transaction.getBorrowingCompany());
        companyRepository.save(transaction.getLoanCompany());
        companyRepository.flush();
        employeeRepository.save(transaction.getEmployee());
        employeeRepository.flush();
        transactionRepository.save(transaction);
        transactionRepository.flush();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.EMP_ID_KEY, transaction.getEmployee().getWorkPermitNumber())
                .param(TestTransaction.DATE_KEY, transaction.getStartDate().toString());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
