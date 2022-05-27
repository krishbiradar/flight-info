package com.virgin.flightinfo.controller;

import com.virgin.flightinfo.exception.NoFlightsFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FlightInfoExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException exception, WebRequest request) {
        Map<String, Object> responseBody = new HashMap<>();

        //check if date conversion issue
        if (exception.getCause().getCause() instanceof IllegalArgumentException) {
            responseBody.put("message", "Invalid date provided in request.");
            responseBody.put("error", exception.getCause().getCause().getMessage());
        }

        if (request instanceof ServletWebRequest) {
            ServletWebRequest servletWebRequest = (ServletWebRequest) request;
            responseBody.put("request", servletWebRequest.getRequest().getRequestURI());
        }

        responseBody.put("time", LocalDateTime.now());
        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NoFlightsFoundException.class)
    protected ResponseEntity<Object> handleNoFlightsFound(RuntimeException exception, WebRequest request) {
        Map<String, Object> responseBody = new HashMap<>();

        if (exception instanceof NoFlightsFoundException) {
            responseBody.put("message", "No flights found.");
            responseBody.put("error", exception.getMessage());
        }

        if (request instanceof ServletWebRequest) {
            ServletWebRequest servletWebRequest = (ServletWebRequest) request;
            responseBody.put("request", servletWebRequest.getRequest().getRequestURI());
        }

        responseBody.put("time", LocalDateTime.now());
        //might not be the best status code to use
        return handleExceptionInternal(exception, responseBody, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED, request);
    }
}
