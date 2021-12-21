 package com.kaizen.model.dto;

 import lombok.*;

 import javax.validation.constraints.*;

 import java.time.LocalDate;

 /**
  * Represents an Transaction DTO.
  *
  * @author Bryan Tan
  * @version 1.0
  * @since 2021-10-22
  */
 @Getter
 @Setter
 @ToString
 @AllArgsConstructor
 @NoArgsConstructor
 @EqualsAndHashCode
 public class TransactionDTO {
     /**
      * Represents the Transaction's id.
      */

     /**
      * Represents the date of Transaction commencement.
      */
     @NotNull
     private LocalDate startDate;

     /**
      * Represents the date of completion (both estimated and actual).
      */
     @NotNull
     private LocalDate endDate;

     /**
      * Represents the Transaction's cost.
      */
     @NotNull
     private double totalCost;

      /**
      * Represents the id of the loan company
      */
     @NotNull
     private String loanCompanyId;

     /**
      * Represents the id of the loan company
      */
     @NotNull
     private String borrowingCompanyId;

       /**
      * Represents the id of employee
      */
     @NotNull
     private String employeeId;

     /**
      * Represents the status of transaction
      */
     @NotNull
     private String status;

 }

