package com.kaizen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.Serializable;

/**
 * {@code ObjectExistsException} is the subclass of {@code RuntimeException}
 * that can be thrown when a value is null when it is not supposed to, marked
 * with status code 400 Bad Request.
 *
 * <p>
 * {@code ObjectExistsException} and its subclasses are <em>unchecked
 * exceptions</em>. Unchecked exceptions do <em>not</em> need to be declared in
 * a method or constructor's {@code throws} clause if they can be thrown by the
 * execution of the method or constructor and propagate outside the method or
 * constructor boundary.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Error
public class NullValueException extends RuntimeException implements Serializable{

    private static final long serialVersionUID = 3L;

    /**
     * Constructs a null value exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call
     * to {@link #initCause}.
     */
    public NullValueException() {
        super();
    }

    /**
     * Constructs a null value exception with the value that is null. The cause is
     * not initialized, and may subsequently be initialized by a call to
     * {@link #initCause}.
     *
     * @param value the value that is null. To append to a custom message that will
     *              be saved for later retrieval by the {@link #getMessage()}
     *              method.
     */
    public NullValueException(String value) {
        super("This value is null: " + value);
    }

}
