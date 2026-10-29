package com.rebanking.controller;


import com.rebanking.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class RebankingControllerValidationHandler {

    Logger logger = LoggerFactory.getLogger(RebankingControllerValidationHandler.class);


    /**
     * Handles validation errors in params.
     *
     * @param ex thrown exception to handle
     * @return message to return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> processValidationError(MethodArgumentNotValidException ex) {

        Map<String, String> data = new HashMap<>();
        data.put("message", "Invalid request parameter(s).");
        logger.error("Invalid request parameter(s).");
        return data;
    }

    /**
     * Handles Resource not found.
     * @param ex
     * @return message to return
     */

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, String> processValidationError(ResourceNotFoundException ex) {

        Map<String, String> data = new HashMap<>();
        data.put("message", ex.getMessage());
        logger.error("Invalid request parameter(s).");
        return data;
    }


    /**
     * Handles internal server errors.
     *
     * @param ex thrown exception to handle
     * @return message to return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> processValidationError(Exception ex) {

        Map<String, String> data = new HashMap<>();
        data.put("message", "The request failed due to an internal server error.");
        logger.error("The request failed due to an internal server error.");
        return data;
    }

}
