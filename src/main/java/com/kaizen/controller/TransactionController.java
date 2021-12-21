package com.kaizen.controller;

import com.kaizen.exceptions.NullValueException;
import com.kaizen.exceptions.ObjectExistsException;
import com.kaizen.exceptions.ObjectNotExistsException;
import com.kaizen.model.entity.Transaction;
import com.kaizen.model.entity.TransactionKey;
import com.kaizen.model.entity.Company;
import com.kaizen.model.entity.Employee;
import com.kaizen.model.dto.TransactionDTO;
import com.kaizen.service.employee.EmployeeService;
import com.kaizen.service.company.CompanyService;
import com.kaizen.service.transaction.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

import java.util.*;

import javax.validation.Valid;

/**
 * {@code TransactionController} is a rest controller for Transaction.
 *
 * @author Bryan Tan
 * @author Tan Jie En
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-10
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    /**
     * The Transaction's service used to do the business's logic for Transaction.
     */
    private final TransactionService transactionService;

    /**
     * The company's service used to do the business's logic for company.
     */
    private final CompanyService companyService;

    /**
     * The employee's service used to do the business's logic for employee.
     */
    private final EmployeeService employeeService;

    /**
     * Create a Transaction's controller with the specific Transaction's service,
     * company's service and employee's service.
     *
     * @param transactionService the Transaction's service used by the application.
     * @param companyService     the company's service used by the application.
     * @param employeeService    the employee's service used by the application.
     */
    @Autowired
    public TransactionController(TransactionService transactionService, CompanyService companyService,
            EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.companyService = companyService;
        this.transactionService = transactionService;
    }

    /**
     * Get all Incoming Transaction DTOs through Transaction's service.
     *
     * @return the list of all Transaction DTOs.
     */
    @GetMapping("incoming")
    public List<TransactionDTO> getIncomingTransactions(@RequestParam(name = "compId") String compId) {
        List<TransactionDTO> TransactionDTOs = new ArrayList<>();
        for (Transaction transaction : transactionService.listTransactions()) {
            if (transaction.getLoanCompany().getUEN().equals(compId)) {
                TransactionDTOs.add(convertToDTO(transaction));
            }
        }
        return TransactionDTOs;
    }

    /**
     * Get all Outgoing Transaction DTOs through Transaction's service.
     *
     * @return the list of all Transaction DTOs.
     */
    @GetMapping("outgoing")
    public List<TransactionDTO> getOutgoingTransactions(@RequestParam(name = "compId") String compId) {
        List<TransactionDTO> TransactionDTOs = new ArrayList<>();
        for (Transaction transaction : transactionService.listTransactions()) {
            if (transaction.getBorrowingCompany().getUEN().equals(compId)) {
                TransactionDTOs.add(convertToDTO(transaction));
            }
        }
        return TransactionDTOs;
    }

    /**
     * Create the specific Transaction through Transaction's service.
     *
     * @param Transaction the Transaction DTO to create from.
     * @exception NullValueException       If the empId is null.
     * @exception ObjectNotExistsException If the employee with the specific id is
     *                                     not in the repository.
     * @return the created Transaction DTO.
     */
    @PutMapping
    public TransactionDTO replaceTransaction(@Valid @RequestBody TransactionDTO transactionDTO)
            throws NullValueException, ObjectExistsException {
        Transaction transaction = convertToEntity(transactionDTO);

        Transaction updatedTransaction = transactionService.updateTransaction(transaction);

        return convertToDTO(updatedTransaction);
    }

    // New method to update status
    @PutMapping(params = { "empId", "date", "status" })
    public TransactionDTO updateTransactionStatus(@RequestParam String empId, @RequestParam String date,
            @RequestParam String status) throws NullValueException, ObjectExistsException {
        LocalDate ldate = LocalDate.parse(date);
        Transaction transaction = transactionService.getTransactionByEmployeeAndStartDate(empId, ldate);
        transaction.setStatus(status);
        Transaction updatedTransaction = transactionService.updateTransaction(transaction);

        return convertToDTO(updatedTransaction);
    }

    /**
     * Update the specific Transaction through Transaction's service.
     *
     * @param Transaction the Transaction DTO to create from.
     * @exception NullValueException       If the empId is null.
     * @exception ObjectNotExistsException If the employee with the specific id is
     *                                     not in the repository.
     * @return the created Transaction DTO.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TransactionDTO createTransaction(@Valid @RequestBody TransactionDTO transactionDTO)
            throws NullValueException, ObjectExistsException {
        Transaction transaction = convertToEntity(transactionDTO);
        Transaction status = transactionService.addTransaction(transaction);

        return convertToDTO(status);
    }

    /**
     * Delete the Transaction with the specific empId through Transaction's service.
     *
     * @param id the id of the Transaction to delete.
     * @exception NullValueException       If the id is null.
     * @exception ObjectNotExistsException If the Transaction is not in the
     *                                     repository.
     */

    @DeleteMapping(params = { "empId", "date" })
    public void deleteTransaction(@RequestParam String empId, @RequestParam String date)
            throws NullValueException, ObjectNotExistsException {
        LocalDate ldate = LocalDate.parse(date);
        transactionService
                .deleteTransaction(transactionService.getTransactionByEmployeeAndStartDate(empId, ldate).getId());
    }

    /**
     * Create an Transaction DTO from the specific Transaction.
     *
     * @param Transaction the Transaction to to create Transaction DTO.
     * @return the DTO of the specific Transaction.
     */
    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(transaction.getStartDate(), transaction.getEndDate(), transaction.getTotalCost(),
                transaction.getLoanCompany().getUEN(), transaction.getBorrowingCompany().getUEN(),
                transaction.getEmployee().getWorkPermitNumber(), transaction.getStatus());
    }

    /**
     * Create an Transaction from the specific Transaction DTO.
     *
     * @param TransactionDTO the Transaction DTO to to create Transaction.
     * @return the Transaction of the specific Transaction DTO.
     */
    private Transaction convertToEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        Company loanCompany = companyService.getCompany(transactionDTO.getLoanCompanyId());
        Company borrowingCompany = companyService.getCompany(transactionDTO.getBorrowingCompanyId());
        Employee employee = employeeService.getEmployee(transactionDTO.getEmployeeId());
        transaction.setId(new TransactionKey(transactionDTO.getLoanCompanyId(), transactionDTO.getBorrowingCompanyId(),
                employee.getName(), transactionDTO.getStartDate()));

        transaction.setStartDate(transactionDTO.getStartDate());
        transaction.setEndDate(transactionDTO.getEndDate());
        transaction.setTotalCost(transactionDTO.getTotalCost());
        transaction.setStatus(transactionDTO.getStatus());
        transaction.setLoanCompany(loanCompany);
        transaction.setBorrowingCompany(borrowingCompany);
        transaction.setEmployee(employee);
        return transaction;
    }
}