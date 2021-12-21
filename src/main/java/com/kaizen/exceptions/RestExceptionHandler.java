package com.kaizen.exceptions;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.*;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.annotation.*;

/**
 * {@code RestExceptionHandler}, inherited from
 * {@code ResponseEntityExceptionHandler}, annotated with
 * {@link ControllerAdvice @ControllerAdvice} to provide centralized exception
 * handling across all {@code @RequestMapping} methods through
 * {@code @ExceptionHandler} methods.
 *
 * @author Gregory Koh
 * @version 1.0
 * @since 2021-10-15
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Customize the response for MethodArgumentNotValidException.
     * <p>
     * This method delegates to {@link #handleExceptionInternal}.
     * 
     * @param ex      the exception.
     * @param headers the headers to be written to the response.
     * @param status  the selected response status.
     * @param request the current request.
     * @return a {@code ResponseEntity} instance.
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        String message = "";
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            message = message + objectError.getDefaultMessage();
        }
        body.put("message", message);
        body.put("path", request.getDescription(false));
        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * Sends an error response of 400 to the client when NullValueException is
     * thrown.
     *
     * @param response the response that send the error status code.
     * @exception IOException If an input or output exception occurs.
     */
    @ExceptionHandler(NullValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleTypeBadRequest(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Sends an error response of 409 to the client when ObjectExistsException or
     * InvalidDateException is thrown.
     *
     * @param response the response that send the error status code.
     * @exception IOException If an input or output exception occurs.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ObjectExistsException.class, InvalidDateException.class})
    public void handleTypeConflict(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }

    /**
     * Sends an error response of 404 to the client when ObjectNotExistsException is
     * thrown.
     *
     * @param response the response that send the error status code.
     * @exception IOException If an input or output exception occurs.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ObjectNotExistsException.class)
    public void handleTypeNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}
