package com.hireflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InterviewConflictException.class)
    public ResponseEntity<Map<String, Object>> handleInterviewConflict(
            InterviewConflictException ex) {

        return buildResponse(
                HttpStatus.CONFLICT,
                "Conflict",
                ex.getMessage()
        );
    }

    @ExceptionHandler(PipelineTransitionException.class)
    public ResponseEntity<Map<String, Object>> handlePipelineTransition(
            PipelineTransitionException ex) {

        return buildResponse(
                HttpStatus.CONFLICT,
                "Conflict",
                ex.getMessage()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return buildResponse(
                HttpStatus.NOT_FOUND,
                "Not Found",
                ex.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", 400);
        response.put("error", "Bad Request");

        Map<String, String> fieldErrors = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        response.put("message", "Validation failed");
        response.put("errors", fieldErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status,
            String error,
            String message) {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);

        return ResponseEntity
                .status(status)
                .body(response);
    }
}