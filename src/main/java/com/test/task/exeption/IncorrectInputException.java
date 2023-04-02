package com.test.task.exeption;

public class IncorrectInputException extends RuntimeException {

    public IncorrectInputException(String message) {

        super(message);
    }

    public IncorrectInputException(String message, Throwable cause) {

        super(message, cause);
    }

}


