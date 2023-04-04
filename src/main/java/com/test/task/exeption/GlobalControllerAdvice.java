package com.test.task.exeption;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.NoSuchElementException;


@ResponseBody
@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<ErrorDetails> handleValidationException(
            ValidationException ex, WebRequest request) {

        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IncorrectInputException.class)
    public final ResponseEntity<ErrorDetails> handleValidationException(
            IncorrectInputException ex, WebRequest request) {

        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<ErrorDetails> handleNotFoundExceptions(Exception ex, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //todo catch IllegalArgumentException on wrong enum Exception/Throwable

    @Getter
    @Setter
    public static class ErrorDetails {

        private Date timestamp;

        private String message;

        private String details;

        ErrorDetails(Date timestamp, String message, String details) {

            this.timestamp = timestamp;
            this.message = message;
            this.details = details;
        }

        @JsonCreator
        public ErrorDetails() {

        }

    }

}
