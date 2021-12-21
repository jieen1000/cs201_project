package com.kaizen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.Serializable;

/**
 * {@code ObjectExistsException} is the subclass of {@code RuntimeException}
 * that can be thrown when an object exists when it is not supposed to, marked
 * with status code 409 Conflict.
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
@ResponseStatus(HttpStatus.CONFLICT)
public class ObjectExistsException extends RuntimeException implements Serializable{

    static final long serialVersionUID = 1L;

    /**
     * Constructs a new object exists exception with {@code null} as its detail
     * message. The cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     */
    public ObjectExistsException() {
        super();
    }

    /**
     * Constructs a new object exists exception with the id of object that exists.
     * The cause is not initialized, and may subsequently be initialized by a call
     * to {@link #initCause}.
     *
     * @param object the object that exists. To append to a custom message that will
     *               be saved for later retrieval by the {@link #getMessage()}
     *               method.
     * @param id     the id of object that exists. To append to a custom message
     *               that will be saved for later retrieval by the
     *               {@link #getMessage()} method.
     */
    public ObjectExistsException(String object, String id) {
        super("This " + object + "'s id exists: " + id);
    }

}
