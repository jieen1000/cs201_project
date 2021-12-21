package com.kaizen.controller;

import static org.mockito.Mockito.*;

import com.kaizen.exceptions.InvalidDateException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.*;
import com.kaizen.model.dto.TransactionDTO;
import com.kaizen.model.entity.Transaction;
import com.kaizen.model.entity.TransactionKey;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.employee.EmployeeService;
import com.kaizen.service.transaction.TransactionService;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * {@code TransactionControllerTest} is a test class to do unit testing on
 * {@link TransactionController}.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@ContextConfiguration(classes = { TransactionController.class })
@ExtendWith(SpringExtension.class)
class TransactionControllerTest {
    /**
     * The transaction's controller used for testing.
     */
    @Autowired
    private TransactionController transactionController;

    /**
     * The mocked transaction's service used for testing.
     */
    @MockBean
    private TransactionService transactionService;

    /**
     * The mocked company's service used for testing.
     */
    @MockBean
    private CompanyService companyService;

    /**
     * The mocked employee's service used for testing.
     */
    @MockBean
    private EmployeeService employeeService;

    /**
     * {@code getIncomingTransactions_MissingCompId_ExpectBadRequest} is a test on
     * {@link TransactionController#getIncomingTransactions(String)} to verify if the method will
     * return Http Status Bad Request(400) when the specific company's id is missing
     * in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getIncomingTransactions_MissingCompId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestTransaction.URL_EXTENSION_INCOMING);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code getIncomingTransactions_Found_ExpectOKFound} is a test on
     * {@link TransactionController#getIncomingTransactions(String)} to verify if
     * the method will call {@link TransactionService#listTransactions()} and
     * return the list of all transaction DTOs of a loan company with Http Status Ok(200)
     * and content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getIncomingTransactions_Found_ExpectOKFound() throws Exception {
        String companyId = TestCompany.TEST_ID + "1";
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(TestTransaction.createTransaction());
        transactions.add(TestTransaction.createTransaction());
        transactions.get(1).getLoanCompany().setUEN(companyId);
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        transactionDTOs.add(TestTransaction.createTransactionDTO());
        transactionDTOs.get(0).setLoanCompanyId(companyId);
        when(transactionService.listTransactions()).thenReturn(transactions);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(TestTransaction.URL_EXTENSION_INCOMING)
                .param(TestTransaction.COMP_ID_KEY, companyId);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(transactionDTOs)));

        verify(transactionService).listTransactions();
    }

    /**
     * {@code getOutgoingTransactions_MissingCompId_ExpectBadRequest} is a test on
     * {@link TransactionController#getOutgoingTransactions(String)} to verify if
     * the method will return Http Status Bad Request(400) when the specific
     * company's id is missing in the call.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getOutgoingTransactions_MissingCompId_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestTransaction.URL_EXTENSION_OUTGOING);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code getOutgoingTransactions_Found_ExpectOKFound} is a test on
     * {@link TransactionController#getOutgoingTransactions(String)} to verify if
     * the method will call {@link TransactionService#listTransactions()} and return
     * the list of all transaction DTOs of a borrowing company with Http Status Ok(200) and
     * content type of application/json.
     * 
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void getOutgoingTransactions_Found_ExpectOKFound() throws Exception {
        String companyId = TestCompany.TEST_ID + "1";
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(TestTransaction.createTransaction());
        transactions.add(TestTransaction.createTransaction());
        transactions.get(1).getBorrowingCompany().setUEN(companyId);
        List<TransactionDTO> transactionDTOs = new ArrayList<>();
        transactionDTOs.add(TestTransaction.createTransactionDTO());
        transactionDTOs.get(0).setBorrowingCompanyId(companyId);
        when(transactionService.listTransactions()).thenReturn(transactions);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(TestTransaction.URL_EXTENSION_OUTGOING)
                .param(TestTransaction.COMP_ID_KEY, companyId);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
                        MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(transactionDTOs)));

        verify(transactionService).listTransactions();
    }

    /**
     * {@code replaceTransaction_MissingTransaction_ExpectBadRequest} is a test on
     * {@link TransactionController#replaceTransaction(TransactionDTO)} to verify if
     * the method will return Http Status Bad Request(400) when the specific
     * transaction DTO is missing in the call.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void replaceTransaction_MissingTransactionDTO_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestTransaction.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code replaceTransaction_MissingTransaction_ExpectBadRequest} is a test on
     * {@link TransactionController#replaceTransaction(TransactionDTO)} to verify if
     * the method will return Http Status Bad Request(400) when the specific
     * transaction DTO is invalid in the call.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void replaceTransaction_InvalidTransaction_ExpectBadRequest() throws Exception {
        TransactionDTO transactionDTO = TestTransaction.createInvalidTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code replaceTransaction_NotFoundLoanCompany_ExpectNotFound} is a test on
     * {@link TransactionController#replaceTransaction(String, String, Transaction)}
     * to verify if the method will call {@link CompanyService#getCompany(String)}
     * and return Http Status Not Found(404) when the loan company with the specific
     * id is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void replaceTransaction_NotFoundLoanCompany_ExpectNotFound() throws Exception {
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        when(companyService.getCompany(transactionDTO.getLoanCompanyId())).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(transactionDTO.getLoanCompanyId());
    }

    /**
     * {@code replaceTransaction_NotFoundBorrowingCompany_ExpectNotFound} is a test
     * on
     * {@link TransactionController#replaceTransaction(String, String, Transaction)}
     * to verify if the method will call {@link CompanyService#getCompany(String)}
     * and return Http Status Not Found(404) when the borrowing company with the specific
     * id is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void replaceTransaction_NotFoundBorrowingCompany_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        when(companyService.getCompany(transactionDTO.getLoanCompanyId())).thenReturn(transaction.getLoanCompany());
        when(companyService.getCompany(transactionDTO.getBorrowingCompanyId()))
                .thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(transactionDTO.getLoanCompanyId());
        verify(companyService).getCompany(transactionDTO.getBorrowingCompanyId());
    }

    /**
     * {@code replaceTransaction_NotFoundEmployee_ExpectNotFound} is a test on
     * {@link TransactionController#replaceTransaction(String, String, Transaction)}
     * to verify if the method will call {@link CompanyService#getCompany(String)}
     * and {@link EmployeeService#getEmployee(String)} and return Http Status Not
     * Found(404) when the employee with the specific id is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void replaceTransaction_NotFoundEmployee_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        when(companyService.getCompany(transactionDTO.getLoanCompanyId())).thenReturn(transaction.getLoanCompany());
        when(companyService.getCompany(transactionDTO.getBorrowingCompanyId()))
                .thenReturn(transaction.getBorrowingCompany());
        when(employeeService.getEmployee(transactionDTO.getEmployeeId())).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(transactionDTO.getLoanCompanyId());
        verify(companyService).getCompany(transactionDTO.getBorrowingCompanyId());
        verify(employeeService).getEmployee(transactionDTO.getEmployeeId());
    }

    /**
     * {@code replaceTransaction_NotFoundTransaction_ExpectNotFound} is a test on
     * {@link TransactionController#replaceTransaction(String, String, Transaction)}
     * to verify if the method will call {@link CompanyService#getCompany(String)},
     * {@link EmployeeService#getEmployee(String)} and
     * {@link TransactionService#updateTransaction(Transaction)} and return Http
     * Status Not Found(404) when the transaction with the specific employee id and
     * start date is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void replaceTransaction_NotFoundTransaction_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        when(companyService.getCompany(transactionDTO.getLoanCompanyId())).thenReturn(transaction.getLoanCompany());
        when(companyService.getCompany(transactionDTO.getBorrowingCompanyId()))
                .thenReturn(transaction.getBorrowingCompany());
        when(employeeService.getEmployee(transactionDTO.getEmployeeId())).thenReturn(transaction.getEmployee());
        when(transactionService.updateTransaction(any(Transaction.class))).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(transactionDTO.getLoanCompanyId());
        verify(companyService).getCompany(transactionDTO.getBorrowingCompanyId());
        verify(employeeService).getEmployee(transactionDTO.getEmployeeId());
        verify(transactionService).updateTransaction(any(Transaction.class));
    }

    /**
     * {@code replaceTransaction_Replaced_ExpectOKReplaced} is a test on
     * {@link TransactionController#replaceTransaction(String, String, Transaction)}
     * to verify if the method will call {@link CompanyService#getCompany(String)},
     * {@link EmployeeService#getEmployee(String)} and
     * {@link TransactionService#updateTransaction(Transaction)} and return the
     * updated specific transaction DTO with Http Status OK(200) and content type of
     * application/json.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void replaceTransaction_Replaced_ExpectOKReplaced() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        when(companyService.getCompany(transactionDTO.getLoanCompanyId())).thenReturn(transaction.getLoanCompany());
        when(companyService.getCompany(transactionDTO.getBorrowingCompanyId()))
                .thenReturn(transaction.getBorrowingCompany());
        when(employeeService.getEmployee(transactionDTO.getEmployeeId()))
                .thenReturn(transaction.getEmployee());
        when(transactionService.updateTransaction(any(Transaction.class))).thenReturn(transaction);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(TestTransaction.URL_EXTENSION).content(TestJsonConverter.writeValueAsString(transactionDTO))
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(
                        transactionDTO)));

        verify(companyService).getCompany(transactionDTO.getLoanCompanyId());
        verify(companyService).getCompany(transactionDTO.getBorrowingCompanyId());
        verify(employeeService).getEmployee(transactionDTO.getEmployeeId());
        verify(transactionService).updateTransaction(any(Transaction.class));
    }

    /**
     * {@code updateTransactionStatus_MissingEmpId_ExpectBadRequest} is a test on
     * {@link TransactionController#updateTransactionStatus(String, String, String)} to
     * verify if the method will return Http Status Bad Request(400) when the
     * specific employee's id is missing in the call.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateTransactionStatus_MissingEmpId_ExpectBadRequest() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.DATE_KEY, transaction.getStartDate().toString())
                .param(TestTransaction.STATUS_KEY, transaction.getStatus());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateTransactionStatus_MissingDate_ExpectBadRequest} is a test on
     * {@link TransactionController#updateTransactionStatus(String, String, String)}
     * to verify if the method will return Http Status Bad Request(400) when the
     * specific date is missing in the call.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateTransactionStatus_MissingDate_ExpectBadRequest() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.EMP_ID_KEY, transaction.getEmployee().getWorkPermitNumber())
                .param(TestTransaction.STATUS_KEY, transaction.getStatus());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateTransactionStatus_MissingStatus_ExpectBadRequest} is a test on
     * {@link TransactionController#updateTransactionStatus(String, String, String)}
     * to verify if the method will return Http Status Bad Request(400) when the
     * specific status is missing in the call.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateTransactionStatus_MissingStatus_ExpectBadRequest() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.EMP_ID_KEY, transaction.getEmployee().getWorkPermitNumber())
                .param(TestTransaction.DATE_KEY, transaction.getStartDate().toString());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code updateTransactionStatus_NotFoundTransaction_ExpectNotFound} is a test
     * on
     * {@link TransactionController#updateTransactionStatus(String, String, String)}
     * to verify if the method will call
     * {@link TransactionService#getTransactionByEmployeeAndStartDate(String, LocalDate)} and return Http Status Not
     * Found(404) when the transaction with the specific employee id and start date
     * is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateTransactionStatus_NotFoundTransaction_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        when(transactionService.getTransactionByEmployeeAndStartDate(transaction.getEmployee().getWorkPermitNumber(),
                transaction.getStartDate())).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.EMP_ID_KEY, transaction.getEmployee().getWorkPermitNumber())
                .param(TestTransaction.DATE_KEY, transaction.getStartDate().toString())
                .param(TestTransaction.STATUS_KEY, transaction.getStatus());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(transactionService).getTransactionByEmployeeAndStartDate(transaction.getEmployee().getWorkPermitNumber(),
                transaction.getStartDate());
    }

    /**
     * {@code updateTransactionStatus_NotFoundTransaction_ExpectNotFound} is a test
     * on
     * {@link TransactionController#updateTransactionStatus(String, String, String)}
     * to verify if the method will call
     * {@link TransactionService#getTransactionByEmployeeAndStartDate(String, LocalDate)}
     * and {@link TransactionService#updateTransaction(Transaction)} and return the
     * updated specific transaction DTO with Http Status OK(200) and content type of
     * application/json.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void updateTransactionStatus_Updated_ExpectOkUpdated() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        when(transactionService.getTransactionByEmployeeAndStartDate(transaction.getEmployee().getWorkPermitNumber(),
                transaction.getStartDate())).thenReturn(transaction);
        when(transactionService.updateTransaction(transaction)).thenReturn(transaction);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.EMP_ID_KEY, transaction.getEmployee().getWorkPermitNumber())
                .param(TestTransaction.DATE_KEY, transaction.getStartDate().toString())
                .param(TestTransaction.STATUS_KEY, transaction.getStatus());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
                        MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(transactionDTO)));

        verify(transactionService).getTransactionByEmployeeAndStartDate(transaction.getEmployee().getWorkPermitNumber(),
                transaction.getStartDate());
        verify(transactionService).updateTransaction(transaction);
    }

    /**
     * {@code createTransaction_MissingTransaction_ExpectBadRequest} is a test on
     * {@link TransactionController#createTransaction(TransactionDTO)} to verify if
     * the method will return Http Status Bad Request(400) when the specific
     * transaction DTO is missing in the call.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createTransaction_MissingTransactionDTO_ExpectBadRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestTransaction.URL_EXTENSION);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code createTransaction_MissingTransaction_ExpectBadRequest} is a test on
     * {@link TransactionController#createTransaction(TransactionDTO)} to verify if
     * the method will return Http Status Bad Request(400) when the specific
     * transaction DTO is invalid in the call.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createTransaction_InvalidTransaction_ExpectBadRequest() throws Exception {
        TransactionDTO transactionDTO = TestTransaction.createInvalidTransactionDTO();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code createTransaction_NotFoundLoanCompany_ExpectNotFound} is a test on
     * {@link TransactionController#createTransaction(String, String, Transaction)}
     * to verify if the method will call {@link CompanyService#getCompany(String)}
     * and return Http Status Not Found(404) when the loan company with the specific
     * id is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createTransaction_NotFoundLoanCompany_ExpectNotFound() throws Exception {
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        when(companyService.getCompany(transactionDTO.getLoanCompanyId())).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(transactionDTO.getLoanCompanyId());
    }

    /**
     * {@code createTransaction_NotFoundBorrowingCompany_ExpectNotFound} is a test
     * on
     * {@link TransactionController#createTransaction(String, String, Transaction)}
     * to verify if the method will call {@link CompanyService#getCompany(String)}
     * and return Http Status Not Found(404) when the borrowing company with the
     * specific id is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createTransaction_NotFoundBorrowingCompany_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        when(companyService.getCompany(transactionDTO.getLoanCompanyId())).thenReturn(transaction.getLoanCompany());
        when(companyService.getCompany(transactionDTO.getBorrowingCompanyId()))
                .thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(transactionDTO.getLoanCompanyId());
        verify(companyService).getCompany(transactionDTO.getBorrowingCompanyId());
    }

    /**
     * {@code createTransaction_NotFoundEmployee_ExpectNotFound} is a test on
     * {@link TransactionController#createTransaction(String, String, Transaction)}
     * to verify if the method will call {@link CompanyService#getCompany(String)}
     * and {@link EmployeeService#getEmployee(String)} and return Http Status Not
     * Found(404) when the employee with the specific id is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createTransaction_NotFoundEmployee_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        when(companyService.getCompany(transactionDTO.getLoanCompanyId())).thenReturn(transaction.getLoanCompany());
        when(companyService.getCompany(transactionDTO.getBorrowingCompanyId()))
                .thenReturn(transaction.getBorrowingCompany());
        when(employeeService.getEmployee(transactionDTO.getEmployeeId())).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(companyService).getCompany(transactionDTO.getLoanCompanyId());
        verify(companyService).getCompany(transactionDTO.getBorrowingCompanyId());
        verify(employeeService).getEmployee(transactionDTO.getEmployeeId());
    }

    /**
     * {@code createTransaction_DateCollide_ExpectConflict} is a test on
     * {@link TransactionController#createTransaction(String, String, Transaction)}
     * to verify if the method will call {@link CompanyService#getCompany(String)},
     * {@link EmployeeService#getEmployee(String)} and
     * {@link TransactionService#updateTransaction(Transaction)} and return Http
     * Status Conflict(409) when the transaction dates collided with existing
     * transactions of the transaction's employee.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createTransaction_DateCollide_ExpectConflict() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        when(companyService.getCompany(transactionDTO.getLoanCompanyId())).thenReturn(transaction.getLoanCompany());
        when(companyService.getCompany(transactionDTO.getBorrowingCompanyId()))
                .thenReturn(transaction.getBorrowingCompany());
        when(employeeService.getEmployee(transactionDTO.getEmployeeId())).thenReturn(transaction.getEmployee());
        when(transactionService.addTransaction(any(Transaction.class))).thenThrow(new InvalidDateException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isConflict());

        verify(companyService).getCompany(transactionDTO.getLoanCompanyId());
        verify(companyService).getCompany(transactionDTO.getBorrowingCompanyId());
        verify(employeeService).getEmployee(transactionDTO.getEmployeeId());
        verify(transactionService).addTransaction(any(Transaction.class));
    }

    /**
     * {@code createTransaction_Created_ExpectCreated} is a test on
     * {@link TransactionController#createTransaction(String, String, Transaction)}
     * to verify if the method will call {@link CompanyService#getCompany(String)},
     * {@link EmployeeService#getEmployee(String)} and
     * {@link TransactionService#addTransaction(Transaction)} and return the created
     * specific transaction DTO with Http Status OK(200) and content type of
     * application/json.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void createTransaction_Created_ExpectCreated() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        TransactionDTO transactionDTO = TestTransaction.createTransactionDTO();
        when(companyService.getCompany(transactionDTO.getLoanCompanyId())).thenReturn(transaction.getLoanCompany());
        when(companyService.getCompany(transactionDTO.getBorrowingCompanyId()))
                .thenReturn(transaction.getBorrowingCompany());
        when(employeeService.getEmployee(transactionDTO.getEmployeeId())).thenReturn(transaction.getEmployee());
        when(transactionService.addTransaction(any(Transaction.class))).thenReturn(transaction);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(TestTransaction.URL_EXTENSION)
                .content(TestJsonConverter.writeValueAsString(transactionDTO)).contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
                        MockMvcResultMatchers.content().string(TestJsonConverter.writeValueAsString(transactionDTO)));

        verify(companyService).getCompany(transactionDTO.getLoanCompanyId());
        verify(companyService).getCompany(transactionDTO.getBorrowingCompanyId());
        verify(employeeService).getEmployee(transactionDTO.getEmployeeId());
        verify(transactionService).addTransaction(any(Transaction.class));
    }

    /**
     * {@code deleteTransaction_MissingEmpId_ExpectBadRequest} is a test on
     * {@link TransactionController#deleteTransaction(String, String)}
     * to verify if the method will return Http Status Bad Request(400) when the
     * specific employee's id is missing in the call.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteTransaction_MissingEmpId_ExpectBadRequest() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.DATE_KEY, transaction.getStartDate().toString());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code deleteTransaction_MissingDate_ExpectBadRequest} is a test on
     * {@link TransactionController#deleteTransaction(String, String)}
     * to verify if the method will return Http Status Bad Request(400) when the
     * specific date is missing in the call.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteTransaction_MissingDate_ExpectBadRequest() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.EMP_ID_KEY, transaction.getEmployee().getWorkPermitNumber());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@code deleteTransaction_NotFoundTransaction_ExpectNotFound} is a test
     * on
     * {@link TransactionController#deleteTransaction(String, String)}
     * to verify if the method will call
     * {@link TransactionService#getTransactionByEmployeeAndStartDate(String, LocalDate)}
     * and return Http Status Not Found(404) when the transaction with the specific
     * employee id and start date is not found.
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteTransaction_NotFoundTransaction_ExpectNotFound() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        when(transactionService.getTransactionByEmployeeAndStartDate(transaction.getEmployee().getWorkPermitNumber(),
                transaction.getStartDate())).thenThrow(new ObjectNotExistsException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.EMP_ID_KEY, transaction.getEmployee().getWorkPermitNumber())
                .param(TestTransaction.DATE_KEY, transaction.getStartDate().toString());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(transactionService).getTransactionByEmployeeAndStartDate(transaction.getEmployee().getWorkPermitNumber(),
                transaction.getStartDate());
    }

    /**
     * {@code deleteTransaction_NotFoundTransaction_ExpectNotFound} is a test
     * on
     * {@link TransactionController#deleteTransaction(String, String)}
     * to verify if the method will call
     * {@link TransactionService#getTransactionByEmployeeAndStartDate(String, LocalDate)}
     * and {@link TransactionService#deleteTransaction(TransactionKey)} and return the
     * Http Status OK(200).
     *
     * @throws Exception if any exceptions occurs.
     */
    @Test
    public void deleteTransaction_Deleted_ExpectOkDeleted() throws Exception {
        Transaction transaction = TestTransaction.createTransaction();
        when(transactionService.getTransactionByEmployeeAndStartDate(transaction.getEmployee().getWorkPermitNumber(),
                transaction.getStartDate())).thenReturn(transaction);
        doNothing().when(transactionService).deleteTransaction(transaction.getId());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TestTransaction.URL_EXTENSION)
                .param(TestTransaction.EMP_ID_KEY, transaction.getEmployee().getWorkPermitNumber())
                .param(TestTransaction.DATE_KEY, transaction.getStartDate().toString());

        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(transactionService).getTransactionByEmployeeAndStartDate(transaction.getEmployee().getWorkPermitNumber(),
                transaction.getStartDate());
        verify(transactionService).deleteTransaction(transaction.getId());
    }
}
