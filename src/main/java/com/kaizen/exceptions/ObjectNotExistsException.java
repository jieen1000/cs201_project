package com.kaizen.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.Serializable;

/**
 * {@code ObjectNotExistsException} is the subclass of {@code RuntimeException}
 * that can be thrown when an object does not exists when it is supposed to,
 * marked with status code 404 Not Found.
 *
 * <p>
 * {@code ObjectNotExistsException} and its subclasses are <em>unchecked
 * exceptions</em>. Unchecked exceptions do <em>not</em> need to be declared in
 * a method or constructor's {@code throws} clause if they can be thrown by the
 * execution of the method or constructor and propagate outside the method or
 * constructor boundary.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotExistsException extends RuntimeException implements Serializable{

    static final long serialVersionUID = 2L;

    /**
     * Constructs a object not exists exception with {@code null} as its detail
     * message. The cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     */
    public ObjectNotExistsException() {
        super();
    }

    /**
     * Constructs a new object not exists exception with the id of object that does
     * not exists. The cause is not initialized, and may subsequently be initialized
     * by a call to {@link #initCause}.
     *
     * @param object the object that does not exists. To append to a custom message
     *               that will be saved for later retrieval by the
     *               {@link #getMessage()} method.
     * @param id     the id of object that does not exists. To append to a custom
     *               message that will be saved for later retrieval by the
     *               {@link #getMessage()} method.
     */
    public ObjectNotExistsException(String object, String id) {
        super("Could not find " + object + " with id: " + id);
    }

}
