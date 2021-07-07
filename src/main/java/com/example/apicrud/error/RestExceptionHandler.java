package com.example.apicrud.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnexpectedException.class)
    protected ResponseEntity<Object> handleUnexpectedError(UnexpectedException ex) {
        RestApiError apiError = new RestApiError(HttpStatus.EXPECTATION_FAILED);
        if (ex.getMessage().isEmpty()) {
            apiError.setMessage(Arrays.toString(ex.getStackTrace()));
        } else {
            apiError.setMessage(ex.getMessage());
            apiError.setDebugMessage(Arrays.toString(ex.getStackTrace()));
        }
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointers(NullPointerException ex) {
        RestApiError apiError = new RestApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Null values are not accepted");
        apiError.setDebugMessage(Arrays.toString(ex.getStackTrace()));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleInputOutputErrors(IOException ex) {
        RestApiError apiError = new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ClosedChannelException.class)
    protected ResponseEntity<Object> handleClosedChannel(ClosedChannelException ex) {
        RestApiError apiError = new RestApiError(HttpStatus.REQUEST_TIMEOUT);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(FileNotFoundException.class)
    protected ResponseEntity<Object> handleFileNotFound(FileNotFoundException ex) {
        RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        RestApiError apiError = new RestApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<Object> handleEntityExists(EntityExistsException ex) {
        RestApiError apiError = new RestApiError(HttpStatus.UNPROCESSABLE_ENTITY);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArguments(IllegalArgumentException ex) {
        RestApiError apiError = new RestApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        RestApiError apiError = new RestApiError(HttpStatus.UNPROCESSABLE_ENTITY);
        apiError.setMessage("Unique constraint violated");
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new RestApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(RestApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
