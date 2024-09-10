package com.hatanaka.book.api.resource.excpetion;

import com.hatanaka.book.api.exception.BookAlreadyExistsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerResource {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(final ConstraintViolationException ex) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(ex.getConstraintViolations().stream()
                               .map(constraintViolation -> Error.builder()
                                       .field(constraintViolation.getPropertyPath().toString())
                                       .message(constraintViolation.getMessage())
                                       .build())
                               .collect(Collectors.toList()))
                .build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(final BookAlreadyExistsException ex) {
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(List.of(Error.builder()
                                       .field(ex.getField())
                                       .message(ex.getMessage())
                                       .build()))
                .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }
}
