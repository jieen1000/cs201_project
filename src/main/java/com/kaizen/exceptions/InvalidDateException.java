package com.kaizen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.LocalDate;
import java.io.Serializable;

/**
 * {@code InvalideDateException} is the subclass of {@code RuntimeException}
 * that can be thrown when an object does not exists when it is supposed to,
 * marked with status code 404 Not Found.
 *
 * <p>
 * {@code InvalidDateException} and its subclasses are <em>unchecked
 * exceptions</em>. Unchecked exceptions do <em>not</em> need to be declared in
 * a method or constructor's {@code throws} clause if they can be thrown by the
 * execution of the method or constructor and propagate outside the method or
 * constructor boundary.
 *
 * @author Tan Jie En
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-11-07
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidDateException extends RuntimeException implements Serializable{

    static final long serialVersionUID = 2L;

    /**
     * Constructs an invalid date exception with {@code null} as its detail
     * message. The cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     */
    public InvalidDateException() {
        super();
    }

    /**
     * Constructs a new invalid date exception with the id of object that does
     * not exists. The cause is not initialized, and may subsequently be initialized
     * by a call to {@link #initCause}.
     *
     * @param startDate the start date of the given transaction. To append to a custom message
     *               that will be saved for later retrieval by the
     *               {@link #getMessage()} method.
     * @param endDate     the end date of the given transaction. To append to a custom
     *               message that will be saved for later retrieval by the
     *               {@link #getMessage()} method.
     */
    public InvalidDateException(LocalDate startDate, LocalDate endDate) {
        super("Date between " + startDate + " and " + endDate + " is not available.");
    }

}